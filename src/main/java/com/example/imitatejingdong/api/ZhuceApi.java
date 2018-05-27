package com.example.imitatejingdong.api;

import com.example.imitatejingdong.dengzhunbean.ZhuCeBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/4/21.
 */

public interface ZhuceApi {

    @GET("user/reg")
    Call<ZhuCeBean>doGet(@Query("mobile") String mobile, @Query("password") String password);
}
