package com.yushilei.rxjavalike.observable;

import com.yushilei.rxjavalike.rxlike.Observable;
import com.yushilei.rxjavalike.rxlike.Subscriber;

/**
 * @author shilei.yu
 * @since 2017/9/11
 */

public class SynchronousObservable<T> extends Observable<T> {
    private final T t;

    public SynchronousObservable(final T t) {
        super(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(t);
                subscriber.onCompleted();
            }
        });
        this.t = t;
    }

    public T get() {
        return t;
    }
}
