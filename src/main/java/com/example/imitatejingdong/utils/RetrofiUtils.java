package com.example.imitatejingdong.utils;

import android.os.Environment;

import com.example.imitatejingdong.fragmentApi.MyInterfaceApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/18.
 */

public class RetrofiUtils {

    private static RetrofiUtils instance;
    private final Retrofit retrofit;

    //初始化
    private RetrofiUtils(String uri){

        File file = new File(Environment.getExternalStorageDirectory(),"chca11");
        //拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //OK
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addNetworkInterceptor(httpLoggingInterceptor.setLevel(new HttpLoggingInterceptor().getLevel().BODY));

        okHttpClient.readTimeout(3000, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(3000,TimeUnit.SECONDS);
        okHttpClient.cache(new Cache(file,10*1024));

        retrofit = new Retrofit.Builder()
                .baseUrl(uri)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();

    }

    //单利
    public static RetrofiUtils getInstance(String uri){
        if(instance==null){
            synchronized (RetrofiUtils.class){
                if(null==instance){
                    instance = new RetrofiUtils(uri);
                }
            }
        }
        return instance;
    }

    //api创建
    public MyInterfaceApi getApi(){
        return retrofit.create(MyInterfaceApi.class);
    }


}
