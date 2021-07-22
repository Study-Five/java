package ch6.sunmin;

import ch4.sunmin.Dish;

import java.util.*;
import java.util.stream.Collectors;

public class StremGrouping {

    public enum CaloricLevel {DIET, NORMAL, FAT}

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

        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(Collectors.groupingBy(Dish::getCaloricLevel));

        Map<Dish.Type, List<String>> dishNameByType = menu.stream()
                                                          .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));

        System.out.println(dishNameByType);

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(
                Collectors.groupingBy(Dish::getType, // 첫 번째 수준의 분류 함수
                        Collectors.groupingBy(dish -> { // 두 번째 수준의 분류함수
                            if(dish.getCalories() <= 400)
                                return CaloricLevel.DIET;
                            else if(dish.getCalories() <= 700)
                                return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                        )
        );

        System.out.println(dishesByTypeCaloricLevel);

        Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
                                                     .collect(Collectors.groupingBy(Dish::getType // 1. Type 별로 그룹핑
                                                                                    ,Collectors.collectingAndThen(
                                                                                            Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)) // 2-1. 그룹된 결과를 macBy로 감싼다.
                                                                                            ,Optional::get))); // 2-2. finishing


    }
}
