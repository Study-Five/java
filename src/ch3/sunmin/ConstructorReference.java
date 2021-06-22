package ch3.sunmin;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import ch3.sunmin.AppleTest.Apple;
import ch3.sunmin.AppleTest.Color;

public class ConstructorReference {
	
	public static void main(String[] args) {
		
		// 인수없는 생성자
		Supplier<Apple> c1 = Apple::new;
		Apple a1 = c1.get();
		
		Supplier<Apple> c2 = () -> new Apple();
		Apple a2 = c2.get();
		
		// weight를 인수로 가지는 생성자
		Function<Integer, Apple> c3 = Apple::new;
		Apple a3 = c3.apply(100);
		
		// 두개의 인수를 가지는 생성자
		BiFunction<Color, Integer, Apple> c4 = Apple::new;
		Apple a4 = c4.apply(Color.GREEN, 110);
	
		
		
	}

}
