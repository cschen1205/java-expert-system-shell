package com.github.cschen1205.ess.engine;


import com.github.cschen1205.ess.enums.ConditionType;
import com.github.cschen1205.ess.enums.IntersectionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cschen on 6/6/16.
 */
public class KieRuleInferenceEngine implements RuleInferenceEngine {
    protected List<Rule> rules = new ArrayList<>();
    protected WorkingMemory memory = new WorkingMemory();

    public KieRuleInferenceEngine(){

    }

    @Override public void addRule(Rule rule){
        rules.add(rule);
    }

    @Override public void clearRules(){
        rules.clear();
    }

    //forward chaining
    @Override public void infer(){
        List<Rule> cs = null;
        do{
            cs = match();
            if(!cs.isEmpty()){
                if(!fireRule(cs)){
                    break;
                }
            }
        } while(!cs.isEmpty());
    }

    //backward chaining
    @Override public Clause infer(String goalVariable, List<Clause> unproved_conditions){
        Clause conclusion = null;

        for(Rule rule : rules){
            rule.firstAntecedent();
            boolean goalReached = true;
            while(rule.hasNextAntecedent()){
                Clause antecedent = rule.nextAntecedent();

                if(!memory.isFact(antecedent)){
                    if(memory.isNotFact(antecedent)){ //conflict with what is already known
                        goalReached = false;
                        break;
                    }else if(isFact(antecedent, unproved_conditions)){ //deduce to be a fact
                        memory.addFact(antecedent);
                    } else { //deduce to not be a fact
                        goalReached = false;
                        break;
                    }
                }
            }
            if(goalReached){
                conclusion = rule.getConsequent();
                break;
            }

        }

        return conclusion;
    }

    @Override public RuleBuilder newRule(){
        return new RuleBuilder(this);
    }

    @Override public RuleBuilder newRule(String name){
        return new RuleBuilder(this, name);
    }

    @Override public void clearFacts(){
        memory.clearFacts();
    }

    @Override public boolean isFact(Clause goal, List<Clause> unproved_conditions){
        List<Rule> goalStack = new ArrayList<>();

        for(Rule rule : rules){
            Clause consequent = rule.getConsequent();
            IntersectionType it = consequent.matchClause(goal);

            if(it == IntersectionType.Inclusive){
                goalStack.add(rule);
            }
        }

        if(goalStack.isEmpty()){
            unproved_conditions.add(goal);
        } else {
            for(Rule rule : goalStack){
                rule.firstAntecedent();
                boolean goalReached = true;
                while(rule.hasNextAntecedent()){
                    Clause antecedent = rule.nextAntecedent();

                    if(!memory.isFact(antecedent)){
                        if(memory.isNotFact(antecedent)){
                            goalReached = false;
                            break;
                        } else if(isFact(antecedent, unproved_conditions)){
                            memory.addFact(antecedent);
                        } else {
                            goalReached = false;
                            break;
                        }
                    }
                }

                if(goalReached){
                    return true;
                }
            }
        }

        return false;

    }


    protected boolean fireRule(List<Rule> conflictingRules){
        boolean hasRule2Fire = false;
        for(Rule rule : conflictingRules){
            if(!rule.isFired()){
                hasRule2Fire = true;
                rule.fire(memory);
            }
        }

        return hasRule2Fire;
    }

    @Override public WorkingMemory getKnowledgeBase(){
        return memory;
    }

    @Override public List<Clause> getFacts() { return memory.getFacts();}

    @Override public void addFact(Clause c){
        memory.addFact(c);
    }

    @Override public List<Clause> getFactsAboutVariable(String variable){
        List<Clause> facts = new ArrayList<>();
        for(Clause c : memory.getFacts()){
            if(c.getVariable().equalsIgnoreCase(variable)){
                facts.add(c);
            }
        }
        return facts;
    }

    @Override public List<Rule> match(){
        List<Rule> cs = new ArrayList<>();
        for(Rule rule : rules){
            if(rule.isTriggered(memory)){
                cs.add(rule);
            }
        }

        return cs;
    }

    @Override public List<Rule> getRules() {
        return rules;
    }

    @Override public void addFact(String name, String value) {
        addFact(new EqualsClause(name, value));
    }

    @Override public void addFact(String name, ConditionType conditionType, String value){
        Clause clause = null;
        switch(conditionType){
            case Equals:
                clause = new EqualsClause(name, value);
                break;
            case Less:
                clause = new LessClause(name, value);
                break;
            case LessEquals:
                clause = new LEClause(name, value);
                break;
            case Greater:
                clause = new GreaterClause(name, value);
                break;
            case GreaterEquals:
                clause = new GEClause(name, value);
                break;
            case Match:
                clause = new RegexMatchClause(name, value);
                break;
            case NotEquals:
                clause = new NegationClause(new EqualsClause(name, value));
                break;
            case NotMatch:
                clause = new NegationClause(new RegexMatchClause(name, value));
                break;
        }

        if(clause == null){
            return;
        }
        addFact(clause);
    }


    @Override public Rule getRule(int index) {
        return rules.get(index);
    }
}
