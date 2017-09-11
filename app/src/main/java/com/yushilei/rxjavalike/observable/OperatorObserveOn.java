package com.yushilei.rxjavalike.observable;

import com.yushilei.rxjavalike.rxlike.Observable;
import com.yushilei.rxjavalike.rxlike.Subscriber;
import com.yushilei.rxjavalike.scheduler.Scheduler;

/**
 * 订阅结果处理线程切换
 *
 * @author shilei.yu
 * @since 2017/9/11
 */

public class OperatorObserveOn<T> implements Observable.Operator<T, T> {
    private final Scheduler mScheduler;

    public OperatorObserveOn(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        /*结果处理的线程通过代理进行变换*/
        return new ProxySubscriber<T>(subscriber);
    }

    private final class ProxySubscriber<T> extends Subscriber<T> {
        final Subscriber<? super T> subscriber;

        ProxySubscriber(Subscriber<? super T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void onNext(final T t) {
            mScheduler.execute(new Runnable() {
                @Override
                public void run() {
                    subscriber.onNext(t);
                }
            });
        }

        @Override
        public void onCompleted() {
            mScheduler.execute(new Runnable() {
                @Override
                public void run() {
                    subscriber.onCompleted();
                }
            });
        }

        @Override
        public void onError(final Throwable trx) {
            mScheduler.execute(new Runnable() {
                @Override
                public void run() {
                    subscriber.onError(trx);
                }
            });
        }
    }
}
