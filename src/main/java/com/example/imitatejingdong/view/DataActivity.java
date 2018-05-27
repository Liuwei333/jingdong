package com.example.imitatejingdong.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imitatejingdong.R;
import com.example.imitatejingdong.api.IconApi;
import com.example.imitatejingdong.bean.NameBean;
import com.example.imitatejingdong.dengzhunbean.DengluBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataActivity extends AppCompatActivity {

    @BindView(R.id.but)
    Button but;
    @BindView(R.id.img)
    SimpleDraweeView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.but1)
    Button but1;
    @BindView(R.id.stop)
    Button stop;
    @BindView(R.id.mm)
    TextView mm;
    private SharedPreferences.Editor edit1;
    private int uid;
    private String nickname;
    private String nicknam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);


        //接受到值
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= getSharedPreferences("data", DataActivity.MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid", 10447);


        //传
        chuan();


        //退出
        tui();

        //返回界面
        backToInterface();


        //头像
        init();


    }

    private void chuan() {
        //传到最后
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DataActivity.this,ProofActivity.class);
                //实例化SharedPreferences对象（第一步）
                SharedPreferences mySharedPreferences= getSharedPreferences("proof", DataActivity.MODE_PRIVATE);
                //实例化SharedPreferences.Editor对象（第二步）
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                //用putString的方法保存数据
                editor.putString("name", nicknam);
                editor.putInt("uid", uid);
                //提交当前数据
                editor.commit();
                startActivity(intent);
            }
        });
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
                   String icon = body.getData().getIcon();
                    Glide.with(DataActivity.this).load(icon).into(img);

                    nicknam = (String) body.getData().getNickname();
                    name.setText(nicknam);

                }else{
                    Toast.makeText(DataActivity.this,"空",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DengluBean> call, Throwable t) {

            }
        });
    }

    private void backToInterface() {
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void tui() {
        //获取shared
        SharedPreferences sharedPreferences = getSharedPreferences("person", MODE_PRIVATE);


        final SharedPreferences.Editor editor = sharedPreferences.edit();
        //退出登录
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DataActivity.this);
                builder.setTitle("确定要退出吗");
                builder.setNegativeButton("取消", null);//取消按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences login = getSharedPreferences("person", MODE_PRIVATE);
                        edit1 = login.edit();
                        edit1.putBoolean("islogin", false);
                        edit1.putString("username", null);
                        edit1.putInt("iconUrl", R.drawable.user);
                        edit1.commit();
                        finish();
                    }
                });
                builder.create();
                builder.show();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        chuan();
    }

}
