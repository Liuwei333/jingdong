package com.example.imitatejingdong.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.QueryAdapter;
import com.example.imitatejingdong.myPre.MyPresenView;
import com.example.imitatejingdong.myPre.MyPresenter;
import com.example.imitatejingdong.shopBean.CountPriceBean;
import com.example.imitatejingdong.shopBean.QueryBean;
import com.example.imitatejingdong.view.LogMyView;
import com.example.imitatejingdong.view.LoginActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Administrator on 2018/5/17.
 */

public class Fragment04 extends Fragment implements LogMyView {


    @BindView(R.id.deng)
    Button deng;
    Unbinder unbinder;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {

                //价格和数量的Bean类 并且赋值

                CountPriceBean countAndPrice = (CountPriceBean) msg.obj;

                text_heji.setText("￥" + countAndPrice.getPriceString());

                text_jiesuan.setText("结算(" + countAndPrice.getCount() + ")");

            }

        }

    };


    String uri = "http://120.27.23.105/product/";

    private ExpandableListView ex;

    private QueryAdapter adapter;

    private MyPresenView myPresenterView;

    private CheckBox check_quanxuan;

    private TextView text_heji;

    private TextView text_jiesuan;
    private ImageView empty;
    private List<QueryBean.DataBean> data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //导入
        View inflate = View.inflate(getActivity(), R.layout.fragment04, null);

        //生成
        ex = inflate.findViewById(R.id.expand_view);
        check_quanxuan = inflate.findViewById(R.id.check_quanxuan);

        text_heji = inflate.findViewById(R.id.zong);
        text_jiesuan = inflate.findViewById(R.id.button_jiesuan);
        empty = inflate.findViewById(R.id.empty);

        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myPresenterView = new MyPresenter(this);
        myPresenterView.setNet(uri);
        //全选
        check_quanxuan.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                adapter.isAllCheckChild(check_quanxuan.isChecked());
            }
        });
    }


    @Override
    public void toBackView(QueryBean data1) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("person", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            //数据
            if (data1 != null) {

                data = data1.getData();
                //集合
                jiHe(data);
                  deng.setVisibility(View.GONE);
                empty.setVisibility(View.INVISIBLE);

            } else {
/*//集合
                jiHe(data);*/
                empty.setVisibility(View.VISIBLE);
                deng.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "购物车为空", Toast.LENGTH_SHORT).show();
                ex.setVisibility(View.GONE);
                return;

            }
        } else {


            //没登录直接跳转
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            deng.setVisibility(View.VISIBLE);
            empty.setVisibility(View.INVISIBLE);
        }


            deng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //没登录直接跳转
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });

    }

    private void jiHe(List<QueryBean.DataBean> data) {


        //默认二级条目全选
        for (int i = 0; i < data.size(); i++) {

            if (isChildCheck(i, data)) {

                data.get(i).setGroup_check(true);

            }

        }
        //调用全选的方法

        //全选图片默认选中
        check_quanxuan.setChecked(isAllChildCheck(data));

        adapter = new QueryAdapter(getActivity(), data, myPresenterView, handler);
        ex.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        for (int i = 0; i < data.size(); i++) {
            ex.expandGroup(i);
        }

//这里是适配器里面的总价方法
        adapter.sendPriceAndCount();
    }

    //全选图片默认选中
    private boolean isAllChildCheck(List<QueryBean.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {

            if (!data.get(i).isGroup_check()) {

                return false;

            }

        }

        return true;
    }

    //默认二级条目全选
    private boolean isChildCheck(int i, List<QueryBean.DataBean> data) {

        QueryBean.DataBean dataBean = data.get(i);

        for (int j = 0; j < dataBean.getList().size(); j++) {

            if (dataBean.getList().get(j).getSelected() == 0) {

                return false;

            }
        }

        return true;
    }

    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (myPresenterView != null) {
            myPresenterView.onDestroy();
            myPresenterView = null;
            System.gc();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

