package com.example.imitatejingdong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.ClassifyAdapter;
import com.example.imitatejingdong.adapter.MyExpandableAdapter;
import com.example.imitatejingdong.api.ClassifyApi;
import com.example.imitatejingdong.bean.ClassifyBean;
import com.example.imitatejingdong.bean.RightBean;
import com.example.imitatejingdong.utils.ClassifyUtils;
import com.example.imitatejingdong.view.ExpanableView;
import com.example.imitatejingdong.view.SeekActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/17.
 */

public class Fragment02 extends Fragment {

    @BindView(R.id.sousuo)
    TextView sousuo;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    @BindView(R.id.my_expand)
    ExpanableView myExpand;
    Unbinder unbinder;
    String uri="https://www.zhaoapi.cn/product/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //导入
        View inflate = View.inflate(getActivity(), R.layout.fragment02, null);

        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //跳转
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeekActivity.class);
                startActivity(intent);
            }
        });

        //默认
        getRequest(1);

        rcy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false
        ));
        //分割线
        rcy.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //请求数据
        request();

    }

    //右边
    private void getRequest(int cid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClassifyApi retrofitService = retrofit.create(ClassifyApi.class);

        retrofit2.Call<RightBean> call = retrofitService.doPost(cid);
        call.enqueue(new retrofit2.Callback<RightBean>() {
            @Override
            public void onResponse(retrofit2.Call<RightBean> call, retrofit2.Response<RightBean> response) {
                RightBean body = response.body();

                List<RightBean.DataBean> data1 = body.getData();

                MyExpandableAdapter adapter = new MyExpandableAdapter(getActivity(),data1);
                myExpand.setAdapter(adapter);
                //展开
                for (int i=0;i<data1.size();i++){
                    myExpand.expandGroup(i);
                }
            }

            @Override
            public void onFailure(Call<RightBean> call, Throwable t) {

            }

        });
    }

    //左边
    private void request() {
        ClassifyUtils.getInstance(uri).getApi().doGet().enqueue(new Callback<ClassifyBean>() {
            @Override
            public void onResponse(Call<ClassifyBean> call, Response<ClassifyBean> response) {
                final List<ClassifyBean.DataBean> data = response.body().getData();
                if(data!=null){

                    ClassifyAdapter classifyAdapter = new ClassifyAdapter(getActivity(),data);
                    rcy.setAdapter(classifyAdapter);

                    //点击事件
                    classifyAdapter.setOnItemClickListener(new ClassifyAdapter.OnItemClickListener() {
                        @Override
                        public void itemClick(View v, int position) {

                            int cid = data.get(position).getCid();
                            getRequest(cid);
                        }

                        @Override
                        public void itemLongClick(View v, int position) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ClassifyBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
