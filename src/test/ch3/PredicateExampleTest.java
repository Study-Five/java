package test.ch3;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ch3.PredicateExample;

class PredicateExampleTest {

    private PredicateExample predicateExample;

    @BeforeEach
    void setUp() {
        predicateExample = new PredicateExample();
    }

    @Test
    void filter() {
        PredicateExample.Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> listOfStrings = List.of("a", "b", "", "  ");
        List<String> nonEmpty = predicateExample.filter(listOfStrings, nonEmptyStringPredicate);
        System.out.println(nonEmpty);
    }
}