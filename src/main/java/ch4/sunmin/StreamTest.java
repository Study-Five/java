package ch4.sunmin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

	public static void main(String[] args) {
		
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", false, 530, Dish.Type.OTHER),
				new Dish("rice", false, 350, Dish.Type.OTHER),
				new Dish("season fruit", false, 120, Dish.Type.OTHER),
				new Dish("pizza", false, 550, Dish.Type.OTHER),
				new Dish("prawans", false, 300, Dish.Type.FISH),
				new Dish("salmon", false, 450, Dish.Type.FISH));
		

		getLowCaloricDishesName(menu);
		getLowCaloricDishesNameLambda(menu);
		getCaloricDishesLimited(menu);
		
	}
	
	private static void getLowCaloricDishesName(List<Dish> menu) {
		// 400 kCal 이하인 Dish 추출
		List<Dish> lowCaloricDishes = new ArrayList<>();
		for(Dish dish : menu) {
			if(dish.getCalories() < 400) {
				lowCaloricDishes.add(dish);
			}
		}
		
		// 칼로리대로 Sort
		Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
			@Override
			public int compare(Dish o1, Dish o2) {
				return Integer.compare(o1.getCalories(), o2.getCalories());
			}				
		});
		
		// 정렬된 리스트들의 요리이름을 선택
		List<String> lowCaloricDishesName = new ArrayList<String>();
		for(Dish dish : lowCaloricDishes) {
			lowCaloricDishesName.add(dish.getName());
		}
		System.out.println(lowCaloricDishesName);
	}
	
	private static void getLowCaloricDishesNameLambda(List<Dish> menu) {
		
		List<String> lowCaloricDishesName = menu.stream()
												.filter(d -> d.getCalories() < 400)
												.sorted(Comparator.comparing(Dish::getCalories))
												.map(Dish::getName)
												.collect(Collectors.toList());
		
		System.out.println(lowCaloricDishesName);
		
	}
	
	private static void getCaloricDishesLimited(List<Dish> menu) {
		
		
		List<String> threeHighCaloricDishNames = menu.stream()
													 .filter(dish -> dish.getCalories() > 300)
													 .map(Dish::getName)
													 .limit(3)
													 .collect(Collectors.toList());
		
		System.out.println(threeHighCaloricDishNames);
		
	}
	
}
