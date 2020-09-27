package com.derick.controllers.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

    private Integer status;
    private String message;
    private Long timeStamp;
    private String path;

    public StandardError() {
    }

    public StandardError(Integer status, String message, Long timeStamp, String path) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
