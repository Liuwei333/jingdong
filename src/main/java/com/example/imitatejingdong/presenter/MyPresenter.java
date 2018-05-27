package com.example.imitatejingdong.presenter;

import android.content.Context;

import com.example.imitatejingdong.bean.JiuGongGe_bean;
import com.example.imitatejingdong.bean.ShouYeBean;
import com.example.imitatejingdong.model.GetGson;
import com.example.imitatejingdong.model.GetJiuGongGe;
import com.example.imitatejingdong.model.MyModel;
import com.example.imitatejingdong.model.MyModelView;
import com.example.imitatejingdong.view.LogView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class MyPresenter implements MyPresenterView {

    LogView logView;
    private final MyModelView myModelView;

    public MyPresenter(LogView logView){
        this.logView=logView;
        myModelView = new MyModel();
    }


    @Override
    public void getmv(final Context context, final LogView iview, MyModel imode) {
        imode.getnetjson(new GetGson() {
            @Override
            public void getnetjson(ShouYeBean shouye_bean) {
                logView.setadapter(context,shouye_bean);
            }
        });

    }

    @Override
    public void getmv1(LogView iview, MyModel imode) {
        imode.getjiugonggejson(new GetJiuGongGe() {
            @Override
            public void getJiugongge(List<JiuGongGe_bean.DataBean> data) {
                logView.setadapter1(data);
            }
        });

    }

    //解绑
    @Override
    public void onDestroy() {
        logView=null;
        System.gc();
    }
}
