package com.qishenghe.developassistant.exception;

import lombok.Data;

/**
 * DevelopAss异常
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe@bonc.com.cn for init
 */
public class DevelopAssException extends RuntimeException {

    /**
     * 错误编码
     */
    private String msgCode;

    /**
     * Constructor default
     */
    public DevelopAssException() {
    }

    /**
     * Constructor single
     *
     * @param message 错误信息
     */
    public DevelopAssException(String message) {
        super(message);
    }

    /**
     * Constructor full
     *
     * @param message 错误信息
     * @param msgCode 错误编码
     */
    public DevelopAssException(String message, String msgCode) {
        super(message);
        this.msgCode = msgCode;
    }

    /**
     * Get Msg Code
     *
     * @return msgCode
     */
    public String getMsgCode() {
        return this.msgCode;
    }

}
