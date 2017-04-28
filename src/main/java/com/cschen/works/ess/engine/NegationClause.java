package com.cschen.works.ess.engine;

import com.cschen.works.ess.enums.IntersectionType;

/**
 * Created by chen0 on 7/6/2016.
 */
public class NegationClause extends Clause {

    private Clause origin;
    public NegationClause(Clause c){
        super(c.getVariable(), c.getValue());
        condition = "!=";
        origin = c;
    }

    @Override
    protected IntersectionType intersect(Clause rhs) {
        IntersectionType r = origin.intersect(rhs);
        if(r == IntersectionType.Inclusive){
            return IntersectionType.MutuallyExclusive;
        } else if(r == IntersectionType.MutuallyExclusive) {
            return IntersectionType.Inclusive;
        }
        return IntersectionType.Unknown;
    }

    @Override
    public String toString(){
        return origin.getVariable() + " !" + origin.getCondition() + " " + origin.getValue();
    }
}
