package com.cschen.works.ess.engine;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by memeanalytics on 1/5/17.
 */
public class RuleBuilderUnitTest {

    private RuleBuilder builder;
    private KieRuleInferenceEngine ruleInferenceEngine;
    private Rule rule;

    @BeforeMethod
    public void setUp(){
        ruleInferenceEngine = Mockito.mock(KieRuleInferenceEngine.class);
        builder = new RuleBuilder(ruleInferenceEngine);

        rule = builder.ifGE("temperature", "22")
                .andLE("temperature", "27")
                .thenEquals("weather", "nice")
                .build();
    }

    @Test
    public void test_fire() {
        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.given("temperature", "24");

        assertTrue(rule.isTriggered(workingMemory));
    }

    @Test
    public void test_not_fire() {
        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.given("temperature", "34");

        assertFalse(rule.isTriggered(workingMemory));
    }
}