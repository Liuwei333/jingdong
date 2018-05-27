package com.example.imitatejingdong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.PayRecyclerViewAdapter;
import com.example.imitatejingdong.dingdanbean.JsonPayBean;
import com.example.imitatejingdong.payPresenter.PayPresenter;
import com.example.imitatejingdong.payPresenter.PayPresenterView;
import com.example.imitatejingdong.payView.PayView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PayFragment extends Fragment implements PayView {
    private PayPresenterView presenterView;

    PayRecyclerViewAdapter myAdapter;

    String uid = "71";

    private View view;

    private RecyclerView rcy;

    private Handler handler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            List<JsonPayBean.DataBean> data = (List<JsonPayBean.DataBean>) msg.obj;
            /*Toast.makeText(getActivity(),data.get(1).getTitle(),Toast.LENGTH_SHORT).show();*/

            rcy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            myAdapter = new PayRecyclerViewAdapter(getActivity(), data);

            rcy.setAdapter(myAdapter);

        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.activity_pay, null);

        return view;

    }

    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initView();

        //初始化数据

        Bundle bundle = getArguments();

        String urlTitle = bundle.getString("url").toString();

        presenterView = new PayPresenter(this);

        presenterView.receive(uid, urlTitle);

    }

    private void initView() {
        rcy = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void toBackView(List<JsonPayBean.DataBean> data) {
        Message msg = Message.obtain();

        msg.obj = data;

        handler.sendMessage(msg);
    }
    //内存泄漏

    @Override

    public void onDestroy() {

        super.onDestroy();

        if (presenterView != null) {

            presenterView.onDestroy();

            presenterView = null;

            System.gc();

        }
    }
}
