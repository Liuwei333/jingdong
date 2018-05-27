package com.example.imitatejingdong.view;

import android.content.Context;

import com.example.imitatejingdong.bean.JiuGongGe_bean;
import com.example.imitatejingdong.bean.ShouYeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public interface LogView {
    void setadapter(Context context, ShouYeBean shouye_bean);

    void setadapter1( List<JiuGongGe_bean.DataBean> data);
}
