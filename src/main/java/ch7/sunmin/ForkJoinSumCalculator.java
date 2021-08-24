package ch7.sunmin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers; // 더할 숫자 배열
    private final int start; // 이 서브태스크에서 처리할 배열의 초기위치와 최종위치
    private final int end;
    private static final long THRESHOLD = 10_000; // 이 값 이하의 서브태스크는 더 이상 분할할 수 없다.

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }
    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start; // 이 태스크에서 더할 배열의 길이
        if(length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
        leftTask.fork(); // ForkJoinPool의 다른 스레드로 새로 생성한 태스크를 비동기로 실행한다.

        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);

        Long rightResult = rightTask.compute(); // 두 번째 서브태스크를 동기 실행한다. 이때 추가로 분할이 일어날 수 있다.
        Long leftResult = leftTask.join(); // 첫번째 서브태스크의 결과를 읽거나 아직 결과가 없으면 기다린다.

        return leftResult + rightResult;

    }

    private long computeSequentially() {
        long sum = 0;
        for(int i=start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
