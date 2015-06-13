package com.sx.frontend.protocal376_1.exception;

/**
 * Created by PETER on 2015/3/23.
 */
public class EncodingException extends Exception{
    private int errorCode;
    public EncodingException(int errorCode,String message){
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
