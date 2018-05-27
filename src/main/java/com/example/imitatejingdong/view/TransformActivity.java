package com.example.imitatejingdong.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.FenLeiAdapter;
import com.example.imitatejingdong.api.FenApi;
import com.example.imitatejingdong.api.ShouYeApi;
import com.example.imitatejingdong.bean.FenBean;
import com.example.imitatejingdong.bean.ShouYeBean;
import com.example.imitatejingdong.selemap.MyGridAdapter;
import com.example.imitatejingdong.selemap.MyLinAdapter;
import com.example.imitatejingdong.selemap.SelectAdapter;
import com.example.imitatejingdong.selemap.SelectRelAdapter;
import com.example.imitatejingdong.utils.Okhtttpclient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransformActivity extends AppCompatActivity {

    @BindView(R.id.back)
    Button back;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.img)
    CheckBox img;
    @BindView(R.id.rcy)
    RecyclerView rcy;

    private boolean flag = true;

    String uri="https://www.zhaoapi.cn/ad/getAd";
String json;
    private int pscid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        ButterKnife.bind(this);

        //返回
        backActivity();

        //接受值
        Intent intent = getIntent();
        String name = intent.getStringExtra("inName");
        pscid = intent.getExtras().getInt("pscid");

        text.setText(name);
        img.setChecked(flag);


        if (name.equals("电脑")) {
            Toast.makeText(TransformActivity.this, "电脑", Toast.LENGTH_SHORT).show();

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag) {
                        ok2();
                        img.setChecked(false);
                        flag = img.isChecked();
                    } else {
                        ok1();
                        img.setChecked(true);
                        flag = img.isChecked();
                    }
                }
            });

            if (img.isChecked()) {
                //表格
                ok1();

            } else {
                //线性
                ok2();
            }
        } else if (name.equals("手机")) {
            Toast.makeText(TransformActivity.this, "手机", Toast.LENGTH_SHORT).show();
//点击事件

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag) {
                        ok4();
                        img.setChecked(false);
                        flag = img.isChecked();

                    } else {
                        ok3();
                        img.setChecked(true);
                        flag = img.isChecked();

                    }
                }
            });
            //可以重复点击
            if (img.isChecked()) {
                ok3();

            } else {
                ok4();
            }

        }else {
            Toast.makeText(TransformActivity.this,"暂无产品，请您查询手机或者电脑或者子分类",Toast.LENGTH_SHORT).show();
        }
    }


    //返回
    private void backActivity() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    //手机//线性
    private void ok4() {
        Okhtttpclient.doGet(uri, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                json = response.body().string();

                //因为ok是在子线程所以发送到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        ShouYeBean shouYeBean = gson.fromJson(json, ShouYeBean.class);
                        List<ShouYeBean.MiaoshaBean.ListBeanX> list = shouYeBean.getMiaosha().getList();
                        rcy.setLayoutManager(new LinearLayoutManager(TransformActivity.this,LinearLayoutManager.VERTICAL,false));

                        MyLinAdapter myLinAdapter = new MyLinAdapter(TransformActivity.this,list);
                        rcy.setAdapter(myLinAdapter);
                    }
                });

            }
        });
    }

    //手机//表格
    private void ok3() {
        Okhtttpclient.doGet(uri, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                json = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        ShouYeBean bean = gson.fromJson(json, ShouYeBean.class);
                        List<ShouYeBean.MiaoshaBean.ListBeanX> list = bean.getMiaosha().getList();
                        rcy.setLayoutManager(new GridLayoutManager(TransformActivity.this,2));

                        MyGridAdapter myGridAdapter = new MyGridAdapter(TransformActivity.this,list);
                        rcy.setAdapter(myGridAdapter);
                    }
                });

            }
        });
    }

    //电脑表格
    public void ok1(){
        Okhtttpclient.doGet(uri, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                json = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        ShouYeBean bean = gson.fromJson(json, ShouYeBean.class);
                        List<ShouYeBean.TuijianBean.ListBean> list = bean.getTuijian().getList();
                        rcy.setLayoutManager(new GridLayoutManager(TransformActivity.this,2));
                        SelectRelAdapter selectRelAdapter = new SelectRelAdapter(TransformActivity.this,list);
                        rcy.setAdapter(selectRelAdapter);
                    }
                });

            }
        });

    }
    //电脑线性
    public void ok2(){
        Okhtttpclient.doGet(uri, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                json = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        ShouYeBean bean = gson.fromJson(json,ShouYeBean.class);
                        List<ShouYeBean.TuijianBean.ListBean> list = bean.getTuijian().getList();
                        rcy.setLayoutManager(new LinearLayoutManager(TransformActivity.this,LinearLayoutManager.VERTICAL,false));
                        SelectAdapter selectAdapter = new SelectAdapter(TransformActivity.this,list);
                        rcy.setAdapter(selectAdapter);
                    }
                });

            }
        });
    }
}
