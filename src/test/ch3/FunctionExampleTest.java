package test.ch3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ch3.FunctionExample;

class FunctionExampleTest {

    private FunctionExample functionExample;

    @BeforeEach
    void setUp() {
        functionExample = new FunctionExample();
    }

    @Test
    void map() {
        List<Integer> l = functionExample.map(
            Arrays.asList("lambdas", "in", "action"),
            (String s) -> s.length()
        );
        System.out.println(l);
    }
}