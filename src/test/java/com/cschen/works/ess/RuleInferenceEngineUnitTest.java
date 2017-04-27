package com.cschen.works.ess;

import org.testng.annotations.Test;

import java.util.Scanner;
import java.util.Vector;

import static org.testng.Assert.*;

/**
 * Created by memeanalytics on 27/4/17.
 */
public class RuleInferenceEngineUnitTest {

    @Test
    public void testBackwardChain()
    {
        RuleInferenceEngine rie=getInferenceEngine();
        rie.addFact(new IsClause("num_wheels", "4"));
        rie.addFact(new IsClause("motor", "yes"));
        rie.addFact(new IsClause("num_doors", "3"));
        rie.addFact(new IsClause("size", "medium"));

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
                rie.addFact(new IsClause(c.getVariable(), value));
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
        rie.addFact(new IsClause("num_wheels", "4"));
        rie.addFact(new IsClause("motor", "yes"));
        rie.addFact(new IsClause("num_doors", "3"));
        rie.addFact(new IsClause("size", "medium"));

        System.out.println("before inference");
        System.out.println(rie.getFacts());
        System.out.println();

        rie.infer(); //forward chain

        System.out.println("after inference");
        System.out.println(rie.getFacts());
        System.out.println();
    }

    private RuleInferenceEngine getInferenceEngine()
    {
        RuleInferenceEngine rie=new RuleInferenceEngine();

        Rule rule=new Rule("Bicycle");
        rule.addAntecedent(new IsClause("vehicleType", "cycle"));
        rule.addAntecedent(new IsClause("num_wheels", "2"));
        rule.addAntecedent(new IsClause("motor", "no"));
        rule.setConsequent(new IsClause("vehicle", "Bicycle"));
        rie.addRule(rule);

        rule=new Rule("Tricycle");
        rule.addAntecedent(new IsClause("vehicleType", "cycle"));
        rule.addAntecedent(new IsClause("num_wheels", "3"));
        rule.addAntecedent(new IsClause("motor", "no"));
        rule.setConsequent(new IsClause("vehicle", "Tricycle"));
        rie.addRule(rule);

        rule=new Rule("Motorcycle");
        rule.addAntecedent(new IsClause("vehicleType", "cycle"));
        rule.addAntecedent(new IsClause("num_wheels", "2"));
        rule.addAntecedent(new IsClause("motor", "yes"));
        rule.setConsequent(new IsClause("vehicle", "Motorcycle"));
        rie.addRule(rule);

        rule=new Rule("SportsCar");
        rule.addAntecedent(new IsClause("vehicleType", "automobile"));
        rule.addAntecedent(new IsClause("size", "medium"));
        rule.addAntecedent(new IsClause("num_doors", "2"));
        rule.setConsequent(new IsClause("vehicle", "Sports_Car"));
        rie.addRule(rule);

        rule=new Rule("Sedan");
        rule.addAntecedent(new IsClause("vehicleType", "automobile"));
        rule.addAntecedent(new IsClause("size", "medium"));
        rule.addAntecedent(new IsClause("num_doors", "4"));
        rule.setConsequent(new IsClause("vehicle", "Sedan"));
        rie.addRule(rule);

        rule=new Rule("MiniVan");
        rule.addAntecedent(new IsClause("vehicleType", "automobile"));
        rule.addAntecedent(new IsClause("size", "medium"));
        rule.addAntecedent(new IsClause("num_doors", "3"));
        rule.setConsequent(new IsClause("vehicle", "MiniVan"));
        rie.addRule(rule);

        rule=new Rule("SUV");
        rule.addAntecedent(new IsClause("vehicleType", "automobile"));
        rule.addAntecedent(new IsClause("size", "large"));
        rule.addAntecedent(new IsClause("num_doors", "4"));
        rule.setConsequent(new IsClause("vehicle", "SUV"));
        rie.addRule(rule);

        rule=new Rule("Cycle");
        rule.addAntecedent(new LessClause("num_wheels", "4"));
        rule.setConsequent(new IsClause("vehicleType", "cycle"));
        rie.addRule(rule);

        rule=new Rule("Automobile");
        rule.addAntecedent(new IsClause("num_wheels", "4"));
        rule.addAntecedent(new IsClause("motor", "yes"));
        rule.setConsequent(new IsClause("vehicleType", "automobile"));
        rie.addRule(rule);

        return rie;
    }
}