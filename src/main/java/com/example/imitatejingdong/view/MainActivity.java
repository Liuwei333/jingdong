package com.example.imitatejingdong.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.imitatejingdong.R;

public class MainActivity extends AppCompatActivity {
    int miao=3;
    //定义跳转事件
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(miao>0){
                miao--;
                handler.sendEmptyMessageDelayed(0,1000);
            }else{
                Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                startActivity(intent);

                finish();
            }
        }
    };
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        去掉TitleBar（需要放到setContentView上面）
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        handler.sendEmptyMessageDelayed(0,1000);


        preferences = getSharedPreferences("count",MODE_WORLD_READABLE);
        //读取SharedPreferences里的count数据，若存在返回其值，否则返回0
        int count = preferences.getInt("count",0);
        Toast.makeText(MainActivity.this,"应用被打开了："+count+"次",Toast.LENGTH_LONG).show();
        editor = preferences.edit();
        //存入数据
        editor.putInt("count",++count);
        //提交修改
        editor.commit();

    }

    }


