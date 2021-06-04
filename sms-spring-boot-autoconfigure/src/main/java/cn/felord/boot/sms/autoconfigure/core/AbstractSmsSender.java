package cn.felord.boot.sms.autoconfigure.core;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Abstract sms sender.
 *
 * @author felord.cn
 * @since 2020 /9/2 16:07
 */
@Slf4j
public abstract class AbstractSmsSender implements SmsSender {
    private final boolean enabled;

    /**
     * Instantiates a new Abstract sms sender.
     *
     * @param enabled the enabled
     */
    public AbstractSmsSender(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public SmsResult send(SmsTemplateEnum smsTemplateEnum, LinkedHashMap<String, String> params, String phoneNumber) {
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


    /**
     * Do send string.
     *
     * @param templateCode the template code
     * @param params       the params
     * @param phoneNumber  the phone number
     * @return the string
     */
    protected abstract String doSend(String templateCode, LinkedHashMap<String, String> params, String phoneNumber);

}
