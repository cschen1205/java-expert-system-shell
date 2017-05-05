package com.cschen.works.ess.engine;

import com.cschen.works.ess.enums.IntersectionType;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by cschen on 29/4/17.
 */
public class LEClauseUnitTest {

    private LEClause clause = new LEClause("temperature", "10");

    @Test
    public void test_get(){
        assertThat(clause.getCondition()).isEqualTo("<=");
        assertThat(clause.getVariable()).isEqualTo("temperature");
        assertThat(clause.getValue()).isEqualTo("10");
    }

    @Test
    public void test_insect(){
        assertThat(clause.intersect(new EqualsClause("temperature", "10"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new EqualsClause("temperature", "9.9"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new EqualsClause("temperature", "11"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new LessClause("temperature", "11"))).isEqualTo(IntersectionType.Unknown);
        assertThat(clause.intersect(new LessClause("temperature", "9"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new GreaterClause("temperature", "10"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new GreaterClause("temperature", "9"))).isEqualTo(IntersectionType.Unknown);

        assertThat(clause.intersect(new Clause("Hello", "World"))).isEqualTo(IntersectionType.Unknown);
        assertThat(clause.intersect(new Clause("number", "2"))).isEqualTo(IntersectionType.Unknown);
    }

}