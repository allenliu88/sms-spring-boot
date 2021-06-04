package cn.felord.boot.sms.autoconfigure;

import cn.felord.boot.sms.autoconfigure.core.HuaweiSmsSender;
import cn.felord.boot.sms.autoconfigure.core.SmsProperties;
import cn.felord.boot.sms.autoconfigure.core.SmsSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The type Huawei sm sconfiguration.
 *
 * @author n1
 * @since 2021 /6/4 10:10
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({RestTemplate.class})
@ConditionalOnProperty(prefix = "sms.huawei", name = "sign-name")
public class HuaweiSMSconfiguration {

    /**
     * Huawei sms sender sms sender.
     *
     * @param restTemplate  the rest template
     * @param smsProperties the sms properties
     * @return the sms sender
     */
    @Bean
    public SmsSender huaweiSmsSender(RestTemplate restTemplate, SmsProperties smsProperties) {
        SmsProperties.Huawei huawei = smsProperties.getHuawei();
        return new HuaweiSmsSender(smsProperties.isEnabled(), restTemplate, huawei);
    }
}
