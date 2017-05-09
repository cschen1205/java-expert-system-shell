package com.github.cschen1205.ess.engine;

import com.github.cschen1205.ess.enums.IntersectionType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;

/**
 * Created by cschen on 1/5/17.
 */
public class RegexMatchClauseUnitTest {

    private static final String regex4Url = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private RegexMatchClause clause;
    @BeforeMethod
    public void setUp(){
        clause = new RegexMatchClause("is-url", regex4Url);
    }

    @Test
    public void test_match(){
        assertTrue(clause.match("http://www.google.com"));
        assertFalse(clause.match("google-com"));
    }

    @Test
    public void test_intersect() {
        assertThat(clause.intersect(new EqualsClause("url", "http://www.google.com"))).isEqualTo(IntersectionType.Inclusive);
        assertThat(clause.intersect(new EqualsClause("url", "google-com"))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(new NegationClause(new EqualsClause("url", "http://www.google.com")))).isEqualTo(IntersectionType.MutuallyExclusive);
        assertThat(clause.intersect(clause)).isEqualTo(IntersectionType.Inclusive);
    }

}