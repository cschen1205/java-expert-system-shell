package com.cschen.works.ess.engine;

import com.cschen.works.ess.enums.IntersectionType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;

/**
 * Created by cschen on 29/4/17.
 */
public class EqualsClauseUnitTest {

    private EqualsClause clause;
    private EqualsClause numericClause;

    @BeforeMethod
    public void setUp(){
        clause = new EqualsClause("name", "Hello World");
        numericClause = new EqualsClause("temperature", "10");


    }

    @Test
    public void test_get(){
        assertThat(clause.getVariable()).isEqualTo("name");
        assertThat(clause.getValue()).isEqualTo("Hello World");
    }

    @Test
    public void test_intersect() {
        GEClause greaterOrEqualTo10 = new GEClause("temperature", "10");
        GreaterClause greaterThan10 = new GreaterClause("temperature", "10");
        NegationClause notGreaterThan10 = new NegationClause(greaterThan10);
        NegationClause notGreaterAndNotEqualTo10 = new NegationClause(greaterOrEqualTo10);

        GEClause lessOrEqualTo10 = new GEClause("temperature", "10");
        GreaterClause lessThan10 = new GreaterClause("temperature", "10");


        assertThat(numericClause.intersect(notGreaterThan10)).isEqualTo(IntersectionType.Inclusive);
        assertThat(numericClause.intersect(notGreaterAndNotEqualTo10)).isEqualTo(IntersectionType.MutuallyExclusive);

        assertThat(numericClause.intersect(greaterOrEqualTo10)).isEqualTo(IntersectionType.Inclusive);
        assertThat(numericClause.intersect(greaterThan10)).isEqualTo(IntersectionType.MutuallyExclusive);

        assertThat(numericClause.intersect(lessOrEqualTo10)).isEqualTo(IntersectionType.Inclusive);
        assertThat(numericClause.intersect(lessThan10)).isEqualTo(IntersectionType.MutuallyExclusive);

        RegexMatchClause matchHello = new RegexMatchClause("name", "^.*Hello.*$");
        RegexMatchClause matchHi = new RegexMatchClause("name", "^.*Hi.*$");

        assertThat(clause.intersect(matchHello)).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(matchHi)).isEqualTo(IntersectionType.MutuallyExclusive);

        assertThat(clause.intersect(new Clause("Hello", "World"))).isEqualTo(IntersectionType.Unknown);
        assertThat(clause.intersect(new Clause("number", "2"))).isEqualTo(IntersectionType.Unknown);
    }


}