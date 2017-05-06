package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;

/**
 * Created by cschen on 6/6/16.
 */
public  class Clause implements Serializable {
    protected String variable = "";
    protected String value = "";
    protected String condition = "=";

    public Clause(){

    }

    public Clause(String variable, String value){
        this.variable = variable;
        this.value = value;
    }

    public Clause(String variable, String condition, String value){
        this.variable = variable;
        this.condition = condition;
        this.value = value;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public IntersectionType matchClause(Clause rhs){

        if(!variable.equals(rhs.getVariable())){
            return IntersectionType.Unknown;
        }
        return intersect(rhs);
    }

    protected IntersectionType intersect(Clause rhs) {
        throw new NotImplementedException();
    }

    @Override
    public String toString(){
        return variable + " " + condition + " " + value;
    }

}
