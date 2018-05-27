package com.example.imitatejingdong.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.api.DengApi;
import com.example.imitatejingdong.dengzhunbean.DengluBean;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.deng)
    Button deng;
    @BindView(R.id.qq)
    Button qq;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.cha)
    ImageView cha;
    private String name1;
    private String pass1;
    private int uid;
    private String nickname;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //赋值
        fuzhi();
        //跳转
        tiao();

        //回到我的界面
        cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               item();
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
        deng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取
                retion();
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //授权

                UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);

                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
            }
        });

    }

    //跳转
    private void item() {
        finish();
    }
    //授权的事件

    UMAuthListener umAuthListener = new UMAuthListener() {

        @Override

        public void onStart(SHARE_MEDIA share_media) {

            Log.e("onStart", "onStart");
        }


//授权成功了。map里面就封装了一些qq信息

        @Override

        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            String uid = map.get("uid");

            String openid = map.get("openid");//微博没有

            String unionid = map.get("unionid");//微博没有

            String access_token = map.get("access_token");

            String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到

            String expires_in = map.get("expires_in");

            String name = map.get("name");

            String gender = map.get("gender");//性别

            String iconurl = map.get("iconurl");//头像

            //传过去用户名
            SharedPreferences sp = getSharedPreferences("person", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("islogin", true);
            edit.putString("username", name);
            edit.commit();
            finish();
        }

//授权失败

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            Log.e("onError", "onError");

        }


        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

            Log.e("onCancel", "onCancel");

        }
    };
    //授权回调

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void retion() {
        //获取输入的字
        name1 = name.getText().toString();
        pass1 = pass.getText().toString();

        /*//MD5加密
        String s = MD5Utils.md5Password(pass1);*/
        //解析
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.zhaoapi.cn/")
                .build();


        retrofit.create(DengApi.class).doGet(name1, pass1).enqueue(new Callback<DengluBean>() {
            @Override
            public void onResponse(Call<DengluBean> call, Response<DengluBean> response) {

                DengluBean body = response.body();
                String msg = body.getMsg();
                uid = body.getData().getUid();
                nickname = (String) body.getData().getNickname();
                mobile = body.getData().getMobile();

                if (msg.equals("登录成功")) {

                    //传过去用户名
                    SharedPreferences sp = getSharedPreferences("person", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("islogin", true);
                    edit.putString("username", nickname);
                    edit.putInt("uid", uid);
                    edit.commit();
                    finish();


                } else {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DengluBean> call, Throwable t) {

            }
        });
    }

    private void tiao() {
        //注册跳转
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fuzhi() {
        //注册
        SharedPreferences sharedPreferences = getSharedPreferences("perso", MODE_PRIVATE);

        boolean islogin = sharedPreferences.getBoolean("islogin", false);
        if (islogin) {

            final String username = sharedPreferences.getString("username", "");
            name.setText(username);
            final String user = sharedPreferences.getString("user", "");
            pass.setText(user);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fuzhi();
    }
}
