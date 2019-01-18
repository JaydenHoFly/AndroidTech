package com.jayden.learnretrofit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.jianshu.com/")
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        // 将回包的body转换成返回结果。
                        return new Converter<ResponseBody, Object>() {
                            @Override
                            public Object convert(@NonNull ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }

                    @Override
                    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
                        // 将请求的body转成Http标识的body.
                        return null;
                    }

                    @Override
                    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<String, String>() {
                            @Override
                            public String convert(@NonNull String value) throws IOException {
                                return value;
                            }
                        };
                    }
                })
                .build();

        ILearnRetrofitService learnRetrofitService = retrofit.create(ILearnRetrofitService.class);

        // GET
        Call<String> call = learnRetrofitService.test1("45cb536be2f4");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d(TAG, "onResponse--response.body()=" + response.body());
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure--throwable=" + t);
            }
        });

        // POST
        Call<String> postCall = learnRetrofitService.testPost("body");
        postCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
