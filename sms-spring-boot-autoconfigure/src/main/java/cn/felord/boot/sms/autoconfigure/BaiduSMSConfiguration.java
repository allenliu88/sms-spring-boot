package cn.felord.boot.sms.autoconfigure;

import cn.felord.boot.sms.autoconfigure.core.BaiduSmsSender;
import cn.felord.boot.sms.autoconfigure.core.SmsProperties;
import cn.felord.boot.sms.autoconfigure.core.SmsSender;
import com.baidubce.services.sms.SmsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Baidu sms configuration.
 *
 * @author felord.cn
 * @since 2020 /9/2 16:12
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({SmsClient.class})
@ConditionalOnProperty(prefix = "sms.baidu",name = "signature-id")
public class BaiduSMSConfiguration {
    /**
     * Baidu sms sender sms sender.
     *
     * @param smsProperties the sms properties
     * @return the sms sender
     */
    @Bean
    public SmsSender baiduSmsSender(SmsProperties smsProperties) {
        SmsProperties.Baidu baidu = smsProperties.getBaidu();
        return new BaiduSmsSender(smsProperties.isEnabled(), baidu.getAccessKeyId(), baidu.getSecretAccessKey(), baidu.getSignatureId());
    }
}
