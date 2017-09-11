package com.yushilei.rxjavalike.rxlike;

/**
 * @author shilei.yu
 * @since 2017/9/11
 */

public interface Observer<T> {
    void onNext(T t);

    void onCompleted();

    void onError(Throwable trx);
}
