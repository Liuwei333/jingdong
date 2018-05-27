package com.example.imitatejingdong.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.fragment.Fragment01;
import com.example.imitatejingdong.fragment.Fragment02;
import com.example.imitatejingdong.fragment.Fragment03;
import com.example.imitatejingdong.fragment.Fragment04;
import com.example.imitatejingdong.fragment.Fragment05;
import com.example.imitatejingdong.utils.ThemeChangeUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewpager;
    private RadioGroup group;
    private List<Fragment> list;
    private FrameLayout frameLayout;
    private Button but01;
    private Button but02;
    private Button but03;
    private Button but04;
    private Button but05;
    private Fragment01 fragment_1;
    private Fragment02 fragment_2;
    private Fragment03 fragment_3;
    private Fragment04 fragment_4;
    private Fragment05 fragment_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(HomePageActivity.this);
        super.onCreate(savedInstanceState);
        //        去掉TitleBar（需要放到setContentView上面）
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_page);
        frameLayout = findViewById(R.id.framelayout);
        group = findViewById(R.id.group);

        but01 = findViewById(R.id.but01);
        but02 = findViewById(R.id.but02);
        but03 = findViewById(R.id.but03);
        but04 = findViewById(R.id.but04);
        but05 = findViewById(R.id.but05);

        //创建Fragment对象及集合

        fragment_1 = new Fragment01();

        fragment_2 = new Fragment02();

        fragment_3 = new Fragment03();

        fragment_4 = new Fragment04();

        fragment_5 = new Fragment05();

        list = new ArrayList<>();
        list.add(fragment_1);
        list.add(fragment_2);
        list.add(fragment_3);
        list.add(fragment_4);
        list.add(fragment_5);


        //时设置的按钮，设置第一个按钮为默认值

        group.check(R.id.but01);

        //设置RadioGroup开始//设置按钮点击监听

        but01.setOnClickListener(this);

        but02.setOnClickListener(this);

        but03.setOnClickListener(this);

        but04.setOnClickListener(this);

        but05.setOnClickListener(this);

        //初始时向容器中添加第一个Fragment对象

        addFragment(fragment_1);

    }

    @Override

    public void finish() {

        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();

        viewGroup.removeAllViews();

        super.finish();

    }

    //点击事件处理



    public void onClick(View v) {

        //我们根据参数的id区别不同按钮

        //不同按钮对应着不同的Fragment对象页面

        switch (v.getId()) {

            case R.id.but01:

                addFragment(fragment_1);

                break;

            case R.id.but02:

                addFragment(fragment_2);

                break;

            case R.id.but03:

                addFragment(fragment_3);

                break;

            case R.id.but04:

                addFragment(fragment_4);

                break;
            case R.id.but05:

                addFragment(fragment_5);

                break;
            default:

                break;

        }
    }
    //向Activity中添加Fragment的方法

    public void addFragment(Fragment fragment) {



        //获得Fragment管理器

        FragmentManager fragmentManager = getSupportFragmentManager();

        //使用管理器开启事务

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //使用事务替换Fragment容器中Fragment对象

        fragmentTransaction.replace(R.id.framelayout,fragment);

        //提交事务，否则事务不生效

        fragmentTransaction.commit();

    }

}
