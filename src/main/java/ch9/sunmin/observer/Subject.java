package ch9.sunmin.observer;

import ch9.sunmin.observer.Observer;

public interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}
