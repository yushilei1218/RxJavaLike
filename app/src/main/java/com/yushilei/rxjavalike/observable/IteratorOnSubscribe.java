package com.yushilei.rxjavalike.observable;

import com.yushilei.rxjavalike.rxlike.Observable;
import com.yushilei.rxjavalike.rxlike.Subscriber;

import java.util.Iterator;

/**
 * @author shilei.yu
 * @since 2017/9/11
 */

public class IteratorOnSubscribe<T> implements Observable.OnSubscribe<T> {
    final Iterator<T> mIterator;

    public IteratorOnSubscribe(Iterator<T> iterator) {
        mIterator = iterator;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        try {
            while (mIterator.hasNext()) {
                T next = mIterator.next();
                subscriber.onNext(next);
            }
            subscriber.onCompleted();
        } catch (Throwable trx) {
            subscriber.onError(trx);
        }
    }
}
