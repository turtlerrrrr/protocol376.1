package com.sx.frontend.protocal376_1.exception;

/**
 * Created by PETER on 2015/3/23.
 */
public class CipherException extends Exception{
    private int errorCode;
    public CipherException(int errorCode,String message){
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
