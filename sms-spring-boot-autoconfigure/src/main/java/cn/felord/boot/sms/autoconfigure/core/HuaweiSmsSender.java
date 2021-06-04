package cn.felord.boot.sms.autoconfigure.core;

import cn.felord.boot.sms.autoconfigure.core.huawei.HuaweiBody;
import cn.felord.boot.sms.autoconfigure.core.huawei.HuaweiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;

/**
 * The type Huawei sms sender.
 *
 * @author n1
 * @since 2021 /6/4 9:23
 */
@Slf4j
public class HuaweiSmsSender extends AbstractSmsSender {
    private static final String SUCCESS_CODE = "000000";
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
    private static final IdGenerator NONCE_STR_GENERATOR = new AlternativeJdkIdGenerator();
    private final RestTemplate restTemplate;
    private final SmsProperties.Huawei huawei;

    /**
     * Instantiates a new Huawei sms sender.
     *
     * @param enabled      the enabled
     * @param restTemplate the rest template
     * @param huawei       the huawei
     */
    public HuaweiSmsSender(boolean enabled, RestTemplate restTemplate, SmsProperties.Huawei huawei) {
        super(enabled);
        this.restTemplate = restTemplate;
        this.huawei = huawei;
    }

    @Override
    protected String doSend(String templateCode, LinkedHashMap<String, String> params, String phoneNumber) {
        HuaweiBody huaweiBody = new HuaweiBody();

        huaweiBody.setTemplateId(templateCode);
        huaweiBody.setFrom(params.get("from"));
        huaweiBody.setTemplateParas(params.get("params"));
        huaweiBody.setTo(phoneNumber);

        HuaweiResponse huaweiResponse = this.doRequest(huaweiBody);
        String code = huaweiResponse.getCode();

        return SUCCESS_CODE.equals(code) ? "0" : "-1";
    }

    /**
     * Do request huawei response.
     *
     * @param huaweiBody the huawei body
     * @return the huawei response
     */
    private HuaweiResponse doRequest(HuaweiBody huaweiBody) {
        URI uri = UriComponentsBuilder.fromHttpUrl(huawei.getEndpoint())
                .build()
                .toUri();
        String wsseHeader = this.buildWsseHeader(huawei.getAccessKeyId(), huawei.getSecretAccessKey());

        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("from", huaweiBody.getFrom());
        postParameters.add("to", huaweiBody.getTo());
        postParameters.add("templateId", huaweiBody.getTemplateId());
        postParameters.add("templateParas", huaweiBody.getTemplateParas());
        postParameters.add("statusCallback", "");
        postParameters.add("signature", huawei.getSignName());

        RequestEntity<MultiValueMap<String, String>> entity = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .header("X-WSSE", wsseHeader)
                .body(postParameters);
        ResponseEntity<HuaweiResponse> exchange = restTemplate.exchange(entity, HuaweiResponse.class);
        HuaweiResponse body = exchange.getBody();
        if (log.isDebugEnabled()) {
            log.debug("huawei sms sender response : {}", body);
        }
        return body;
    }


    /**
     * 构造X-WSSE参数值
     *
     * @param appKey    the app key
     * @param appSecret the app secret
     * @return string string
     */
    private String buildWsseHeader(String appKey, String appSecret) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        String nonce = NONCE_STR_GENERATOR.generateId()
                .toString()
                .replaceAll("-", "");
        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes());
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }
}
