package org.seckill.dto;

/**
 * 封装Json结果
 * @param <T>
 */
public class SeckillResult<T> {

    private Boolean success;

    private T data;

    private String error;

    public SeckillResult(Boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public SeckillResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
