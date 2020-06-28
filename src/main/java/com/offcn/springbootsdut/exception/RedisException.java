package com.offcn.springbootsdut.exception;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/6/22
 */
public class RedisException extends RuntimeException {

    private String message;

    public RedisException(){
        super();
    }

    public RedisException(String message) {
        super(message);
        this.message = message;
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }
}
