package cn.felord.boot.sms.autoconfigure.core;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author felord.cn
 * @since 2020/9/2 16:07
 */
@Slf4j
public abstract class AbstractSmsSender implements SmsSender {
    private final boolean enabled;

    public AbstractSmsSender(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public SmsResult send(SmsTemplateEnum smsTemplateEnum, Map<String, String> params, String phoneNumber) {
        SmsResult smsResult = new SmsResult();
        final String successCode = "0";
        boolean sendSuccess = false;
        if (enabled) {
            String result = doSend(smsTemplateEnum.templateId(), params, phoneNumber);
            sendSuccess = successCode.equals(result);
        } else {
            log.info("phoneNumber：{} params：{}", phoneNumber, params);
        }
        smsResult.setSuccessful(sendSuccess);
        return smsResult;
    }


    protected abstract String doSend(String templateCode, Map<String, String> params, String phoneNumber);

}
