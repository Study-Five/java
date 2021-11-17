package ch17.sunmin;

import java.util.concurrent.Flow;

public class TempSubscriber implements Flow.Subscriber<TempInfo> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription; // 구독 저장
        subscription.request(1); // 첫번째 요청을 전달
    }

    @Override
    public void onNext(TempInfo item) {
        System.out.println(item);
        subscription.request(1); // 다음 정보를 요청
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}
