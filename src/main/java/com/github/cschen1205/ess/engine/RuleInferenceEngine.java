package com.github.cschen1205.ess.engine;


import com.github.cschen1205.ess.enums.ConditionType;

import java.util.List;


/**
 * Created by xschen on 11/9/16.
 */
public interface RuleInferenceEngine {
   void addRule(Rule rule);

   void clearRules();

   //forward chaining
   void infer();

   //backward chaining
   Clause infer(String goalVariable, List<Clause> unproved_conditions);

   RuleBuilder newRule();

   RuleBuilder newRule(String name);

   void clearFacts();

   boolean isFact(Clause goal, List<Clause> unproved_conditions);

   WorkingMemory getKnowledgeBase();

   List<Clause> getFacts();

   void addFact(Clause c);

   List<Clause> getFactsAboutVariable(String variable);

   List<Rule> match();

   List<Rule> getRules();

   void addFact(String name, String value);

   void addFact(String name, ConditionType conditionType, String value);

   Rule getRule(int index);
}
