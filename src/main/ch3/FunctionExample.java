package main.ch3;

import java.util.ArrayList;
import java.util.List;

public class FunctionExample {

    @FunctionalInterface
    public interface Function<T, R> {
        R apply(T t);
    }

    public<T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }
}
