package ch6.sunmin;

import ch4.sunmin.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamPartitioning {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("season fruit", false, 120, Dish.Type.OTHER),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", false, 530, Dish.Type.OTHER),
                new Dish("rice", false, 350, Dish.Type.OTHER),
                new Dish("pizza", false, 550, Dish.Type.OTHER),
                new Dish("prawans", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        // 1. Partitioning 예제
        Map<Boolean, List<Dish>> isVegeterian = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        Map<Boolean, Map<Dish.Type, List<Dish>>> isVegeterianNgrouping = menu.stream()
                                                                             .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));

        // 2. 다수준 Partitioning
        Map<Boolean, Map<Boolean, List<Dish>>> quiz1 = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian // 1차 분할
                                                                                                     , Collectors.partitioningBy(d -> d.getCalories() > 500))); // 2차분할
        System.out.println(quiz1);
    }
}
