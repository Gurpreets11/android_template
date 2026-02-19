package com.preet.androidtemplate.core.network;

public class ApiResult<T> {

    public enum Status { LOADING, SUCCESS, ERROR }

    public Status status;
    public T data;
    public String message;

    private ApiResult(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResult<T> loading() {
        return new ApiResult<>(Status.LOADING, null, null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(Status.SUCCESS, data, null);
    }

    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(Status.ERROR, null, message);
    }
}

