package com.example.imitatejingdong.api;

import com.example.imitatejingdong.bean.NameBean;
import com.example.imitatejingdong.dengzhunbean.DengluBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface IconApi {
    @GET("getUserInfo")
    Call<DengluBean>doGet(@Query("uid")int uid);
    @GET("updateNickName")
    Call<NameBean>doName(@Query("uid")int uid, @Query("nickname")String nickname);
}
