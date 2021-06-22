package ch3.sunmin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import ch3.sunmin.AppleTest.Apple;
import ch3.sunmin.AppleTest.Color;

public class LambdaComposite {

	public static void main(String[] args) {
		Apple[] appleList = {
                new Apple(Color.GREEN, 100),
                new Apple(Color.GREEN, 30),
                new Apple(Color.GREEN, 40),
                new Apple(Color.RED, 50),
                new Apple(Color.RED, 80),
                new Apple(Color.GREEN, 10)
            };
		
		Arrays.stream(appleList).sorted(Comparator.comparing(Apple::getWeight)
												  .reversed() // 무게를 내림차순으로 정렬
												  .thenComparing(Apple::getColor)); // 두 사과의 무게가 같으면 칼라별로 정렬
		
		Predicate<Apple> redApple = (a -> Color.RED.equals(a.getColor())); // 빨간 사과만 true로 반환하는 Predicate
		Predicate<Apple> notRedApple = redApple.negate(); // 반전해버림
		
		Predicate<Apple> readAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
		
		Function<Integer, Integer> f = x -> x + 1;
		Function<Integer, Integer> g = x -> x * 2;
		Function<Integer, Integer> h = f.andThen(g);
		
		int result = h.apply(1); // 4를 반환
		
	}
	
}
