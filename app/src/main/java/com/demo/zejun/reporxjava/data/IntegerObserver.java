package com.demo.zejun.reporxjava.data;

import android.util.Log;
import rx.Observer;

public class IntegerObserver implements Observer<Integer> {

    private static final String TAG = IntegerObserver.class.getSimpleName();

    @Override
    public void onNext(Integer t) {
        Log.d(TAG, "IntegerObserver:onNext=" + t);
    }

    @Override
    public void onCompleted() {
        Log.d(TAG, "IntegerObserver:onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "IntegerObserver:onError");
    }
}
