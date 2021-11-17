package ch17.sunmin;

import java.util.concurrent.Flow;

/**
 * Processor 의 목적은 Publisher를 구독한 다음, 수신한 데이터를 가공해 다시 제공하는 것이다.
 * 화씨로 제공된 데이터를 섭씨로 변환해 다시 방출하는 다음의 예제를 통해 Processor 를 구현한다.
 */
// * @param <T> the subscribed item type
// * @param <R> the published item type
public class TempProcessor implements Flow.Processor<TempInfo, TempInfo> {

    private Flow.Subscriber<? super TempInfo> subscriber;

    @Override
    public void subscribe(Flow.Subscriber<? super TempInfo> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscriber.onSubscribe(subscription);
    }

    // 아래 다른 모든 메서드는 단순히 수신한 모든 신호를 업스트림 Subscriber로 전달한다.
    @Override
    public void onNext(TempInfo item) {
        subscriber.onNext(new TempInfo(item.getTown(), (item.getTemp() - 32) * 5 / 9));
        // 섭씨로 변환한 다음 TempInfo를 다시 전송
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
