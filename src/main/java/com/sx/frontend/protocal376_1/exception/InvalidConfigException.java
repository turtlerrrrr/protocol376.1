package com.sx.frontend.protocal376_1.exception;

/**
 * Created by PETER on 2015/2/28.
 */
public class InvalidConfigException extends Exception{
    private static final long serialVersionUID = -614392367119589902L;
    private int errorCode;
    public InvalidConfigException(int errorCode,String message) {
        super(message);
        this.errorCode=errorCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
