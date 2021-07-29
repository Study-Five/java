package ch6.sunmin;

/**
 * 컬렉터 성능을 확인해보자
 */
public class CollectorHarness {
    public static void main(String[] args) {
        long fastest = Long.MAX_VALUE;
        for(int i=0; i<10; i++) { // 테스트 반복 횟수
            long start = System.nanoTime();
            Prime.partitionPrimeUsingCustomCollector(1_000_000);
            long duration = (System.nanoTime() - start) / 1_000_000; // ms 단위로 환산
            if (duration < fastest) fastest = duration;
        }
        System.out.println("Fastest execution done in " + fastest + " msecs");
    }
}
