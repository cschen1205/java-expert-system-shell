package com.github.cschen1205.ess.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cschen on 6/6/16.
 */
public class Rule implements Serializable {
    private Clause consequent = null;
    private List<Clause> antecedents = new ArrayList<>();
    private boolean fired = false;
    private int index = 0;
    private String name;

    public Rule(String name){
        this.name = name;
    }

    public void firstAntecedent(){
        index = 0;
    }

    public boolean hasNextAntecedent(){
        return index < antecedents.size();
    }

    public Clause nextAntecedent(){
        Clause c = antecedents.get(index);
        index++;
        return c;
    }

    public String getName() {
        return name;
    }

    public Clause getConsequent() {
        return consequent;
    }

    public void setConsequent(Clause consequent) {
        this.consequent = consequent;
    }

    public void addAntecedent(Clause antecedent){
        this.antecedents.add(antecedent);
    }

    public boolean isFired(){
        return fired;
    }

    public void fire(WorkingMemory wm){
        if(!wm.isFact(consequent)){
            wm.addFact(consequent);
        }

        fired = true;
    }

    public boolean isTriggered(WorkingMemory wm){
        for(Clause antecedent : antecedents){
            if(!wm.isFact(antecedent)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rule: \"").append(name).append("\"\n");
        sb.append("\twhen\n");

        for(int i=0; i < antecedents.size(); ++i) {
            Clause antecedent = antecedents.get(i);
            sb.append("\t\t").append(antecedent).append("\n");
        }

        sb.append("\tthen\n");

        sb.append("\t\t").append(consequent).append("\n");

        sb.append("end");

        return sb.toString();
    }
}
