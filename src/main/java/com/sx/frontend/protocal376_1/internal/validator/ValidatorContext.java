package com.sx.frontend.protocal376_1.internal.validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PETER on 2015/3/24.
 */
public class ValidatorContext {
    private Map<String,IValidator> validatorMap=new ConcurrentHashMap<>();
    public ValidatorContext(){
        validatorMap.put("exclude",new Exclude());
        validatorMap.put("include",new Include());
        validatorMap.put("max",new Max());
        validatorMap.put("min",new Min());
        validatorMap.put("range",new Range());
    }

    public IValidator get(String name){
        return validatorMap.get(name);
    }
}
