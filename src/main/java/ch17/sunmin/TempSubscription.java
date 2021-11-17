package ch17.sunmin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class TempSubscription implements Flow.Subscription {

    private final Flow.Subscriber<? super TempInfo> subscriber;
    private final String town;

    public TempSubscription(Flow.Subscriber<? super TempInfo> subscriber, String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    /**
     * subscriber 가 request를 보내고 onNext()로 또 다시 request 를 보내면서
     * StackOverFlow 가 될 때까지 반복되므로, 해당 요청을 다른 스레드가 처리하도록 바꾼다.
     */
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void request(long n) {
        executor.submit(() -> { // 다른 스레드에서 다음 요소를 구독자에게 보낸다.
            for(long i=0L; i < n; i++) {
                try {
                    subscriber.onNext(TempInfo.fetch(town));
                } catch (Exception e) {
                    subscriber.onError(e);
                    break;
                }
            }
        });
    }

    @Override
    public void cancel() {
        subscriber.onComplete(); // 구독이 취소되면 complete
    }
}
