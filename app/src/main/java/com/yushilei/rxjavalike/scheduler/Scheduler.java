package com.yushilei.rxjavalike.scheduler;

/**
 * Created by Administrator on 2017/9/10.
 */

public interface Scheduler {
    void execute(Runnable run);
}
