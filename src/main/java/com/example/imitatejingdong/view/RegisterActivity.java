package com.example.imitatejingdong.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.api.ZhuceApi;
import com.example.imitatejingdong.dengzhunbean.ZhuCeBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.zhuce)
    Button zhuce;
    @BindView(R.id.cha)
    ImageView cha;
    private String name1;
    private String pass1;
    private String mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);





        //注册
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //api
                apier();
            }
        });
        //回车登录
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))

                {

//do something;

                    return true;

                }

                return false;

            }

        });

//回车登录
        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))

                {

//do something;

                    return true;

                }

                return false;

            }

        });
        cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void apier() {
        //获取输入字
        name1 = name.getText().toString().trim();
        pass1 = pass.getText().toString().trim();

        //解析
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ZhuceApi zhuceApi = retrofit.create(ZhuceApi.class);
        zhuceApi.doGet(name1, pass1).enqueue(new Callback<ZhuCeBean>() {
            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                mess = response.body().getMsg();
                Toast.makeText(RegisterActivity.this, mess.toString(), Toast.LENGTH_SHORT).show();
                if (mess.equals("注册成功")) {


                    //传过去值

                    SharedPreferences sp = getSharedPreferences("perso", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("islogin", true);
                    edit.putString("username", name1);
                    edit.putString("user", pass1);
                    edit.commit();
                    finish();


                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {

            }
        });
    }

}
