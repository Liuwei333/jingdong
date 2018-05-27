package com.example.imitatejingdong.filePresenter;

import com.example.imitatejingdong.bean.MessageBean;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface BasePresenter {
    void getData(String uid, MultipartBody.Part file);

    void getNews(Flowable<MessageBean> flowable);
}
