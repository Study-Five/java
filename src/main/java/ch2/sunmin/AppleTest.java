package ch2.sunmin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class AppleTest {

	enum Color {RED, GREEN};
	
	@Getter @Setter @AllArgsConstructor
	@ToString
	static class Apple {
		private Color color;
		private Integer weight;
	}
	
	/**
	 * 고전적인 방법 : for 과 if 를 사용해서 리스트에 담아서 리턴
	 * 빨간 사과를 필터링 하려면 filterRedApples 메서드를 또 만들어야 함
	 * @param inventory
	 * @return
	 */
	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory) {
			if(Color.GREEN.equals(apple.getColor())) { // 하드코딩
				result.add(apple);
			}
		}
		return result;
		
	}
	
	/**
	 * 고전적인 방법2 : Color를 파라미터로 넘김
	 * 하지만, 칼러가 아니라 무게로 필터링을 해야하면 또 filterApplesByWeight 라는 메서드를 만들어야 된다.
	 * @param inventory
	 * @param color
	 * @return
	 */
	public static List<Apple> filterGreenApples(List<Apple> inventory, Color color) {
		
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory) {
			if(apple.getColor().equals(color)) {
				result.add(apple);
			}
		}
		return result;
		
	}
	
	/**
	 * 플래그 값으로 가능한 모든 속성으로 필터링
	 * 정말 좋지 않은 방법이다. 다른속성이 들어오면 또 어떻게 할 것인가
	 * @param inventory
	 * @param color
	 * @param weight
	 * @param flag
	 * @return
	 */
	public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
		
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory) {
			if((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
				result.add(apple);
			}
		}
		return result;
		
	}
	
	
	/**
	 * Predicate 선언
	 * @author eldorado
	 *
	 */
	public interface ApplePredicate {
		boolean test(Apple apple);
	}
	
	/**
	 * 필터조건 1 : 무거운 사과만 선택
	 * @author eldorado
	 *
	 */
	public class AppleHeavyWeightPredicate implements ApplePredicate {
		@Override
		public boolean test(Apple apple) {
			return apple.getWeight() > 80;
		}		
	}
	
	/**
	 * 필터조건 2 : 초록 사과만 선택
	 * @author eldorado
	 *
	 */
	public static class AppleGreenColorPredicate implements ApplePredicate {
		@Override
		public boolean test(Apple apple) {
			return Color.GREEN.equals(apple.getColor());
		}		
	}
	
	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory) {
			if(p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}
	
	/**
	 * 제네릭을 사용한 Predicate
	 * @author eldorado
	 *
	 * @param <T>
	 */
	public interface Predicate<T> {
		boolean test(T t);
	}
	
	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for(T e: list) {
			if(p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Apple[] appleList = {
                new Apple(Color.GREEN, 100),
                new Apple(Color.GREEN, 30),
                new Apple(Color.GREEN, 40),
                new Apple(Color.RED, 50),
                new Apple(Color.RED, 80),
                new Apple(Color.GREEN, 10)
            };
		
		System.out.println(filterGreenApples(Arrays.asList(appleList)));
		System.out.println(filterGreenApples(Arrays.asList(appleList), Color.RED));
		
		System.out.println(filterApples(Arrays.asList(appleList), Color.GREEN, 0, true));
		System.out.println(filterApples(Arrays.asList(appleList), null, 50, false));
		
		System.out.println(filterApples(Arrays.asList(appleList), new AppleGreenColorPredicate()));
		System.out.println(filterApples(Arrays.asList(appleList), new ApplePredicate() {			
			@Override
			public boolean test(Apple apple) {
				return Color.RED.equals(apple.getColor());
			}
		}));
		
		System.out.println(filterApples(Arrays.asList(appleList), (Apple apple) -> Color.RED.equals(apple.getColor())));
		
	}
	
}
