package com.example.imitatejingdong.api;

import com.example.imitatejingdong.bean.ClassifyBean;
import com.example.imitatejingdong.bean.RightBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/23.
 */

public interface ClassifyApi {
    @GET("getCatagory")
    Call<ClassifyBean>doGet();
    @GET("product/getProductCatagory")
    Call<RightBean> doPost(@Query("cid") int cid);
}
