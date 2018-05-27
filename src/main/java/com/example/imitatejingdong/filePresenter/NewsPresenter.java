package com.example.imitatejingdong.filePresenter;

import android.util.Log;

import com.example.imitatejingdong.bean.MessageBean;
import com.example.imitatejingdong.fileModel.NewModel;
import com.example.imitatejingdong.fileView.NewsView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/5/24.
 */

public class NewsPresenter implements BasePresenter{
    private NewsView inv;
    private DisposableSubscriber<MessageBean> disposableSubscriber;

    public void attachView(NewsView inv){
        this.inv = inv;
    }

    public void detachView(){
        // 当Activity销毁的时候取消订阅时间，防止内存泄漏
        if (disposableSubscriber != null) {
            if (disposableSubscriber.isDisposed()) {
                disposableSubscriber.dispose();
            }
        }
        if (inv!=null){
            inv = null;
        }
    }
    @Override
    public void getData(String uid, MultipartBody.Part file) {
        NewModel newModel = new NewModel(this);
        newModel.getData(uid,file);
    }
    public void getNews(Flowable<MessageBean> flowable) {
        disposableSubscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MessageBean>() {
                    @Override
                    public void onNext(MessageBean messageBean) {
                        if(messageBean!=null){
                            inv.onSuccess(messageBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("zxz",t.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
