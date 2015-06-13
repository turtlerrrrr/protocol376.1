package com.sx.frontend.protocal376_1.internal.ConfigParse;

import java.util.List;
/**
 * Created by PETER on 2015/3/17.
 */
public class Validator {
    private String name;
    private List<Double> target;

    public Validator(String name, List<Double> target) {
        this.name = name;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getTarget() {
        return target;
    }

    public void setTarget(List<Double> target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Validator{" +
                "name='" + name + '\'' +
                ", target=" + target +
                '}';
    }
}
