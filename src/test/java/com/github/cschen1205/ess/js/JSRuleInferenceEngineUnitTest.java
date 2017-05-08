package com.github.cschen1205.ess.js;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by cschen on 28/4/17.
 */
public class JSRuleInferenceEngineUnitTest {
    private JSRuleInferenceEngine engine;

    @BeforeTest
    public void setup() throws ScriptException {
        engine = new JSRuleInferenceEngine();

    }

    @Test
    public void testLoadFile() throws IOException, ScriptException {
        String jsContent = IOUtil.readToEnd("js/vehicle-rules.js");
        Files.write(Paths.get("/tmp/rules.js"), Collections.singletonList(jsContent));
        engine.loadFile("/tmp/rules.js");

        engine.buildRules();

        engine.clearFacts();

        engine.addFact("num_wheels", "4");
        engine.addFact("motor", "yes");
        engine.addFact("num_doors", "3");
        engine.addFact("size", "medium");



        System.out.println("before inference");
        System.out.println(engine.getKnowledgeBase());
        System.out.println();



        engine.infer(); //forward chain

        System.out.println("after inference");
        System.out.println(engine.getKnowledgeBase());
        System.out.println();

    }



    @Test
    public void testVehicleScenario() throws ScriptException {

        String jsContent = IOUtil.readToEnd("js/vehicle-rules.js");
        engine.loadString(jsContent);

        engine.buildRules();

        engine.clearFacts();

        engine.addFact("num_wheels", "4");
        engine.addFact("motor", "yes");
        engine.addFact("num_doors", "3");
        engine.addFact("size", "medium");



        System.out.println("before inference");
        System.out.println(engine.getKnowledgeBase());
        System.out.println();



        engine.infer(); //forward chain

        System.out.println("after inference");
        System.out.println(engine.getKnowledgeBase());
        System.out.println();
    }

    @Test
    public void testRun() throws ScriptException {
        JSRuleInferenceEngine another = new JSRuleInferenceEngine();

        String jsContent = IOUtil.readToEnd("js/classify-vehicle.js");
        another.run(jsContent);


    }

    @Test
    public void testRegexMatch() throws ScriptException {
        JSRuleInferenceEngine another = new JSRuleInferenceEngine();

        String jsContent = IOUtil.readToEnd("js/regex-match.js");
        another.run(jsContent);
    }

}