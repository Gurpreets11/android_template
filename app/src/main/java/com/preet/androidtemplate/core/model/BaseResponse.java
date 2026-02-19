package com.preet.androidtemplate.core.model;

import java.util.List;

public class BaseResponse<T> {

    private String statusCode;
    private String statusMessage;
    private List<T> data;
//    private T data;
    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<T> getData() {
        return data;
    }

    /*public T getData() {
        return data;
    }
*/
    public boolean isSuccess() {
        return "200".equals(statusCode);
    }
}

