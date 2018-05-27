package com.example.imitatejingdong.myPre;


import com.example.imitatejingdong.shopBean.QueryBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 */

public interface MyPresenView {
    void success(QueryBean data );

    void setNet(String detUrl);

    void onDestroy();
}
