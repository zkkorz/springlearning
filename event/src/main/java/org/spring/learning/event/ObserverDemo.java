package org.spring.learning.event;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

public class ObserverDemo {

    public static void main(String[] args) {
        EventObservable observable = new EventObservable();
        //添加监听者(观察者)
        observable.addObserver(new EventObserver());

        observable.notifyObservers("Test Observer");
    }

    static class EventObservable extends Observable {

        @Override
        protected synchronized void setChanged() {
            super.setChanged();
        }

        @Override
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(new EventObject(arg));
            clearChanged();
        }
    }

    static class EventObserver implements Observer, EventListener {

        @Override
        public void update(Observable o, Object event) {
            EventObject eventObject = (EventObject) event;
            System.out.println("收到事件：" + eventObject.toString());
        }
    }

}
