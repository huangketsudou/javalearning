package DesignPattern.observer.weather;

import DesignPattern.observer.weather.Observer;

public interface Subject {
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers();
}