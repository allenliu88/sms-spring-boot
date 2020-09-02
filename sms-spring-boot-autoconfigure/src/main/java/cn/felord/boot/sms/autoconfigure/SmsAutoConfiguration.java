package cn.felord.boot.sms.autoconfigure;


import cn.felord.boot.sms.autoconfigure.core.SmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The type Sms configuration.
 *
 * @author Dax
 * @since 11 :34  2019-04-03
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SmsProperties.class)
@Import({AliyunSMSConfiguration.class,BaiduSMSConfiguration.class})
public class SmsAutoConfiguration {

}
