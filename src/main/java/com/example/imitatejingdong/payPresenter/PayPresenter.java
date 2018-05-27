package com.example.imitatejingdong.payPresenter;

import com.example.imitatejingdong.dingdanbean.JsonPayBean;
import com.example.imitatejingdong.payModel.PayModel;
import com.example.imitatejingdong.payModel.PayModelView;
import com.example.imitatejingdong.payView.PayView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PayPresenter implements PayPresenterView {
    PayView logView;

    private PayModelView modelView;

    public PayPresenter(PayView logView){

        this.logView = logView;

        modelView = new PayModel();

    };
    @Override
    public void toPresenter(List<JsonPayBean.DataBean> data) {
        logView.toBackView(data);
    }

    @Override
    public void receive(String uid, String urlTitle) {
        modelView.toModel(uid,urlTitle,this);
    }

    @Override
    public void onDestroy() {
        logView = null;
        System.gc();
    }
}
