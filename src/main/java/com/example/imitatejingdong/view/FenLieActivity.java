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

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.FenLeiAdapter;
import com.example.imitatejingdong.api.FenApi;
import com.example.imitatejingdong.bean.FenBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FenLieActivity extends AppCompatActivity {

    @BindView(R.id.back)
    Button back;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.img)
    CheckBox img;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    private int pscid;
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_lie);
        ButterKnife.bind(this);

        //返回
        backActivity();

        //接受值
        Intent intent = getIntent();
        String name = intent.getStringExtra("inName");
        pscid = intent.getExtras().getInt("pscid");

        text.setText(name);
        img.setChecked(flag);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag) {
                        retion();
                        img.setChecked(false);
                        flag = img.isChecked();

                    } else {
                        retion1();
                        img.setChecked(true);
                        flag = img.isChecked();

                    }
                }
            });
            //可以重复点击
            if (img.isChecked()) {
                retion1();

            } else {
                retion();
            }

        }

    private void backActivity() {
        finish();
    }

    private void retion() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/product/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FenApi fenApi = retrofit.create(FenApi.class);
        fenApi.doGet(pscid,1,0).enqueue(new retrofit2.Callback<FenBean>() {
            @Override
            public void onResponse(retrofit2.Call<FenBean> call, retrofit2.Response<FenBean> response) {
                List<FenBean.DataBean> data = response.body().getData();

                rcy.setLayoutManager(new LinearLayoutManager(FenLieActivity.this,LinearLayoutManager.VERTICAL,false));
                FenLeiAdapter fenLeiAdapter = new FenLeiAdapter(FenLieActivity.this,data);
                rcy.setAdapter(fenLeiAdapter);
            }

            @Override
            public void onFailure(retrofit2.Call<FenBean> call, Throwable t) {

            }
        });
    }
    private void retion1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/product/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FenApi fenApi = retrofit.create(FenApi.class);
        fenApi.doGet(pscid,1,0).enqueue(new retrofit2.Callback<FenBean>() {
            @Override
            public void onResponse(retrofit2.Call<FenBean> call, retrofit2.Response<FenBean> response) {
                List<FenBean.DataBean> data = response.body().getData();

                rcy.setLayoutManager(new GridLayoutManager(FenLieActivity.this,2));
                FenLeiAdapter fenLeiAdapter = new FenLeiAdapter(FenLieActivity.this,data);
                rcy.setAdapter(fenLeiAdapter);
            }

            @Override
            public void onFailure(retrofit2.Call<FenBean> call, Throwable t) {

            }
        });
    }
}
