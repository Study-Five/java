package ch3.sunmin;

import java.util.Arrays;
import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AppleTest {

	enum Color {RED, GREEN};
	
	@Getter @Setter @AllArgsConstructor
	@ToString
	@NoArgsConstructor
	static class Apple {
		private Color color;
		private Integer weight;
		
		public Apple(Integer weight) {
			this.weight = weight;
		}
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
		
		// 기존 방법 : 익명 클래스로 Comparator 인터페이스 구현
		Comparator<Apple> byWeight = new Comparator<Apple>() {
			public int compare(Apple a1, Apple a2) {
				return a1.getWeight().compareTo(a2.getWeight());
			}
		};
		
		Arrays.stream(appleList).sorted(byWeight).forEach((apple) -> System.out.print(apple + ","));
		
		System.out.println();
		
		// 람다 이용		
		Comparator<Apple> byWeightLambda = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
		
		Arrays.stream(appleList).sorted(byWeightLambda).forEach((apple) -> System.out.print(apple + ","));
		
	}
	
}
