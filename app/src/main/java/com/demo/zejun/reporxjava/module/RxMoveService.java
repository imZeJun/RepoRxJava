package com.demo.zejun.reporxjava.module;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author lizejun
 * @version 1.0 2016/11/8
 */
public interface RxMoveService {

    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

}
