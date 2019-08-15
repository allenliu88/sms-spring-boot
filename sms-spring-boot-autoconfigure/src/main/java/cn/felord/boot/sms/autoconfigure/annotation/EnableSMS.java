package cn.felord.boot.sms.autoconfigure.annotation;

import cn.felord.boot.sms.autoconfigure.SmsAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用短信息配置
 *
 * @author Dax
 * @since 16:03  2019-05-15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SmsAutoConfiguration.class)
public @interface EnableSMS {
}
