package com.sx.frontend.protocal376_1.exception;

/**
 * Created by PETER on 2015/3/25.
 */
public class Preconditions {
    public static void checkNotNull(Object object,int code,String message) throws VerifyException{
        if(object==null){
            throw new VerifyException(code,message);
        }
        if(object instanceof String){
            if(object.toString().trim().length()==0){
                throw new VerifyException(code,message);
            }
        }
    }
}
