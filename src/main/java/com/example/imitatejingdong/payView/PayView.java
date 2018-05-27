package com.example.imitatejingdong.payView;

import com.example.imitatejingdong.dingdanbean.JsonPayBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface PayView {
    void toBackView(List<JsonPayBean.DataBean> data);
}
