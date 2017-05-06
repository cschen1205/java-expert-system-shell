package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chen0 on 7/6/2016.
 */
public class RegexMatchClause extends Clause {


    public RegexMatchClause(String variable, String value){
        super(variable, value);
        condition = "match";
    }

    private Pattern pattern = null;

    @Override
    protected IntersectionType intersect(Clause rhs) {

        if(rhs instanceof RegexMatchClause){
            RegexMatchClause rhs2 = (RegexMatchClause)rhs;

            if(value.equals(rhs2.getValue())) return IntersectionType.Inclusive;

            if(match(rhs2.getValue())){
                return IntersectionType.Inclusive;
            } else if(rhs2.match(value)){
                return IntersectionType.Inclusive;
            }
            return IntersectionType.MutuallyExclusive;
        } else if(rhs instanceof EqualsClause){
            if(match(rhs.getValue())){
                return IntersectionType.Inclusive;
            }else {
                return IntersectionType.MutuallyExclusive;
            }
        } else if(rhs instanceof NegationClause){
            return rhs.intersect(this);
        }
        return IntersectionType.Unknown;
    }

    public boolean match(String content){
        if(pattern == null) {
            pattern = Pattern.compile(value);
        }

        Matcher m = pattern.matcher(content);
        if(m.find()) {
            return true;
        }
        else {
            return false;
        }
    }
}
