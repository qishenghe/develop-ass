package com.qishenghe.developassistant.exception;

/**
 * DevelopAss异常
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe@bonc.com.cn for init
 */
public class DevelopAssException extends RuntimeException {

    /**
     * 全局异常前缀
     */
    private static final String EXCEPTION_START_WITH = "Develop Ass Exception : ";

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
        super(EXCEPTION_START_WITH + message);
    }

    /**
     * Constructor full
     *
     * @param message 错误信息
     * @param msgCode 错误编码
     */
    public DevelopAssException(String message, String msgCode) {
        super(EXCEPTION_START_WITH + message);
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
