package com.example.imitatejingdong.fragmentApi;

import com.example.imitatejingdong.bean.MyCakeBean;
import com.example.imitatejingdong.bean.ShouYeBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/18.
 */


public interface MyInterfaceApi {
    @GET("getProducts")
    Call<MyCakeBean>doGet(@Query("source")String source,@Query("pscid")String pscid);

    @GET("ad/getAd")
    Call<ShouYeBean>doShou(@Query("source")String source,@Query("num")String num);
}
