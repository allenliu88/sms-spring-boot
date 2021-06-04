package cn.felord.boot.sms.autoconfigure.core.huawei;

import lombok.Data;

/**
 * @author n1
 * @since 2021/6/3 17:09
 */
@Data
public class HuaweiBody {
    private String from;
    private String to;
    private String templateId;
    private String templateParas;
}
