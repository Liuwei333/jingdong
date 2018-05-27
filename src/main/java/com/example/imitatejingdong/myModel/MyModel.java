package com.example.imitatejingdong.myModel;


import android.widget.Toast;

import com.example.imitatejingdong.myPre.MyPresenView;
import com.example.imitatejingdong.shopBean.QueryBean;
import com.example.imitatejingdong.utils.HttpUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/4/12.
 */

public class MyModel implements MyModelView {
    @Override
    public void toPresenterBack(String url, final MyPresenView presenterView) {

        HttpUtil.getInsance(url).getApi().doQuery(10447,"android").enqueue(new retrofit2.Callback<QueryBean>() {
            @Override
            public void onResponse(retrofit2.Call<QueryBean> call, retrofit2.Response<QueryBean> response) {
                QueryBean data = response.body();

                    presenterView.success(data);

            }

            @Override
            public void onFailure(retrofit2.Call<QueryBean> call, Throwable t) {

            }
        });
    }
}
