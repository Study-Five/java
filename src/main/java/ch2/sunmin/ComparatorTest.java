package ch2.sunmin;

import java.util.Arrays;
import java.util.Comparator;

import ch2.sunmin.AppleTest.Apple;
import ch2.sunmin.AppleTest.Color;

public class ComparatorTest {
	
	
	public static void main(String[] args) {
		Apple[] appleList = {
                new Apple(Color.GREEN, 100),
                new Apple(Color.GREEN, 30),
                new Apple(Color.GREEN, 40),
                new Apple(Color.RED, 50),
                new Apple(Color.RED, 80),
                new Apple(Color.GREEN, 10)
            };
		
//		Arrays.asList(appleList).sort(new Comparator<Apple>() {
//			@Override
//			public int compare(Apple o1, Apple o2) {
//				return o1.getWeight().compareTo(o2.getWeight());
//			}			
//		});
		
		Arrays.asList(appleList).sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
		
		System.out.println(Arrays.asList(appleList));
		
	}
}
