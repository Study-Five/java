package ch3.sunmin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ch3.sunmin.AppleTest.Apple;
import ch3.sunmin.AppleTest.Color;

public class MethodReference {
	
	public static void main(String[] args) {
		Apple[] appleList = {
                new Apple(Color.GREEN, 100),
                new Apple(Color.GREEN, 30),
                new Apple(Color.GREEN, 40),
                new Apple(Color.RED, 50),
                new Apple(Color.RED, 80),
                new Apple(Color.GREEN, 10)
            };
		
		// 기존코드
		Arrays.stream(appleList).sorted((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
		
		// 메서드 참조활용
		Arrays.stream(appleList).sorted(Comparator.comparing(Apple::getWeight));
		

	}
	
	/**
	 * 인스턴스 메서드
	 */
	public void validNames() {
		String[] string_list = new String[] {"Lian", "judy"};
		List<String> validNames = Arrays.stream(string_list).filter(this::isValidName).collect(Collectors.toList());
		
		System.out.println(validNames);
	}
	
	/**
	 * private helper method
	 * @param s
	 * @return
	 */
	private boolean isValidName(String s) {
		return Character.isUpperCase(s.charAt(0));
	}

}
