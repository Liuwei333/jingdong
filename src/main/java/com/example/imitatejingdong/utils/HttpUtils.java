package com.example.imitatejingdong.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gjl on 2018/4/18.
 */

public class HttpUtils {
    private static final String TAG = "HttpUtils-----";
    private static HttpUtils httpUtils;
    private final int SUCCESS = 0;
    private final int ERROR = 1;
    private MyHandler myHandler = new MyHandler();
    private OkLoadListener okLoadListener;

    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    //成功
                    String json = (String) msg.obj;
                    okLoadListener.okLoadSuccess(json);
                    break;

                case ERROR:
                    //失败
                    String error = (String) msg.obj;
                    okLoadListener.okLoadError(error);
                    break;
            }
        }
    }

    //get
    public void okGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();

        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = myHandler.obtainMessage();
                message.what = ERROR;
                message.obj = e.getMessage();
                myHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = myHandler.obtainMessage();
                message.what = SUCCESS;
                message.obj = response.body().string();
                myHandler.sendMessage(message);
            }
        });
    }

    public void setOkLoadListener(OkLoadListener okLoadListener) {
        this.okLoadListener = okLoadListener;
    }

    //post
    public void okPost(String url, Map<String, String> params) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();

        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keySet = params.keySet();
        for (String key :
                keySet) {
            String value = params.get(key);
            builder.add(key, value);
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = myHandler.obtainMessage();
                message.what = ERROR;
                message.obj = e.getMessage();
                myHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = myHandler.obtainMessage();
                message.what = SUCCESS;
                message.obj = response.body().string();
                myHandler.sendMessage(message);
            }
        });

    }

    //拦截器
    class MyIntercepter implements Interceptor {
        //intercept 拦截
        @Override
        public Response intercept(Chain chain) throws IOException {
            //添加公共参数
			//post 取出原来所有的参数，将之加到新的请求体里面。然后让请求去执行
            Request request = chain.request();
            //获取请求方法
            String method = request.method();
            if (method.equals("GET")) {//---------------------------GET 拦截
                //取出url地址
                String url = request.url().toString();
                //拼接公共参数
                boolean contains = url.contains("?");
                if (contains) {
                    url = url + "&source=android";
                } else {
                    url = url + "?source=android";
                }

                Request request1 = request.newBuilder().url(url).build();

                Response response = chain.proceed(request1);

                return response;


            } else if (method.equals("POST")) {//---------------------POST 拦截
                RequestBody body = request.body();//请求体
                if (body instanceof FormBody) {
                    //创建新的请求体
                    FormBody.Builder newBuilder = new FormBody.Builder();
                    for (int i = 0; i < ((FormBody) body).size(); i++) {
                        String key = ((FormBody) body).name(i);
                        String value = ((FormBody) body).value(i);
                        newBuilder.add(key, value);
                    }
                    //添加公共参数
                    newBuilder.add("source", "android");
                    FormBody newBody = newBuilder.build();
                    //创建新的请求体
                    Request request1 = request.newBuilder().post(newBody).build();
                    //去请求
                    Response response = chain.proceed(request1);
                    return response;
                }
            }
            return null;
        }
    }

    //上传文件（图片）
    public void upLoadImage(String url, String path) {//url 要上传的地址。path 要上传的文件路径
        //媒体类型
        MediaType mediaType = MediaType.parse("image/*");
        //multipartbody
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(path);
        MultipartBody multipartBody = builder.addFormDataPart("file", file.getName(), RequestBody.create(mediaType, file)).build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "上传失败0----: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "上传成功: ");
            }
        });

    }
}
