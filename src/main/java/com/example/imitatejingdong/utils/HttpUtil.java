package com.example.imitatejingdong.utils;

import android.os.Environment;


import com.example.imitatejingdong.api.GouApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HttpUtil {
    private static  HttpUtil insance;
    private final Retrofit retrofit;

    private HttpUtil(String uri){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor httpLoggingInterceptor1 = httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File file = new File(Environment.getExternalStorageDirectory(),"cache999");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor1)
                .readTimeout(3000, TimeUnit.SECONDS)
                .connectTimeout(3000,TimeUnit.SECONDS)
                .cache(new Cache(file,10*1024))
                        .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(uri)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static HttpUtil getInsance(String uri){
        if(insance==null){
            synchronized (HttpUtils.class){
                if(null==insance){
                    insance = new HttpUtil(uri);
                }
            }
        }
        return insance;
    }


    public GouApi getApi(){
        return  retrofit.create(GouApi.class);
    }
}
