package cn.felord.boot.sms.autoconfigure.core;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author felord.cn
 * @since 2020/9/2 15:51
 */
@Slf4j
public class BaiduSmsSender extends AbstractSmsSender {
    private static final String ENDPOINT = "http://smsv3.bj.baidubce.com";
    private final String accessKeyId;
    private final String secretAccessKey;
    private final String signatureId;

    public BaiduSmsSender(boolean enabled, String accessKeyId, String secretAccessKey, String signatureId) {
        super(enabled);
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.signatureId = signatureId;
    }



    @Override
    protected String doSend(String templateCode, LinkedHashMap<String, String> params, String phoneNumber) {
        String result = "1";
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(accessKeyId, secretAccessKey));
        config.setEndpoint(ENDPOINT);
        SmsClient client = new SmsClient(config);

        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(phoneNumber);
        request.setSignatureId(signatureId);
        request.setTemplate(templateCode);
        request.setContentVar(params);
        SendMessageV3Response response = client.sendMessage(request);
        // 解析请求响应 response.isSuccess()为true 表示成功
        if (response != null && response.isSuccess()) {
            return "0";
        } else {
            String reason = response == null ? "unknown" : response.getMessage();
            log.error("sms send error,phoneNumber: {}, reason : {}", phoneNumber, reason);
            return result;
        }
    }

}
