package com.example.imitatejingdong.api;

import com.example.imitatejingdong.dingdanbean.QuxiaoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface PayApi {
    //uid=71&status=2&orderId=1449
    @GET("updateOrder")
    Call<QuxiaoBean> doGet(@Query("uid") String uid, @Query("status") String status, @Query("orderId") int orderId);
}
