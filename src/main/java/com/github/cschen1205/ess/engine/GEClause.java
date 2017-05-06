package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;

/**
 * Created by cschen on 6/6/16.
 */
public class GEClause extends Clause {

    public GEClause(String variable, String value){
        super(variable, value);
        condition = ">=";
    }



    @Override
    protected IntersectionType intersect(Clause rhs) {
        if(rhs instanceof NegationClause){
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
            if(rhs instanceof LessClause){
                if(b <= a) return IntersectionType.MutuallyExclusive;
                else return IntersectionType.Unknown;
            } else if(rhs instanceof  LEClause){
                if(b < a) return IntersectionType.MutuallyExclusive;
                else return IntersectionType.Unknown;
            } else if(rhs instanceof EqualsClause){
                if(b >= a) return IntersectionType.Inclusive;
                else return IntersectionType.MutuallyExclusive;
            } else if(rhs instanceof GEClause){
                if(b >= a) return IntersectionType.Inclusive;
                else return IntersectionType.Unknown;
            } else if(rhs instanceof GreaterClause){
                if(b >= a) return IntersectionType.Inclusive;
                else return IntersectionType.Unknown;
            } else {
                return IntersectionType.Unknown;
            }
        } else {
            return IntersectionType.Unknown;
        }
    }
}
