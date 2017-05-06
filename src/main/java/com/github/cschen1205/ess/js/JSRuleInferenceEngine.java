package com.github.cschen1205.ess.js;

import com.github.cschen1205.ess.engine.*;
import com.github.cschen1205.ess.enums.ConditionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.io.*;
import java.util.List;

/**
 * Created by chen0 on 7/6/2016.
 */
public class JSRuleInferenceEngine {

    private static Logger logger = LoggerFactory.getLogger(JSRuleInferenceEngine.class);

    private CompiledScript cs;
    private Bindings params;

    private RuleInferenceEngine ruleEngine = new KieRuleInferenceEngine();

    public void addFact(String name, Object value){
        ruleEngine.addFact(name, value.toString());
    }

    public void addFact(String name, ConditionType conditionType, Object value){
        ruleEngine.addFact(name, conditionType, value.toString());
    }

    public RuleBuilder newRule(String name){
        return ruleEngine.newRule(name);
    }

    public RuleBuilder newRule(){
        return ruleEngine.newRule();
    }

    public void infer(){
        ruleEngine.infer();
    }

    public List<Clause> getFacts(String variable){
        return ruleEngine.getFactsAboutVariable(variable);
    }

    public boolean loadFile(String filepath) throws ScriptException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                sb.append(line + "\n");
            }
            reader.close();
            return loadString(sb.toString());
        } catch (FileNotFoundException e) {
            logger.error("File not found: "+filepath, e);
        } catch (IOException e) {
            logger.error("Failed to load file: "+filepath, e);
        }
        return false;
    }

    public boolean loadString(String js_content) throws ScriptException {
        boolean success = true;

        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("nashorn");

            Compilable comp = (Compilable)engine;
            cs = comp.compile(js_content);

            this.params = engine.createBindings();
            this.params.put("expert", this);
        }
        catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            success = false;
        }
        return success;
    }

    public void clearFacts(){
        ruleEngine.clearFacts();
    }

    public void buildRules() throws ScriptException {
        ruleEngine.clearRules();
        cs.eval(params);
    }

    public WorkingMemory getKnowledgeBase() {
        return ruleEngine.getKnowledgeBase();
    }

    public void run(String jsContent) throws ScriptException {
        loadString(jsContent);
        cs.eval(params);
    }
}
