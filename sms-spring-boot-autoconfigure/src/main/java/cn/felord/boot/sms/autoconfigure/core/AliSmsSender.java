package cn.felord.boot.sms.autoconfigure.core;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Ali sms sender.
 *
 * @author Dax
 * @since 10 :40  2019-04-03
 */
@Slf4j
public class AliSmsSender extends AbstractSmsSender {

    private final String signName;
    private final String accessKeyId;
    private final String accessKeySecret;
    /**
     * 产品名称:云通信短信API产品,开发者无需替换   阿里云短信测试专用
     */
    private static final String PRODUCT = "Dysmsapi";
    private static final String SUCCESS = "OK";

    /**
     * 产品域名,开发者无需替换
     */
    private static final String API = "dysmsapi.aliyuncs.com";
    private static Map<String, String> statusMap = new HashMap<>();

    static {
        statusMap.put("OK", "请求成功");
        statusMap.put("isp.RAM_PERMISSION_DENY", "RAM权限DENY");
        statusMap.put("isv.OUT_OF_SERVICE", "业务停机");
        statusMap.put("isv.PRODUCT_UN_SUBSCRIPT", "未开通云通信产品的阿里云客户");
        statusMap.put("isv.PRODUCT_UNSUBSCRIBE", "产品未开通");
        statusMap.put("isv.ACCOUNT_NOT_EXISTS", "账户不存在");
        statusMap.put("isv.ACCOUNT_ABNORMAL", "账户异常");
        statusMap.put("isv.SMS_TEMPLATE_ILLEGAL", "短信模板不合法");
        statusMap.put("isv.SMS_SIGNATURE_ILLEGAL", "短信签名不合法");
        statusMap.put("isv.INVALID_PARAMETERS", "参数异常");
        statusMap.put("isp.SYSTEM_ERROR", "ISP系统错误");
        statusMap.put("isv.MOBILE_NUMBER_ILLEGAL", "非法手机号");
        statusMap.put("isv.MOBILE_COUNT_OVER_LIMIT", "手机号码数量超过限制");
        statusMap.put("isv.TEMPLATE_MISSING_PARAMETERS", "模板缺少变量");
        statusMap.put("isv.BUSINESS_LIMIT_CONTROL", "业务限流");
        statusMap.put("isv.INVALID_JSON_PARAM", "JSON参数不合法，只接受字符串值");
        statusMap.put("isv.BLACK_KEY_CONTROL_LIMIT", "黑名单管控");
        statusMap.put("isv.PARAM_LENGTH_LIMIT", "参数超出长度限制");
        statusMap.put("isv.PARAM_NOT_SUPPORT_URL", "不支持URL");
        statusMap.put("isv.AMOUNT_NOT_ENOUGH", "账户余额不足");
    }

    public AliSmsSender(boolean enabled, String signName, String accessKeyId, String accessKeySecret) {
        super(enabled);
        this.signName = signName;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }


    /**
     * Send sms send sms response.
     * <p>
     * 阿里云短信
     *
     * @param templateCode the template code
     * @param params       the params
     * @param phoneNumber  the phone number
     * @return the send sms response
     */
    @Override
    public String doSend(String templateCode, Map<String, String> params, String phoneNumber) {
        String result = "1";
        IAcsClient acsClient = initClient();

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        log.debug("短信参数" + params.toString());

        Gson gson = new Gson();
        String json = gson.toJson(params);
        request.setTemplateParam(json);


        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            String status = sendSmsResponse.getCode();

            if (SUCCESS.equals(status)) {
                result = "0";
            }

            String msg = "{\"状态码\":\"%s\",\"描述\":\"%s\"}";
            log.debug(String.format(msg, status, statusMap.get(status)));

        } catch (ClientException e) {
            log.error("短信发送异常 {}", params);
        }

        return result;
    }

    private IAcsClient initClient() {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "4000");
        System.setProperty("sun.net.client.defaultReadTimeout", "4000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", PRODUCT, API);
        return new DefaultAcsClient(profile);
    }
}
