package com.yushilei.rxjavalike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yushilei.rxjavalike.rxlike.Observable;
import com.yushilei.rxjavalike.rxlike.Subscriber;
import com.yushilei.rxjavalike.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ids.add(i);
        }
        Observable.from(ids)
                .subscribeOn(Schedulers.mainThread())
                .observerOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext " + integer.toString() + getThreadName());
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted " + getThreadName());
                    }

                    @Override
                    public void onError(Throwable trx) {
                        Log.d(TAG, "onError " + getThreadName());
                    }
                });
    }

    private String getThreadName() {
        return "  " + Thread.currentThread().getName();
    }
}
