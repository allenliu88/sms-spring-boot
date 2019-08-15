package cn.felord.boot.sms.autoconfigure.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 短信配置类
 *
 * @author Dax
 * @since 10 :10  2019-04-03
 */
@ConfigurationProperties(prefix = SmsProperties.SMS_PREFIX)
@Data
public class SmsProperties {

    /**
     *  sms prefix.
     */
    static final String SMS_PREFIX = "sms";
    private Aliyun aliyun;

    /**
     * sms switch  production is set to true, development set to false
     */
    private boolean enabled;

    /**
     *  aliyun  sms config.
     */
    @Data
    public static class Aliyun {
        /**
         * aliyun sms sign name must not be null
         */
        private String signName;
        /**
         * aliyun sms access key
         */
        private String accessKeyId;
        /**
         * aliyun sms access key secret
         */
        private String accessKeySecret;

    }

}
