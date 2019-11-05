package com.noah.solutions.common.exception;

public class CustormException extends RuntimeException {
    protected Integer code;
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CustormException(String message, Integer code, String url) {
        super(message);
        this.code = code;
        this.url = url;
    }

    public CustormException(IExceptionEnums cdeAndMsg, String url) {
        super(cdeAndMsg.getMsg());
        this.code = cdeAndMsg.getCode();
        this.url = url;
    }

    public CustormException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public CustormException(IExceptionEnums cdeAndMsg) {
        super(cdeAndMsg.getMsg());
        this.code = cdeAndMsg.getCode();
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
