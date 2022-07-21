package com.example.demo.ex;

/**
 * Created by shaosen on 2022/1/6
 */
public class CustomerException extends RuntimeException{
    private String code;

    public CustomerException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
