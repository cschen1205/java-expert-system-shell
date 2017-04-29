package com.cschen.works.ess.engine;

import com.cschen.works.ess.enums.IntersectionType;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;

/**
 * Created by memeanalytics on 29/4/17.
 */
public class GEClauseUnitTest {

    private GEClause clause = new GEClause("temperature", "10");

    @Test
    public void test_get(){
        assertThat(clause.getCondition()).isEqualTo(">=");
        assertThat(clause.getVariable()).isEqualTo("temperature");
        assertThat(clause.getValue()).isEqualTo("10");
    }

    @Test
    public void test_insect(){
        assertThat(clause.intersect(new EqualsClause("temperature", "10"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new EqualsClause("temperature", "11"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new EqualsClause("temperature", "9.9"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new GreaterClause("temperature", "9"))).isEqualTo(IntersectionType.Unknown);
        assertThat(clause.intersect(new GreaterClause("temperature", "11"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new LessClause("temperature", "10"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new LessClause("temperature", "11"))).isEqualTo(IntersectionType.Unknown);
    }

}