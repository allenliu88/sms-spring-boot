package cn.felord.boot.sms.autoconfigure.core.huawei;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author n1
 * @since 2021/6/4 9:16
 */
@Data
public class HuaweiResponse {
    private List<Result> result;
    private String code;
    private String description;

    @Data
    public static class Result {
        private String originTo;
        private LocalDateTime createTime;
        private String from;
        private String smsMsgId;
        private String status;
    }
}
