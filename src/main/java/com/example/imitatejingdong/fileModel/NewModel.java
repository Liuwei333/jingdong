package com.example.imitatejingdong.fileModel;

import com.example.imitatejingdong.bean.MessageBean;
import com.example.imitatejingdong.filePresenter.NewsPresenter;
import com.example.imitatejingdong.utils.RetrofitUtils;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/5/24.
 */

public class NewModel {
    private NewsPresenter presenter;

    public NewModel(NewsPresenter presenter) {
        this.presenter = presenter;
    }
    public void getData(String uid , MultipartBody.Part file){
        Flowable<MessageBean> flowable = RetrofitUtils.getInstance().getService().getMusicList(uid, file);
        presenter.getNews(flowable);
    }

}
