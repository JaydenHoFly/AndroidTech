package com.jayden.learnretrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 学习Retrofit的api.
 * Created by hedazhao on 2019/1/17.
 */
public interface ILearnRetrofitService {

    @GET("p/{id}")
    Call<String> test1(@Path("id") String id);

    @POST("add")
    Call<String> testPost(@Body String body);
}
