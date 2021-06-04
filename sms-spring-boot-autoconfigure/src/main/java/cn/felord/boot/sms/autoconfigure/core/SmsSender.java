package cn.felord.boot.sms.autoconfigure.core;


import java.util.LinkedHashMap;

/**
 * The interface Sms sender.
 *
 * @author dax
 */
public interface SmsSender {

    /**
     * 发送短信息
     *
     * @param smsTemplateEnum the smsTemplateEnum
     * @param params          the params
     * @param phoneNumber     the phone number
     * @return the sms result
     */
    SmsResult send(SmsTemplateEnum smsTemplateEnum, LinkedHashMap<String, String> params, String phoneNumber);

}