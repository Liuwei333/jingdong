package com.example.imitatejingdong.api;

import com.example.imitatejingdong.bean.JiuGongGe_bean;
import com.example.imitatejingdong.bean.ShouYeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface ShouYeApi {
    @GET("ad/getAd")
    Observable<ShouYeBean> getUser();

    @GET("product/getCatagory")
    Observable<JiuGongGe_bean> getjiugongge();
}
