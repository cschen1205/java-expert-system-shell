package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.ConditionType;
import org.testng.annotations.Test;

import java.util.Scanner;
import java.util.Vector;

/**
 * Created by cschen on 27/4/17.
 */
public class RuleInferenceEngineUnitTest {

    @Test
    public void testBackwardChain()
    {
        RuleInferenceEngine rie=getInferenceEngine();
        rie.addFact(new EqualsClause("num_wheels", "4"));
        rie.addFact(new EqualsClause("motor", "yes"));
        rie.addFact(new EqualsClause("num_doors", "3"));
        rie.addFact(new EqualsClause("size", "medium"));

        System.out.println("Infer: vehicle");

        Vector<Clause> unproved_conditions= new Vector<>();

        Clause conclusion=rie.infer("vehicle", unproved_conditions);

        System.out.println("Conclusion: "+conclusion);
    }

    private String showInputDialog(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question + " ");
        return scanner.next();
    }

    public void demoBackwardChainWithNullMemory()
    {
        RuleInferenceEngine rie=getInferenceEngine();

        System.out.println("Infer with All Facts Cleared:");
        rie.clearFacts();

        Vector<Clause> unproved_conditions= new Vector<>();

        Clause conclusion=null;
        while(conclusion==null)
        {
            conclusion=rie.infer("vehicle", unproved_conditions);
            if(conclusion==null)
            {
                if(unproved_conditions.size()==0)
                {
                    break;
                }
                Clause c=unproved_conditions.get(0);
                System.out.println("ask: "+c+"?");
                unproved_conditions.clear();
                String value=showInputDialog("What is "+c.getVariable()+"?");
                rie.addFact(new EqualsClause(c.getVariable(), value));
            }
        }

        System.out.println("Conclusion: "+conclusion);
        System.out.println("Memory: ");
        System.out.println(rie.getFacts());
    }

    @Test
    public void testForwardChain()
    {
        RuleInferenceEngine rie=getInferenceEngine();
        rie.addFact(new EqualsClause("num_wheels", "4"));
        rie.addFact(new EqualsClause("motor", "yes"));
        rie.addFact(new EqualsClause("num_doors", "3"));
        rie.addFact(new EqualsClause("size", "medium"));

        System.out.println("before inference");
        System.out.println(rie.getFacts());
        System.out.println();

        rie.infer(); //forward chain

        System.out.println("after inference");
        System.out.println(rie.getFacts());
        System.out.println();
    }

    @Test
    public void testAddFacts(){
        RuleInferenceEngine engine = new KieRuleInferenceEngine();
        engine.addFact("table.length.cm", ConditionType.Greater, "15");
        engine.addFact("table.width.cm", ConditionType.Less, "15");
        engine.addFact("table.height.cm", ConditionType.Equals, "115");
        engine.addFact("door.length.cm", ConditionType.GreaterEquals, "105");
        engine.addFact("door.width.cm", ConditionType.LessEquals, "85");
    }

    private RuleInferenceEngine getInferenceEngine()
    {
        RuleInferenceEngine rie=new KieRuleInferenceEngine();

        Rule rule=new Rule("Bicycle");
        rule.addAntecedent(new EqualsClause("vehicleType", "cycle"));
        rule.addAntecedent(new EqualsClause("num_wheels", "2"));
        rule.addAntecedent(new EqualsClause("motor", "no"));
        rule.setConsequent(new EqualsClause("vehicle", "Bicycle"));
        rie.addRule(rule);

        rule=new Rule("Tricycle");
        rule.addAntecedent(new EqualsClause("vehicleType", "cycle"));
        rule.addAntecedent(new EqualsClause("num_wheels", "3"));
        rule.addAntecedent(new EqualsClause("motor", "no"));
        rule.setConsequent(new EqualsClause("vehicle", "Tricycle"));
        rie.addRule(rule);

        rule=new Rule("Motorcycle");
        rule.addAntecedent(new EqualsClause("vehicleType", "cycle"));
        rule.addAntecedent(new EqualsClause("num_wheels", "2"));
        rule.addAntecedent(new EqualsClause("motor", "yes"));
        rule.setConsequent(new EqualsClause("vehicle", "Motorcycle"));
        rie.addRule(rule);

        rule=new Rule("SportsCar");
        rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
        rule.addAntecedent(new EqualsClause("size", "medium"));
        rule.addAntecedent(new EqualsClause("num_doors", "2"));
        rule.setConsequent(new EqualsClause("vehicle", "Sports_Car"));
        rie.addRule(rule);

        rule=new Rule("Sedan");
        rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
        rule.addAntecedent(new EqualsClause("size", "medium"));
        rule.addAntecedent(new EqualsClause("num_doors", "4"));
        rule.setConsequent(new EqualsClause("vehicle", "Sedan"));
        rie.addRule(rule);

        rule=new Rule("MiniVan");
        rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
        rule.addAntecedent(new EqualsClause("size", "medium"));
        rule.addAntecedent(new EqualsClause("num_doors", "3"));
        rule.setConsequent(new EqualsClause("vehicle", "MiniVan"));
        rie.addRule(rule);

        rule=new Rule("SUV");
        rule.addAntecedent(new EqualsClause("vehicleType", "automobile"));
        rule.addAntecedent(new EqualsClause("size", "large"));
        rule.addAntecedent(new EqualsClause("num_doors", "4"));
        rule.setConsequent(new EqualsClause("vehicle", "SUV"));
        rie.addRule(rule);

        rule=new Rule("Cycle");
        rule.addAntecedent(new LessClause("num_wheels", "4"));
        rule.setConsequent(new EqualsClause("vehicleType", "cycle"));
        rie.addRule(rule);

        rule=new Rule("Automobile");
        rule.addAntecedent(new EqualsClause("num_wheels", "4"));
        rule.addAntecedent(new EqualsClause("motor", "yes"));
        rule.setConsequent(new EqualsClause("vehicleType", "automobile"));
        rie.addRule(rule);

        return rie;
    }
}