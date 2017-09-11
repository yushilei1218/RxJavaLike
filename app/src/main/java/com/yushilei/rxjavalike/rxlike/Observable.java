package com.yushilei.rxjavalike.rxlike;

import com.yushilei.rxjavalike.observable.IteratorOnSubscribe;
import com.yushilei.rxjavalike.observable.OperatorObserveOn;
import com.yushilei.rxjavalike.observable.OperatorSubscribeOn;
import com.yushilei.rxjavalike.observable.SynchronousObservable;
import com.yushilei.rxjavalike.scheduler.Scheduler;

import java.util.Iterator;

/**
 * @author shilei.yu
 * @since 2017/9/11
 */

public class Observable<T> {

    private final OnSubscribe<T> onSubscribe;

    public Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> just(final T value) {
        return new SynchronousObservable<T>(value);
    }

    public static <T> Observable<T> from(Iterable<? extends T> iterator) {
        return new Observable<T>(new IteratorOnSubscribe<T>(iterator));
    }

    public Observable<T> subscribeOn(Scheduler scheduler) {
        return nest().lift(new OperatorSubscribeOn<T>(scheduler));
    }

    public Observable<T> observerOn(Scheduler scheduler) {
        return lift(new OperatorObserveOn<T>(scheduler));
    }

    public void subscribe(Subscriber<T> subscriber) {
        onSubscribe.call(subscriber);
    }

    public final Observable<Observable<T>> nest() {
        return just(this);
    }

    public <R> Observable<R> lift(final Operator<R, T> operator) {
        return new Observable<R>(new OnSubscribe<R>() {
            @Override
            public void call(Subscriber<? super R> subscriber) {
                try {
                    Subscriber<? super T> st = operator.call(subscriber);
                    try {
                        st.onStart();
                        onSubscribe.call(st);
                    } catch (Throwable e) {
                        st.onError(e);
                    }
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {

    }

    public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
        // cover for generics insanity
    }
}
