package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;

/**
 * Created by cschen on 6/6/16.
 */
public class EqualsClause extends Clause {

    public EqualsClause(String variable, String value){
        super(variable, value);
        condition = "=";
    }

    @Override
    protected IntersectionType intersect(Clause rhs) {
        if(rhs instanceof EqualsClause){
            if(value.equals(rhs.getValue())){
                return IntersectionType.Inclusive;
            } else {
                return IntersectionType.MutuallyExclusive;
            }
        } else if(rhs instanceof RegexMatchClause){
            RegexMatchClause rhs2 = (RegexMatchClause)rhs;
            return rhs2.intersect(this);
        } else if(rhs instanceof NegationClause){
            return rhs.intersect(this);
        }

        String v1 = value;
        String v2 = rhs.getValue();

        double a = 0;
        double b = 0;

        boolean isNumeric = true;
        try{
            a = Double.parseDouble(v1);
            b = Double.parseDouble(v2);
        }catch(NumberFormatException exception){
            isNumeric = false;
        }

        if(isNumeric){
            if(rhs instanceof LessClause) {
                if (a >= b) {
                    return IntersectionType.MutuallyExclusive;
                } else {
                    return IntersectionType.Inclusive;
                }
            } else if(rhs instanceof LEClause) {
                if(a > b) return IntersectionType.MutuallyExclusive;
                else return IntersectionType.Inclusive;
            } else if(rhs instanceof  GreaterClause){
                if(a <= b) return IntersectionType.MutuallyExclusive;
                else return IntersectionType.Inclusive;
            } else if(rhs instanceof  GEClause){
                if(a < b) return IntersectionType.MutuallyExclusive;
                else return IntersectionType.Inclusive;
            } else {
                return IntersectionType.Unknown;
            }
        } else {
            return IntersectionType.Unknown;
        }
    }
}
