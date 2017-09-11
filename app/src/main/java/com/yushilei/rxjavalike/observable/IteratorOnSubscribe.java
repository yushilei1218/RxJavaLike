package com.yushilei.rxjavalike.observable;

import android.util.Log;

import com.yushilei.rxjavalike.rxlike.Observable;
import com.yushilei.rxjavalike.rxlike.Subscriber;

import java.util.Iterator;

/**
 * @author shilei.yu
 * @since 2017/9/11
 */

public class IteratorOnSubscribe<T> implements Observable.OnSubscribe<T> {
    private final Iterable<? extends T> mIterator;

    public IteratorOnSubscribe(Iterable<? extends T> iterator) {
        mIterator = iterator;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        try {
            Iterator<? extends T> iterator = mIterator.iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                Log.d("IteratorOnSubscribe", "subscribe " + next.toString() + " " + Thread.currentThread().getName());
                subscriber.onNext(next);
            }
            subscriber.onCompleted();
        } catch (Throwable trx) {
            subscriber.onError(trx);
        }
    }
}
