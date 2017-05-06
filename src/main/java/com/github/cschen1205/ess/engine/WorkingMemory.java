package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cschen on 6/6/16.
 */
public class WorkingMemory implements Serializable {
    private List<Clause> facts =new ArrayList<>();

    public void addFact(Clause fact){
        facts.add(fact);
    }

    public boolean isNotFact(Clause c){
        for(Clause fact : facts){
            if(fact.matchClause(c) == IntersectionType.MutuallyExclusive){
                return true;
            }
        }
        return false;
    }

    public void clearFacts(){
        facts.clear();
    }

    public boolean isFact(Clause c){
        for(Clause fact : facts){
            if(fact.matchClause(c) == IntersectionType.Inclusive){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(int i=0; i < facts.size(); ++i){
            if(i != 0){
                sb.append("\n");
            }
            sb.append(facts.get(i));
        }

        return sb.toString();

    }

    public List<Clause> getFacts() {
        return facts;
    }

    public void given(String variable, String value) {
        addFact(new EqualsClause(variable, value));
    }

}
