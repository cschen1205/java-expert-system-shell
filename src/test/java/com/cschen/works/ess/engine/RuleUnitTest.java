package com.cschen.works.ess.engine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;

/**
 * Created by cschen on 1/5/17.
 */
public class RuleUnitTest {

    private Rule rule;

    @BeforeMethod
    public void setUp(){
        rule = new Rule("If temperature is higher than 30 degrees, it is uncomfortable");
        rule.addAntecedent(new GreaterClause("temperature", "30"));
        rule.setConsequent(new EqualsClause("human-comfort-level", "uncomfortable"));
    }

    @Test
    public void test_fire_success(){


        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.addFact(new EqualsClause("temperature", "34"));

        assertTrue(rule.isTriggered(workingMemory));

        rule.fire(workingMemory);

        assertTrue(rule.isFired());
        assertThat(workingMemory.getFacts()).contains(rule.getConsequent());
    }

    @Test
    public void test_fire_failure(){

        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.addFact(new EqualsClause("temperature", "23"));

        assertFalse(rule.isTriggered(workingMemory));

    }
}