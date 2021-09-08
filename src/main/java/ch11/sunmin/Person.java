package ch11.sunmin;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Person {
    private Optional<Car> car;

    @Getter
    public static class Car {
        private Optional<Insurance> insurance;
    }
    @Getter
    public static class Insurance {
        private String name; // 필수로 값이 있을 경우에는 Optional 로 감싸지 않는다.
    }
}
