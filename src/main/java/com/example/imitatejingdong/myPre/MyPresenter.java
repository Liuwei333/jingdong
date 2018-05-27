package com.example.imitatejingdong.myPre;


import com.example.imitatejingdong.myModel.MyModel;
import com.example.imitatejingdong.myModel.MyModelView;
import com.example.imitatejingdong.shopBean.QueryBean;
import com.example.imitatejingdong.view.LogMyView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 */

public class MyPresenter implements MyPresenView {
    LogMyView logView;
    private final MyModelView myModelView;

    public MyPresenter(LogMyView logView){
        this.logView=logView;
        myModelView = new MyModel();
    }
    @Override
    public void success(QueryBean data ) {

        logView.toBackView(data);
    }

    @Override
    public void setNet(String detUrl) {
        myModelView.toPresenterBack(detUrl,this);
    }

    //解除
    @Override
    public void onDestroy() {

        logView=null;
        System.gc();
    }


}
