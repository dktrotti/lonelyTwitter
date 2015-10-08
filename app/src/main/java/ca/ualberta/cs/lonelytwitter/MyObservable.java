package ca.ualberta.cs.lonelytwitter;

/**
 * Created by Dominic on 15-10-07.
 */
public interface MyObservable {
    void notifyObservers();
    void addObserver(MyObserver observer);
}
