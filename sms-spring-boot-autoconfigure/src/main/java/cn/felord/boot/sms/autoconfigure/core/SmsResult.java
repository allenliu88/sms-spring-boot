package cn.felord.boot.sms.autoconfigure.core;

/**
 * 短信返回结果
 *
 * @author Dax
 * @since 09:45  2019-04-03
 */
public class SmsResult {
    private boolean successful;
    private Object result;

    /**
     * 短信是否发送成功
     *
     * @return
     */
    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
