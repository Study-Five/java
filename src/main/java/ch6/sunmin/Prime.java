package ch6.sunmin;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Prime {
    public static void main(String[] args) {
        Map<Boolean, List<Integer>> partitionPrimeMap = partitionPrime(10);
        System.out.println(partitionPrimeMap);

        // 커스텀 컬렉터로 구현한 소수나누기
        Map<Boolean, List<Integer>> partitionPrimeCustomMap = partitionPrimeUsingCustomCollector(10);
        System.out.println(partitionPrimeCustomMap);
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate); // 최적화를 위해 제곱근까지만 반복 (1/2 으로 줄여버림)
        return IntStream.rangeClosed(2, candidateRoot)
                        .noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrime(int n) {
        return IntStream.range(2, n).boxed()
                        .collect(Collectors.partitioningBy(x -> isPrime(x)));
    }

    public static Map<Boolean, List<Integer>> partitionPrimeUsingCustomCollector(int n) {
        return IntStream.range(2, n).boxed()
                        .collect(new PrimeNumbersCollector());
    }

    /**
     * 자바 9부터 지원되는 takeWhile 메서드 직접 구현
     * @param list
     * @param predicate
     * @param <A>
     * @return
     */
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> predicate) {
        int i = 0;
        for(A item : list) {
            if(!predicate.test(item)){
                return list.subList(0, i);
            }
            i ++;
        }
        return list;
    }
}
