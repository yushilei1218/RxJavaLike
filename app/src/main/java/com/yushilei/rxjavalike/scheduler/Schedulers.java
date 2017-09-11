package com.yushilei.rxjavalike.scheduler;

import android.os.Handler;
import android.os.Looper;

import com.yushilei.rxjavalike.thread.ThreadPools;


/**
 * Created by Administrator on 2017/9/10.
 */

public class Schedulers {
    private Schedulers() {
    }

    public static Scheduler io() {
        return new SchedulerIo();
    }

    public static Scheduler newThread() {
        return new SchedulerNewThread();
    }

    public static Scheduler mainThread() {
        return new SchedulerMainThread();
    }

    private static class SchedulerIo implements Scheduler {
        @Override
        public void execute(Runnable run) {
            ThreadPools.THREAD_POOL_EXECUTOR.execute(run);
        }
    }

    private static class SchedulerNewThread implements Scheduler {
        @Override
        public void execute(Runnable run) {
            ThreadPools.newThread(run).start();
        }
    }

    private static class SchedulerMainThread implements Scheduler {
        @Override
        public void execute(Runnable run) {
            new Handler(Looper.getMainLooper()).post(run);
        }
    }
}
