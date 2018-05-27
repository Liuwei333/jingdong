package com.example.imitatejingdong.payPresenter;

import com.example.imitatejingdong.dingdanbean.JsonPayBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface PayPresenterView {
    void toPresenter(List<JsonPayBean.DataBean> data);

    void receive(String uid, String urlTitle);

    void onDestroy();
}
