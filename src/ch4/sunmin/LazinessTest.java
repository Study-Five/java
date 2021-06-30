package ch4.sunmin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LazinessTest {
	
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
		
		List<String> names = menu.stream()
							     .filter(dish -> {
							    	 System.out.println("filtering:" + dish.getName());
							    	 return dish.getCalories() > 300;
							     })
							     .map(dish -> {
							    	 System.out.println("mapping:" + dish.getName());
							    	 return dish.getName();
							     })
							     .limit(3)
							     .collect(Collectors.toList());
		
		System.out.println(names);
	}

}
