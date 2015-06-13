package com.sx.frontend.protocal376_1.internal.fieldGroupFormater;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by PETER on 2015/3/27.
 */
public class FormatterImpl implements IFormatter {
    private static Pattern ptn=Pattern.compile("\\$\\{[0-9a-zA-Z]+\\}");
    StringBuilder patterBuilder=new StringBuilder();
    StringBuilder valueBuilder=new StringBuilder();
    @Override
    public String union(String pattern, Map<String, Object> value) {
        String out=pattern;
        for(String s:value.keySet()){
            out=out.replace(String.format("${%s}", s),value.get(s).toString());
        }
        return out;
    }

    //${ip1Seg1}.${ip1Seg2}.${ip1Seg3}.${ip1Seg4}:${ip1port}
    @Override
    public Map<String, Object> division(String pattern, String value) {
        String[] splits=ptn.split(pattern);
        patterBuilder.delete(0, patterBuilder.length());
        patterBuilder.append(pattern.replace(splits[0],""));
        valueBuilder.delete(0, valueBuilder.length());
        valueBuilder.append(value.replace(splits[0],""));
        Map<String,Object> map=new HashMap<>();
        for(int i=0;i<splits.length-1;i++){
            map.put(patterBuilder.substring(2,patterBuilder.indexOf(splits[i+1])-1),
                    valueBuilder.substring(0,valueBuilder.indexOf(splits[i+1])));
            patterBuilder.delete(0, patterBuilder.indexOf(splits[i+1])+1);
            valueBuilder.delete(0, valueBuilder.indexOf(splits[i+1])+1);
        }
        map.put(patterBuilder.substring(2,patterBuilder.length()-1),valueBuilder.substring(0));
        return map;
    }


}
