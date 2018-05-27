package com.example.imitatejingdong.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.fragment.PayFragment;

public class IndentActivity extends AppCompatActivity {
    private TabLayout myTab;

    private ViewPager viewPage;

    private String[] title = {"待支付", "已支付", "已取消"};

    private String[] urlTitle = {"0", "1", "2"};

    private IndentAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent);
        //初始化

        initView();

        myAdapter = new IndentAdapter(getSupportFragmentManager());

        viewPage.setAdapter(myAdapter);

        myTab.setupWithViewPager(viewPage); }

    private void initView() {

        myTab = findViewById(R.id.myTab);

        viewPage = findViewById(R.id.viewPage);

    }
    private class IndentAdapter extends FragmentPagerAdapter {

        public IndentAdapter(FragmentManager supportFragmentManager) {

            super(supportFragmentManager);

        }

        @Override

        public CharSequence getPageTitle(int position) {

            return title[position];

        }

        @Override

        public Fragment getItem(int position) {

            //创建对象并返回

            Bundle bundle = new Bundle();

            bundle.putString("url", urlTitle[position]);

            //实例化

            PayFragment payFragment = new PayFragment();

            payFragment.setArguments(bundle);

            return payFragment;

        }

        @Override

        public int getCount() {

            return title.length;

        }
    }
}
