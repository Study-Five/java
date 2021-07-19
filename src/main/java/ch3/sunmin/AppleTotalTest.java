package ch3.sunmin;

import java.util.Arrays;
import java.util.Comparator;

import ch3.sunmin.AppleTest.Apple;
import ch3.sunmin.AppleTest.Color;

/**
 * 동작 파라미터화, 익명 클래스, 람다 표현식, 메서드 참조 를 총 동원한 실습예제
 * @author eldorado
 *
 */
public class AppleTotalTest {
	
	public static class AppleComparator implements Comparator<Apple> {
		@Override
		public int compare(Apple o1, Apple o2) {
			return o1.getWeight().compareTo(o2.getWeight());
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
		
		// 1. 동작 파라미터화
		Arrays.stream(appleList).sorted(new AppleComparator());
		
		// 2. 익명 클래스 사용
		Arrays.stream(appleList).sorted(new Comparator<Apple>() {
			@Override
			public int compare(Apple o1, Apple o2) {
				return o1.getWeight().compareTo(o2.getWeight());
			}			
		});
		
		// 3. 람다 표현식 사용
		Arrays.stream(appleList).sorted((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
		
		// 3-1. comparing 활용
		Arrays.stream(appleList).sorted(Comparator.comparing(apple -> apple.getWeight()));
		
		// 4. 메서드 참조 사용
		Arrays.stream(appleList).sorted(Comparator.comparing(Apple::getWeight));
		
	}

}
