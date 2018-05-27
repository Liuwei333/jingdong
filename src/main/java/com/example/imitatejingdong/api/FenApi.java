package com.example.imitatejingdong.api;

import com.example.imitatejingdong.bean.FenBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/26.
 */

public interface FenApi {
    @GET("getProducts")
    Call<FenBean>doGet(@Query("pscid")int pscid,@Query("page")int page,@Query("sort")int sort);
}
