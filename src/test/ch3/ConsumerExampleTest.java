package test.ch3;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.ch3.ConsumerExample;

class ConsumerExampleTest {

    private ConsumerExample consumerExample;

    @BeforeEach
    void setUp() {
        consumerExample = new ConsumerExample();
    }

    @Test
    void forEach() {
        consumerExample.forEach(Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println(i));
    }
}