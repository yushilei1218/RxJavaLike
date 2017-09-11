package com.yushilei.rxjavalike.observable;

import com.yushilei.rxjavalike.rxlike.Observable;
import com.yushilei.rxjavalike.rxlike.Subscriber;
import com.yushilei.rxjavalike.scheduler.Scheduler;

/**
 * 订阅时线程切换
 *
 * @author shilei.yu
 * @since 2017/9/11
 */

public class OperatorSubscribeOn<T> implements Observable.Operator<T, Observable<T>> {
    private final Scheduler mScheduler;

    public OperatorSubscribeOn(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    @Override
    public Subscriber<? super Observable<T>> call(final Subscriber<? super T> subscriber) {

        return new Subscriber<Observable<T>>(subscriber) {
            @Override
            public void onNext(final Observable<T> tObservable) {
                /*原被观察者被作为事件被发送 并在新的线程中被订阅*/
                mScheduler.execute(new Runnable() {
                    @Override
                    public void run() {
                        tObservable.subscribe(new Subscriber<T>(subscriber) {
                            @Override
                            public void onNext(T t) {
                                subscriber.onNext(t);
                            }

                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable trx) {
                                subscriber.onError(trx);
                            }
                        });
                    }
                });
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable trx) {
                subscriber.onError(trx);
            }
        };
    }
}
