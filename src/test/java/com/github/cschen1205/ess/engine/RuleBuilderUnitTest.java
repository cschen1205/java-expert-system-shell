package com.github.cschen1205.ess.engine;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by cschen on 1/5/17.
 */
public class RuleBuilderUnitTest {

    private RuleBuilder builder;
    private KieRuleInferenceEngine ruleInferenceEngine;
    private Rule rule1;
    private Rule rule2;

    @BeforeMethod
    public void setUp(){
        ruleInferenceEngine = Mockito.mock(KieRuleInferenceEngine.class);
        builder = new RuleBuilder(ruleInferenceEngine);

        rule1 = builder.ifGE("temperature", "22")
                .andLE("temperature", "27")
                .thenEquals("weather", "nice")
                .build();

        rule2 = builder.ifGreater("temperature", "22")
                .andLess("temperature", "27")
                .build();
    }

    @Test
    public void test_rule1_fire() {
        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.given("temperature", "22");

        assertTrue(rule1.isTriggered(workingMemory));
    }

    @Test
    public void test_rule1_not_fire() {
        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.given("temperature", "34");

        assertFalse(rule1.isTriggered(workingMemory));
    }

    @Test
    public void test_rule2_fire() {
        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.given("temperature", "23");

        assertTrue(rule2.isTriggered(workingMemory));
    }

    @Test
    public void test_rule2_not_fire() {
        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.given("temperature", "22");

        assertFalse(rule2.isTriggered(workingMemory));
    }

    @Test
    public void test_all_other_cases(){
        new RuleBuilder(ruleInferenceEngine).ifNotEquals("x", "2.0")
                .andNotEquals("x", "3.0")
                .andGreater("x", "3.0")
                .andLess("x", "4.0")
                .thenGreater("x", "3.0");
        new RuleBuilder(ruleInferenceEngine).ifLess("x", "2.0")
                .thenLess("y", "4.0");
        new RuleBuilder(ruleInferenceEngine).ifMatch("x", "^s")
                .andMatch("y", "^s")
                .thenMatch("y", "^s");
        new RuleBuilder(ruleInferenceEngine).ifNotMatch("x", "^s")
                .andNotMatch("y", "^s")
                .thenNotMatch("y", "^s");
        new RuleBuilder(ruleInferenceEngine).ifLE("x", "2.0")
                .thenLE("x", "3.0");
        new RuleBuilder(ruleInferenceEngine).ifGE("x", "3.0")
                .thenGE("x", "3.0");
    }
}