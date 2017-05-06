package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by cschen on 29/4/17.
 */
public class LessClauseUnitTest {

    private LessClause clause = new LessClause("temperature", "10");

    @Test
    public void test_get(){
        assertThat(clause.getCondition()).isEqualTo("<");
        assertThat(clause.getVariable()).isEqualTo("temperature");
        assertThat(clause.getValue()).isEqualTo("10");
    }

    @Test
    public void test_insect(){
        assertThat(clause.intersect(new EqualsClause("temperature", "10"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new EqualsClause("temperature", "9.9"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new EqualsClause("temperature", "11"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new LessClause("temperature", "11"))).isEqualTo(IntersectionType.Unknown);
        assertThat(clause.intersect(new LessClause("temperature", "9"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new GreaterClause("temperature", "10"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new GreaterClause("temperature", "9"))).isEqualTo(IntersectionType.Unknown);

        assertThat(clause.intersect(new GEClause("temperature", "12"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new GEClause("temperature", "9"))).isEqualTo(IntersectionType.Unknown);

        assertThat(clause.intersect(new NegationClause(new LEClause("temperature", "10")))).isEqualTo(IntersectionType.MutuallyExclusive);

        assertThat(clause.intersect(new Clause("Hello", "World"))).isEqualTo(IntersectionType.Unknown);
        assertThat(clause.intersect(new Clause("number", "2"))).isEqualTo(IntersectionType.Unknown);
    }

}