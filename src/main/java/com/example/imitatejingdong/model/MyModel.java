package com.example.imitatejingdong.model;

import com.example.imitatejingdong.bean.JiuGongGe_bean;
import com.example.imitatejingdong.bean.ShouYeBean;
import com.example.imitatejingdong.utils.HttpConfig;
import com.example.imitatejingdong.utils.Util;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/18.
 */

public class MyModel implements MyModelView{


    @Override
    public void getnetjson(final GetGson getjson) {
        Observable<ShouYeBean> user = Util.getmInstance(HttpConfig.net).getnetjson().getUser();
        user.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShouYeBean>() {
                    @Override
                    public void accept(ShouYeBean shouyeBean) throws Exception {
                        getjson.getnetjson(shouyeBean);
                    }

                });
    }

    @Override
    public void getjiugonggejson(final GetJiuGongGe getjiugongge) {
        Observable<JiuGongGe_bean> getjiugongge1 = Util.getmInstance(HttpConfig.net).getnetjson().getjiugongge();
        getjiugongge1.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JiuGongGe_bean>() {
                    @Override
                    public void accept(JiuGongGe_bean jiugongge_bean) throws Exception {
                        List<JiuGongGe_bean.DataBean> data = jiugongge_bean.getData();
                        getjiugongge.getJiugongge(data);
                    }
                });
    }

}
