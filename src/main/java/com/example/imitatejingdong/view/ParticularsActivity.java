package com.example.imitatejingdong.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imitatejingdong.R;
import com.example.imitatejingdong.shopBean.AddShopBean;
import com.example.imitatejingdong.utils.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticularsActivity extends AppCompatActivity {

    @BindView(R.id.img)
    SimpleDraweeView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.pid)
    TextView pid;
    @BindView(R.id.shop)
    Button shop;
    @BindView(R.id.marKet)
    TextView marKet;
    @BindView(R.id.web)
    TextView web;

    private int pid1;
    private String img1;
    private String name1;
    private String price1;
    private int marKet1;
    private String wang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);
        ButterKnife.bind(this);

        //接受值
        Intent intent = getIntent();
        img1 = intent.getStringExtra("photo");
        name1 = intent.getStringExtra("name");
        price1 = intent.getStringExtra("price");
        pid1 = intent.getExtras().getInt("pid");
        marKet1 = intent.getExtras().getInt("marKet");
        wang = intent.getStringExtra("wang");
        Toast.makeText(this, pid1 + "", Toast.LENGTH_SHORT).show();

        //赋值
        Glide.with(this).load(img1).into(img);
        name.setText(name1);
        price.setText(price1);
        pid.setText("" + pid1);
        marKet.setText("" + marKet1);
        web.setText(wang);


        //网址点击
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = new WebView(ParticularsActivity.this);
                webView.loadUrl(wang);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(ParticularsActivity.this).onActivityResult(requestCode, resultCode, data);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        SharedPreferences sharedPreferences = getSharedPreferences("person", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username != null) {
            //点击添加到购物车
            shopping();

        } else {
            shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //没有登录怎么处理
                    Intent intent1 = new Intent(ParticularsActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }
            });
        }
    }

    //添加
    private void shopping() {

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<AddShopBean> beanCall = HttpUtil.getInsance("http://120.27.23.105/product/").getApi().doGet("android", 10447, pid1);

                beanCall.enqueue(new Callback<AddShopBean>() {
                    @Override
                    public void onResponse(Call<AddShopBean> call, Response<AddShopBean> response) {
                        AddShopBean body = response.body();

                        if (body.getMsg().equals("加购成功")) {
                            Toast.makeText(ParticularsActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ParticularsActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<AddShopBean> call, Throwable t) {

                    }
                });
            }
        });
    }

    //分享
    public void click(View view) {
        UMWeb web = new UMWeb("http://www.baidu.com");//连接地址
        web.setTitle(name1);//标题
        web.setDescription(name1);//描述
        new ShareAction(ParticularsActivity.this).withMedia(web).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ParticularsActivity.this, "成功 了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ParticularsActivity.this, "失 败" + t.getMessage(), Toast.LENGTH_LONG).show();

        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ParticularsActivity.this, "取消                                     了", Toast.LENGTH_LONG).show();


        }
    };
}
