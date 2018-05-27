package com.example.imitatejingdong.presenter;

import android.content.Context;

import com.example.imitatejingdong.bean.ShouYeBean;
import com.example.imitatejingdong.model.MyModel;
import com.example.imitatejingdong.view.LogView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public interface MyPresenterView {
    void getmv(Context context, LogView iview, MyModel imode);

    void getmv1( LogView iview, MyModel imode);


    void onDestroy();
}
