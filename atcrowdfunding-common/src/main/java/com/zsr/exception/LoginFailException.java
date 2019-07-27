package com.zsr.exception;

/**
 * Demo class
 * 自定义异常类
 * @author shourenzhang
 * @date 2019/7/27 17:08
 */
public class LoginFailException extends RuntimeException {

    public LoginFailException(String message){
        super(message);
    }
}
