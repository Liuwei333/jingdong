package com.example.imitatejingdong.payModel;

import com.example.imitatejingdong.payPresenter.PayPresenterView;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface PayModelView {
    void toModel(String uid, String urlTitle, PayPresenterView presenterView);
}
