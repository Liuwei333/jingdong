package com.example.imitatejingdong.utils;

import com.example.imitatejingdong.api.ShouYeApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {
    private volatile static Util util = null;
    private final Retrofit retrofit;

    private Util(String uri) {
        retrofit = new Retrofit.Builder().baseUrl(uri)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Util getmInstance(String uri) {
        if (util == null) {
            synchronized (Util.class) {
                if (util == null) {
                    util = new Util(uri);
                }
            }
        }
        return util;
    }

    public ShouYeApi getnetjson() {

        return retrofit.create(ShouYeApi.class);
    }

}
