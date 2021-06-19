package main.ch3;

import java.util.List;

public class ConsumerExample {

    @FunctionalInterface
    public interface Consumer<T> {
        void accept(T t);
    }

    public<T> void forEach(List<T> list, Consumer<T> c) {
        for(T t : list) {
            c.accept(t);
        }
    }
}
