package com.yushilei.rxjavalike.rxlike;

/**
 * @author shilei.yu
 * @since 2017/9/11
 */

public interface Action1<T> extends Action {
    void call(T t);
}
