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
     * sms prefix.
     */
    static final String SMS_PREFIX = "sms";
    /**
     * aliyun
     */
    private Aliyun aliyun;
    /**
     * baidu
     */
    private Baidu baidu;
    /**
     * huawei
     */
    private Huawei huawei;

    /**
     * sms switch  production is set to true, development set to false
     */
    private boolean enabled;

    /**
     * aliyun  sms config.
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

    /**
     * baidu  sms config.
     */
    @Data
    public static class Baidu {
        /**
         * baidu sms accessKeyId
         */
        private String accessKeyId;
        /**
         * baidu sms secretAccessKey
         */
        private String secretAccessKey;
        /**
         * baidu sms signatureId
         */
        private String signatureId;

    }

    /**
     * huawei sms config.
     */
    @Data
    public static class Huawei {
        /**
         *  endpoint
         */
        private String endpoint;
        /**
         *  accessKeyId
         */
        private String accessKeyId;
        /**
         *  secretAccessKey
         */
        private String secretAccessKey;
        /**
         *  signName
         */
        private String signName;
    }

}
