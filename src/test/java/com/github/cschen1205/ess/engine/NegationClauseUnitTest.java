package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by cschen on 30/4/17.
 */
public class NegationClauseUnitTest {

    @Test
    public void test_intersect(){
        GreaterClause greaterThan = new GreaterClause("temperature", "10");
        LessClause lessThan = new LessClause("temperature", "10");
        GEClause greaterThanOrEqualTo = new GEClause("temperature", "10");
        LEClause lessThanOrEqualTo = new LEClause("temperature", "10");
        assertThat(new NegationClause(greaterThan).intersect(greaterThan)).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(new NegationClause(lessThan).intersect(lessThan)).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(new NegationClause(greaterThanOrEqualTo).intersect(greaterThanOrEqualTo)).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(new NegationClause(lessThanOrEqualTo).intersect(lessThanOrEqualTo)).isEqualTo(IntersectionType.MutuallyExclusive);

        assertThat(new NegationClause(greaterThan).intersect(lessThanOrEqualTo)).isEqualTo(IntersectionType.Inclusive);
        assertThat(new NegationClause(lessThan).intersect(greaterThanOrEqualTo)).isEqualTo(IntersectionType.Inclusive);

        AssertionsForInterfaceTypes.assertThat(new NegationClause(lessThan).intersect(new Clause("Hello", "World"))).isEqualTo(IntersectionType.Unknown);
        AssertionsForInterfaceTypes.assertThat(new NegationClause(lessThan).intersect(new Clause("number", "2"))).isEqualTo(IntersectionType.Unknown);
    }
}