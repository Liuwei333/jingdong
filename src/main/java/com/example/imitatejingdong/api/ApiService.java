package com.example.imitatejingdong.api;

import com.example.imitatejingdong.bean.MessageBean;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface ApiService {
    //    https://www.zhaoapi.cn/file/upload
    @Multipart
    @POST("upload")
    Flowable<MessageBean> getMusicList(@Query("uid") String uid, @Part MultipartBody.Part file);


}
