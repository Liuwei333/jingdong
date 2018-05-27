package com.example.imitatejingdong.utils;

import android.os.Environment;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Timeout;

/**
 * Created by Administrator on 2018/4/12.
 */

public class Okhtttpclient {

    private static OkHttpClient client;

    //单例模式
    private Okhtttpclient(){};

    public static OkHttpClient getInstance(){

        if(client==null){
            //更加安全
            synchronized (Okhtttpclient.class){
                File file = new File(Environment.getExternalStorageDirectory(),"cache1");

                client = new OkHttpClient.Builder()
                        .readTimeout(3000, TimeUnit.SECONDS)//读取事件
                        .connectTimeout(3000,TimeUnit.SECONDS)//连接时间
                        .cache(new Cache(file,10*1024))
                        .build();
            }
        }
        return client;
    };


    //get请求
    public static void doGet(String url, Callback callback){

        //获取对象
        OkHttpClient okHttpClient = new OkHttpClient();
         //请求操作
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    //post请求
    public static  void doPost(String url, Map<String,String>parms,Callback callback){

        //获取对象
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();

        for (String key:parms.keySet()){
            builder.add(key,parms.get(key));
        }

        //请求操作
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    //url  , 图片  ，参数    Callback
    public static  void upImage(String url,File file,String filenName,Map<String,String> params,Callback callback){

        OkHttpClient client = getInstance();


        //requestBody的实现类  Formbody
        MultipartBody.Builder builder = new MultipartBody.Builder();

        if (params!=null){
            for (String key :params.keySet()){
                builder.addFormDataPart(key,params.get(key));
            }
        }

        //设置类型
        builder.setType(MultipartBody.FORM);


        builder.addFormDataPart("file",filenName, RequestBody.create(MediaType.parse("application/octet-stream"),file));

        // builder.setType(MultipartBody.FORM);
        // builder.addFormDataPart("file",filenName,RequestBody.create(MediaType.parse("application/octet-stream"),file));
        //builder.addFormDataPart("file","aa.png",builder.build());

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        client.newCall(request).enqueue(callback);


    }

}
