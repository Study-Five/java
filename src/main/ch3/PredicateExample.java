package main.ch3;

import java.util.ArrayList;
import java.util.List;

public class PredicateExample {

    @FunctionalInterface
    public interface Predicate<T> {
        boolean test(T t);
    }

    public<T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T t : list) {
            if(p.test(t)) {
                results.add(t);
            }
        }
        return results;
    }
}
