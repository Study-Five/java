package ch9.sunmin.observer;

public class ObserverClient {
    public static void main(String[] args) {
        Feed f = new Feed();
        // Observer 등록
        f.registerObserver(new Observers.NYTimes());
        f.registerObserver(new Observers.Coin());

        // 람다식 파라미터 전달
        // 여기서 알 수 있는 건, 하나의 메서드만 가지는 @inteface 는 모두 동작 파라미터화 해서 람다로 전달할 수 있다는 것이다.
        f.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("careful")){
                System.out.println("!Warning! " + tweet);
            }
        });

        // 트윗 내용 notify
        f.notifyObservers("doge coin is getting money Be careful");
    }
}
