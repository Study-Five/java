package ch9.sunmin.observer;

public class Observers {
    static class NYTimes implements Observer {
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        }
    }

    static class Coin implements Observer {
        public void notify(String tweet) {
            if(tweet != null && tweet.contains("doge")) {
                System.out.println("Con is crazy! " + tweet);
            }
        }
    }
}
