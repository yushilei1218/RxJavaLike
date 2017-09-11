package com.yushilei.rxjavalike.rxlike;

/**
 * @author shilei.yu
 * @since 2017/9/11
 */

public abstract class Subscriber<T> implements Observer<T> {
    private final Subscriber<?> subscriber;

    public Subscriber() {
        subscriber = null;
    }

    public Subscriber(Subscriber<?> subscriber) {
        this.subscriber = subscriber;
    }

    public void onStart() {

    }
}
