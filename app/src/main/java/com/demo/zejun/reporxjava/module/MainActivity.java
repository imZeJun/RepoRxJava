package com.demo.zejun.reporxjava.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.zejun.reporxjava.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RX_JAVA";

    private Button mBasicButton;
    private Button mMapButton;
    private Button mFlatMapButton;
    private Button mOnlyRetrofitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBasicButton = (Button) findViewById(R.id.basic_btn);
        mBasicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicSample();
            }
        });
        mMapButton = (Button) findViewById(R.id.map_btn);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapSample();
            }
        });
        mFlatMapButton = (Button) findViewById(R.id.flat_map_btn);
        mFlatMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flatMapSample();
            }
        });
        mOnlyRetrofitButton = (Button) findViewById(R.id.only_retrofit_btn);
        mOnlyRetrofitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRxJavaRetrofit();
            }
        });
    }

    private void basicSample() {
        Subscriber<String> observer = new Subscriber<String>() {

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext, currentThread:" + Thread.currentThread().getId());
            }
        };
        rx.Observable observable = rx.Observable.create(new rx.Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello World!, currentThread:" + Thread.currentThread().getId());
                subscriber.onCompleted();
            }
        });
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private void mapSample() {
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                long nextId = Thread.currentThread().getId();
                Log.d(TAG, "onNext:" + s + ", threadId=" + nextId);
            }
        };
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                long callId = Thread.currentThread().getId();
                subscriber.onNext(5);
                subscriber.onCompleted();
            }
        });
        observable.map(new Func1<Integer, String>() {

            @Override
            public String call(Integer integer) {
                long mapId = Thread.currentThread().getId();
                return "My Number is:" + integer;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    private void flatMapSample() {
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext, s=" + s);
            }
        };
        Observable<List<String>> observable = Observable.create(new Observable.OnSubscribe<List<String>>() {

            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> list = new ArrayList<>();
                list.add("First");
                list.add("Second");
                list.add("Third");
                subscriber.onNext(list);
            }
        });
        observable.flatMap(new Func1<List<String>, Observable<String>>() {

            @Override
            public Observable<String> call(List<String> strings) {
                return Observable.from(strings);
            }

        }).subscribe(subscriber);
    }

    private void getOnlyRetrofit() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getTopMovie(0, 10);
        call.enqueue(new Callback<MovieEntity>() {

            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                String str = response.body().toString();
                Log.d(TAG, "onResponse:" + str);
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private void getRxJavaRetrofit() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RxMoveService rxMoveService = retrofit.create(RxMoveService.class);
        rxMoveService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        int count = movieEntity.getCount();
                        Log.d(TAG, "onNext, count=" + count);
                    }
                });
    }
}
