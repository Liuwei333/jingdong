package com.example.imitatejingdong.payModel;

import android.util.Log;
import android.widget.Toast;

import com.example.imitatejingdong.dingdanbean.JsonPayBean;
import com.example.imitatejingdong.payPresenter.PayPresenterView;
import com.example.imitatejingdong.utils.PayHttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PayModel implements PayModelView {
    @Override
    public void toModel(String uid, String urlTitle, final PayPresenterView presenterView) {
        OkHttpClient instance = PayHttpUtils.getInstance();

        HashMap<String,String> hashMap = new HashMap<>();

        hashMap.put("uid", uid);

        if (urlTitle.trim().equals("9")) {

        } else {

            hashMap.put("status", urlTitle); }

        PayHttpUtils.doPost("http://120.27.23.105/product/getOrders", hashMap, new Callback() {

            @Override

            public void onFailure(Call call, IOException e) {

            }

            @Override

            public void onResponse(Call call, Response response) throws IOException {

                String str = response.body().string();

                if (str != null) {

                    Gson gson = new Gson();

                    JsonPayBean jsonPayBean = gson.fromJson(str, JsonPayBean.class);
                    Log.d("aaa", "onResponse: "+jsonPayBean.getCode());
                    if (jsonPayBean != null) {

                        List<JsonPayBean.DataBean> data = jsonPayBean.getData();

                        if (data != null) {
                            presenterView.toPresenter(data);

                        }
                    }
                }
            }
        });
    }
}
