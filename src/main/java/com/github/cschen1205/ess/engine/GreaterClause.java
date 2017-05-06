package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;

/**
 * Created by cschen on 6/6/16.
 */
public class GreaterClause extends Clause {

    public GreaterClause(String variable, String value){
        super(variable, value);
        condition = ">";
    }

    @Override
    protected IntersectionType intersect(Clause rhs) {
        if(rhs instanceof NegationClause){
            return rhs.intersect(this);
        }

        double a = 0;
        double b = 0;
        boolean isNumeric = true;
        try{
            a = Double.parseDouble(value);
            b = Double.parseDouble(rhs.getValue());
        }catch(NumberFormatException exception){
            isNumeric = false;
        }
        if(isNumeric){if(rhs instanceof LessClause)
            {
                //v1 > a
                //v2 < b
                //mutually exclusive: b <= a
                //unmatched: b > a
                if(b <= a)
                {
                    return IntersectionType.MutuallyExclusive;
                }
                else
                {
                    return IntersectionType.Unknown;
                }
            }
            else if(rhs instanceof LEClause)
            {
                //v1 > a
                //v2 <= b
                //matched: b <= a
                //unmatched: b > a
                if(b <= a)
                {
                    return IntersectionType.MutuallyExclusive;
                }
                else
                {
                    return IntersectionType.Unknown;
                }
            }
            else if(rhs instanceof EqualsClause)
            {
                //v1 > a
                //v2 = b
                //matched: b > a
                //mutually exclusive: b <= a
                if(b > a)
                {
                    return IntersectionType.Inclusive;
                }
                else
                {
                    return IntersectionType.MutuallyExclusive;
                }
            }
            else if(rhs instanceof GEClause)
            {
                //v1 > a
                //v2 >= b
                //mutually exclusive: b > a
                //unmatched: b < a
                if(b > a)
                {
                    return IntersectionType.Inclusive;
                }
                else
                {
                    return IntersectionType.Unknown;
                }
            }
            else if(rhs instanceof GreaterClause)
            {
                //v1 > a
                //v2 > b
                //mutually exclusive: b >= a
                //unmatched: b < a
                if(b >= a)
                {
                    return IntersectionType.Inclusive;
                }
                else
                {
                    return IntersectionType.Unknown;
                }
            }
            else
            {
                return IntersectionType.Unknown;
            }
        }
        else
        {
            return IntersectionType.Unknown;
        }
    }
}
