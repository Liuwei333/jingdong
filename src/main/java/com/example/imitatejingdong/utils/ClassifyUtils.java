package com.example.imitatejingdong.utils;

import com.example.imitatejingdong.api.ClassifyApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/23.
 */

public class ClassifyUtils {

    private static  ClassifyUtils instance;
    private final Retrofit retrofit;

    private ClassifyUtils(String uri){

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(uri)
                .build();
    }

    public static ClassifyUtils getInstance(String uri){
        if(instance==null){
            synchronized (ClassifyUtils.class){
                if(null==instance){
                    instance = new ClassifyUtils(uri);
                }
            }
        }
        return  instance;
    }

    public ClassifyApi getApi(){
        return retrofit.create(ClassifyApi.class);
    }
}
