package com.github.cschen1205.ess.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen0 on 7/6/2016.
 */
public class RuleBuilder {
    private List<Clause> antecendents = new ArrayList<>();
    private Clause consequent = null;
    private KieRuleInferenceEngine engine;
    private String name;

    public RuleBuilder(KieRuleInferenceEngine engine){
        this.engine = engine;
        name = "Rule #"+(engine.getRules().size()+1);
    }

    public RuleBuilder(KieRuleInferenceEngine engine, String name){
        this.engine = engine;
        this.name = name;
    }

    public RuleBuilder ifEquals(String variable, String value){
        antecendents.add(new EqualsClause(variable, value));
        return this;
    }

    public RuleBuilder ifNotEquals(String variable, String value){
        antecendents.add(new NegationClause(new EqualsClause(variable, value)));
        return this;
    }

    public RuleBuilder andEquals(String variable, String value){
        antecendents.add(new EqualsClause(variable, value));
        return this;
    }

    public RuleBuilder andNotEquals(String variable, String value){
        antecendents.add(new NegationClause(new EqualsClause(variable, value)));
        return this;
    }

    public RuleBuilder thenEquals(String variable, String value){
        consequent = new EqualsClause(variable, value);
        return this;
    }

    public RuleBuilder thenNotEquals(String variable, String value){
        consequent = new NegationClause(new EqualsClause(variable, value));
        return this;
    }

    public RuleBuilder ifLess(String variable, String value){
        antecendents.add(new LessClause(variable, value));
        return this;
    }

    public RuleBuilder andLess(String variable, String value){
        antecendents.add(new LessClause(variable, value));
        return this;
    }

    public RuleBuilder thenLess(String variable, String value){
        consequent = new LessClause(variable, value);
        return this;
    }

    public RuleBuilder ifGreater(String variable, String value){
        antecendents.add(new GreaterClause(variable, value));
        return this;
    }

    public RuleBuilder andGreater(String variable, String value){
        antecendents.add(new GreaterClause(variable, value));
        return this;
    }

    public RuleBuilder thenGreater(String variable, String value){
        consequent = new GreaterClause(variable, value);
        return this;
    }

    public RuleBuilder ifGE(String variable, String value){
        antecendents.add(new GEClause(variable, value));
        return this;
    }

    public RuleBuilder andGE(String variable, String value){
        antecendents.add(new GEClause(variable, value));
        return this;
    }

    public RuleBuilder thenGE(String variable, String value){
        consequent = new GEClause(variable, value);
        return this;
    }

    public RuleBuilder ifLE(String variable, String value){
        antecendents.add(new LEClause(variable, value));
        return this;
    }

    public RuleBuilder andLE(String variable, String value){
        antecendents.add(new LEClause(variable, value));
        return this;
    }

    public RuleBuilder thenLE(String variable, String value){
        consequent = new LEClause(variable, value);
        return this;
    }

    public RuleBuilder ifMatch(String variable, String value){
        antecendents.add(new RegexMatchClause(variable, value));
        return this;
    }

    public RuleBuilder ifNotMatch(String variable, String value){
        antecendents.add(new NegationClause(new RegexMatchClause(variable, value)));
        return this;
    }

    public RuleBuilder andMatch(String variable, String value){
        antecendents.add(new RegexMatchClause(variable, value));
        return this;
    }

    public RuleBuilder andNotMatch(String variable, String value){
        antecendents.add(new NegationClause(new RegexMatchClause(variable, value)));
        return this;
    }

    public RuleBuilder thenMatch(String variable, String value){
        consequent = new RegexMatchClause(variable, value);
        return this;
    }

    public RuleBuilder thenNotMatch(String variable, String value){
        consequent = new NegationClause(new RegexMatchClause(variable, value));
        return this;
    }

    public Rule build(){
        Rule rule = new Rule(name);
        for(Clause c : antecendents){
            rule.addAntecedent(c);
        }
        rule.setConsequent(consequent);
        engine.addRule(rule);

        return rule;
    }


}
