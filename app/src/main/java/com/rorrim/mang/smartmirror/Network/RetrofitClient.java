package com.rorrim.mang.smartmirror.Network;

import com.rorrim.mang.smartmirror.Interface.Connectable;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements Connectable {
    private static RetrofitClient instance;
    private Retrofit retrofit;
    private OkHttpClient client;

    public RetrofitClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        client = okHttpBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
