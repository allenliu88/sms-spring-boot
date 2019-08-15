package cn.felord.boot.sms.autoconfigure;


import cn.felord.boot.sms.autoconfigure.core.AliSmsSender;
import cn.felord.boot.sms.autoconfigure.core.SmsProperties;
import cn.felord.boot.sms.autoconfigure.core.SmsSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Sms configuration.
 *
 * @author Dax
 * @since 11 :34  2019-04-03
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {
    /**
     * Sms sender sms sender.
     *
     * @param smsProperties the sms properties
     * @return the sms sender
     */
    @Bean
    @ConditionalOnProperty(prefix = "sms.aliyun",name = "sign-name",matchIfMissing = false)
    public SmsSender aliyunSmsSender(SmsProperties smsProperties) {
        SmsProperties.Aliyun aliyun = smsProperties.getAliyun();
        return new AliSmsSender(smsProperties.isEnabled(), aliyun.getSignName(), aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());
    }
}
