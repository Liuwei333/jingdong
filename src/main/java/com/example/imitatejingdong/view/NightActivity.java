package com.example.imitatejingdong.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.night.MyToggleButton;
import com.example.imitatejingdong.night.OnButton;
import com.example.imitatejingdong.utils.ThemeChangeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NightActivity extends AppCompatActivity implements OnButton {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ThemeChangeUtil.changeTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
        MyToggleButton myToggleButton = findViewById(R.id.myToggleButton);


        myToggleButton.setState(true);

        myToggleButton.seButtonBackGround(R.mipmap.switch_background);
        myToggleButton.setSlidingBack(R.mipmap.slide_button_background);


        myToggleButton.setButtonListener(this);


        //模式
        Button mChangeBtn = findViewById(R.id.btn_change);

        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeChangeUtil.isChange) {
                    ThemeChangeUtil.isChange = false;
                }else{
                    ThemeChangeUtil.isChange = true;
                }

                NightActivity.this.recreate();//重新创建当前Activity实例
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mIntent = new Intent(this, HomePageActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        finish();
    }


    public void setOnButtonChangeState(boolean state) {

        if (state){

            Toast.makeText(NightActivity.this,"夜间模式",Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(NightActivity.this,"白天模式",Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //3. 在onDestroy调用

    }


}
