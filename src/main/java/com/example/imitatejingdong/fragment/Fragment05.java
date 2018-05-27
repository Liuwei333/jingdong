package com.example.imitatejingdong.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.MyAdapter;
import com.example.imitatejingdong.api.IconApi;
import com.example.imitatejingdong.bean.MyCakeBean;
import com.example.imitatejingdong.bean.NameBean;
import com.example.imitatejingdong.dengzhunbean.DengluBean;
import com.example.imitatejingdong.utils.RetrofiUtils;
import com.example.imitatejingdong.utils.ThemeChangeUtil;
import com.example.imitatejingdong.view.DataActivity;
import com.example.imitatejingdong.view.IndentActivity;
import com.example.imitatejingdong.view.LoginActivity;
import com.example.imitatejingdong.view.NightActivity;
import com.example.imitatejingdong.view.ParticularsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/5/17.
 */

public class Fragment05 extends Fragment {

    private TextView name;
    private RecyclerView rcy;
    private SharedPreferences sharedPreferences;
    private SimpleDraweeView tou;

    //网址
    String uri="https://www.zhaoapi.cn/product/";
    private List<MyCakeBean.DataBean> data;
    private TextView mSettingBtn;
    private TextView ding;
    private String icon;
    private String username;
    private int uid;
    private String nickname;
    private String nicknam;
    private String mobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //导入
        View inflate = View.inflate(getActivity(), R.layout.fragment05, null);

        name = inflate.findViewById(R.id.name);
        rcy = inflate.findViewById(R.id.rcy);
        tou = inflate.findViewById(R.id.tou);
        mSettingBtn = inflate.findViewById(R.id.btn_setting);
        ding = inflate.findViewById(R.id.ding);

        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        EventBus.getDefault().register(this);


        //订单
        ding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IndentActivity.class);
                startActivity(intent);
            }
        });

        mSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), NightActivity.class));
            }
        });


        //判断
        firstRefresh();

        //解析数据
        analysis();

        rcy.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

    //解析数据
    private void analysis() {
        RetrofiUtils.getInstance(uri).getApi().doGet("android","1").enqueue(new Callback<MyCakeBean>() {
            @Override
            public void onResponse(Call<MyCakeBean> call, Response<MyCakeBean> response) {
                data = response.body().getData();

                MyAdapter myAdapter = new MyAdapter(getActivity(), data);
                rcy.setAdapter(myAdapter);

                //接口回调
                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void itemClick(View v, int position) {
                        //传值
                        Intent intent = new Intent(getActivity(), ParticularsActivity.class);
                        intent.putExtra("photo", data.get(position).getImages());
                        intent.putExtra("name",data.get(position).getTitle());
                        intent.putExtra("price",data.get(position).getPrice()+"");
                        int pid = (int) data.get(position).getPid();
                        intent.putExtra("marKet",data.get(position).getSalenum());
                        intent.putExtra("pid",pid);
                        intent.putExtra("wang",data.get(position).getDetailUrl());
                        startActivity(intent);
                    }

                    @Override
                    public void itemLongClick(View v, int position) {

                    }
                });

            }

            @Override
            public void onFailure(Call<MyCakeBean> call, Throwable t) {

            }
        });
    }

    //这里是调用Bean类 并且接受回调回来的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event2(final String json) {
     name.setText(json);
    }

    //解除注册
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
    //判断登录
    private void firstRefresh() {
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //没有登录怎么处理
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        //获取shared

        sharedPreferences = getActivity().getSharedPreferences("person", MODE_PRIVATE);
        int iconUrl = sharedPreferences.getInt("iconUrl", 0);


        boolean islogin = sharedPreferences.getBoolean("islogin", true);
        Log.d("TAG",islogin+"");
        if(islogin){
            //如果登录了，要怎么处理
            username = sharedPreferences.getString("username", "哈喽");
            uid = sharedPreferences.getInt("uid",10447);
            name.setText(username);

                init();


           //登录以后的点击事件
            tou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), DataActivity.class);
                    //实例化SharedPreferences对象（第一步）
                    SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("data", MODE_PRIVATE);
                    //实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //用putString的方法保存数据
                    editor.putString("nickname", nicknam);
                    editor.putInt("uid", uid);
                    //提交当前数据
                    editor.commit();
                    startActivity(intent);
                }
            });


        }else {
            //没登录跳转登录
            name.setText("登录/注册>");
            //设默认头像
            if(iconUrl!= 0){
                tou.setImageResource(iconUrl);
            }

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //没有登录怎么处理
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }
            });

        }


    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IconApi iconApi = retrofit.create(IconApi.class);
        iconApi.doGet(uid).enqueue(new Callback<DengluBean>() {
            @Override
            public void onResponse(Call<DengluBean> call, Response<DengluBean> response) {
                DengluBean body = response.body();
                if(body.getMsg().equals("获取用户信息成功")){
                    icon = body.getData().getIcon();

                    Glide.with(getActivity()).load(icon).into(tou);
                    nicknam = (String) body.getData().getNickname();
                    name.setText(nicknam);


                }else{
                    Toast.makeText(getActivity(),"空",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DengluBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        firstRefresh();
    }
}
