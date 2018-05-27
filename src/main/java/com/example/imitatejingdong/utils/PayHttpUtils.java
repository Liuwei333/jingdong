package com.example.imitatejingdong.utils;

import android.os.Environment;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PayHttpUtils {
    //单例模式，把构造方法进行私有化
    private PayHttpUtils(){};
    static OkHttpClient client;


    public static OkHttpClient getInstance(){


        if (client==null) {
            //更加安全
            synchronized (HttpUtils.class) {
                //缓存的地方     mnt/sdcard
                File file = new File(Environment.getExternalStorageDirectory(), "cache11");
                client = new OkHttpClient().newBuilder()
                        .readTimeout(3000, TimeUnit.SECONDS)   //设置读取超时时间
                        .connectTimeout(3000, TimeUnit.SECONDS) //设置连接的超时时间

                        .cache(new Cache(file, 10 * 1024))
                        .build();
            }
        }
        return client;
    }

    //post请求
    public static void doPost(String url, Map<String,String> parms, Callback callback){

        //得到客户端的对像
        OkHttpClient client = getInstance();

        //不是FormBody，而是一个Builder
        FormBody.Builder body = new FormBody.Builder();
        //key   value
        for (String key:parms.keySet()){
            //value的值
            body.add(key,parms.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(body.build())
                .build();

        client.newCall(request).enqueue(callback);

    }

}
