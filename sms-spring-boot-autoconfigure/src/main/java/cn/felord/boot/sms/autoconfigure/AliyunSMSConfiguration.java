package cn.felord.boot.sms.autoconfigure;

import cn.felord.boot.sms.autoconfigure.core.AliSmsSender;
import cn.felord.boot.sms.autoconfigure.core.SmsProperties;
import cn.felord.boot.sms.autoconfigure.core.SmsSender;
import com.aliyuncs.IAcsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author felord.cn
 * @since 2020/9/2 15:41
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "sms.aliyun",name = "sign-name")
@ConditionalOnClass(IAcsClient.class)
public class AliyunSMSConfiguration {
    /**
     * Sms sender sms sender.
     *
     * @param smsProperties the sms properties
     * @return the sms sender
     */
    @Bean
    public SmsSender aliyunSmsSender(SmsProperties smsProperties) {
        SmsProperties.Aliyun aliyun = smsProperties.getAliyun();
        return new AliSmsSender(smsProperties.isEnabled(), aliyun.getSignName(), aliyun.getAccessKeyId(), aliyun.getAccessKeySecret());
    }
}
