package ch17.sunmin;

import java.util.concurrent.Flow;

public class Main {
    public static void main(String[] args) {
        // 뉴욕 Publisher 를 만들고 TempSubscriber 를 구독시킴
//        getTemperatures("New York").subscribe(new TempSubscriber());
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
    }
    private static Flow.Publisher<TempInfo> getTemperatures(String town) {
        // 함수형 인터페이스니까
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    public static Flow.Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }
}
