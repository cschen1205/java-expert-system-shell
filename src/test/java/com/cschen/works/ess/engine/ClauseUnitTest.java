package com.cschen.works.ess.engine;

import com.cschen.works.ess.enums.IntersectionType;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;

/**
 * Created by memeanalytics on 4/5/17.
 */
public class ClauseUnitTest {

    @Test
    public void test_clause(){
        Clause c1 = new Clause();
        assertEquals(c1.getCondition(), "=");
        assertEquals(c1.getValue(), "");
        assertEquals(c1.getVariable(), "");

        Clause c2 = new Clause("hello", "world");
        assertEquals(c2.getCondition(), "=");
        assertEquals(c2.getVariable(), "hello");
        assertEquals(c2.getValue(),"world");

        Clause c3 = new Clause("hello", "=", "world");
        assertEquals(c3.getCondition(), "=");
        assertEquals(c3.getVariable(), "hello");
        assertEquals(c3.getValue(), "world");

        c3.setVariable("hi");
        c3.setValue("there");
        c3.setCondition("=>");

        assertEquals(c3.getValue(), "there");
        assertEquals(c3.getVariable(), "hi");
        assertEquals(c3.getCondition(), "=>");
    }

    @Test
    public void test_clause_not_match() {
        Clause c1 = new Clause("c1", "value");
        Clause c2 = new Clause("c2", "value");

        assertThat(c1.matchClause(c2)).isEqualTo(IntersectionType.Unknown);
    }
}