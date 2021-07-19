package ch3.sunmin;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

import ch2.sunmin.AppleTest.Predicate;

public class FunctionInterfaceTest {
	
	public static void main(String[] args) {
		
		predicateTest();
		
		consumerTest();
		
		functionTest();
		
		boxingTest();
	}
	
	public static void predicateTest() {		
		// 파라미터로 받은 String s가 Empty 인지 확인하는 Predicate
		// return Boolean
		Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
		
		String[] predicateTestString = new String[] {"룰루랄라", "Blank?", ""};
		
		List<String> filterString = Arrays.stream(predicateTestString).filter(x -> nonEmptyStringPredicate.test(x)).collect(Collectors.toList());
		
		System.out.println(filterString);
	}
	
	public static void consumerTest() {
		
		// T 객체를 받아서 어떠한 동작을 하는 것		
		Arrays.asList(1, 2, 3, 4, 5).forEach((Integer i) -> System.out.println(i));
		
	}
	
	public static void functionTest() {
		
		List<Integer> l = Arrays.asList("lambdas", "in", "action")
								.stream()
								.map((String s) -> s.length()) // s 의 길이로 바꿔준다.
								.collect(Collectors.toList());
		
		System.out.println(l);
		
	}
	
	public static void boxingTest() {
		
		IntPredicate evenNumbers = (int i) -> i % 2 == 0;
		System.out.println(evenNumbers.test(1000)); // true (박싱없음)
		
		Predicate<Integer> oddNumbers = (Integer i) -> i % 2 != 0;
		System.out.println(oddNumbers.test(1000)); // false (박싱)		
		
	}

}
