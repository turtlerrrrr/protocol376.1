package com.sx.frontend.protocal376_1.internal.validator;

import java.util.List;

/**
 * Created by PETER on 2015/3/17.
 */
public class Range implements IValidator {
    @Override
    public boolean check(Object value, List<Double> target) {
        if(Double.parseDouble(value.toString())<target.get(0) || Double.parseDouble(value.toString())>target.get(1)){
            return false;
        }else{
            return true;
        }
    }
}
