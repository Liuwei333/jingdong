package com.example.imitatejingdong.api;


import com.example.imitatejingdong.dengzhunbean.DengluBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/4/21.
 */

public interface DengApi {
    @GET("user/login")
    Call<DengluBean>doGet(@Query("mobile") String mobile, @Query("password") String password);
}
