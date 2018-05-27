package com.example.imitatejingdong.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.MyAdapter;
import com.example.imitatejingdong.adapter.SeekAdapter;
import com.example.imitatejingdong.bean.MessageEvent;
import com.example.imitatejingdong.bean.SeekBean;
import com.example.imitatejingdong.greenDao.DaoMaster;
import com.example.imitatejingdong.greenDao.DaoSession;
import com.example.imitatejingdong.greenDao.SeekBeanDao;
import com.fynn.fluidlayout.FluidLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeekActivity extends AppCompatActivity {

    String[] arrs = {"我们相爱吧", "手机", "iPad", "充电宝", "切水果", "荣耀手机", "背包新品", "防水墙纸", "会议椅"};
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.seek)
    TextView seek;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.edit)
    EditText edit;
    private FluidLayout fluidLayout;
    private String name;
    private SeekBeanDao seekBeanDao;
    private Button textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);
        ButterKnife.bind(this);

        fluidLayout = findViewById(R.id.fluid_layout);

        SQLiteDatabase writableDatabase = new DaoMaster.DevOpenHelper(SeekActivity.this, "stu.db").getWritableDatabase();
        DaoMaster master = new DaoMaster(writableDatabase);

        DaoSession daoSession = master.newSession();
        seekBeanDao = daoSession.getSeekBeanDao();
        //数据
        getTag();

        //展示
        query();

        //回调
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //跳转传值
        seek.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                name = edit.getText().toString();
                if (edit.length() == 0) {

                    Toast.makeText(SeekActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();

//弹出一个自动消失的提示框return;

                } else {

                    SeekBean seekBean = new SeekBean(null, name);
                    long insert = seekBeanDao.insert(seekBean);
                    if (insert == 0) {
                        Toast.makeText(SeekActivity.this, "没添加", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SeekActivity.this, "搜索成功", Toast.LENGTH_SHORT).show();
                    }
                    //展示
                    query();

                    //搜索跳转
                    Intent intent = new Intent(SeekActivity.this, TransformActivity.class);

                    intent.putExtra("inName",name);

                    startActivity(intent);

                }
            }
        });

        //清空历史记录
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBeanDao.deleteAll();

                query();
            }
        });

    }

    //查询
    public void query(){
        //查询全部
        final List<SeekBean> seekBeans = seekBeanDao.loadAll();
        rcy.setLayoutManager(new LinearLayoutManager(SeekActivity.this,LinearLayoutManager.VERTICAL,false));
//分割线
        rcy.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //适配器
        SeekAdapter adapter = new SeekAdapter( SeekActivity.this,seekBeans);

        adapter.setOnItemClickListener(new SeekAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                Toast.makeText(SeekActivity.this,seekBeans.get(position).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemLongClick(View v, int position) {

            }
        });

        rcy.setAdapter(adapter);
    }

    public void getTag() {
//循环数据
        for (int i = 0; i < arrs.length; i++) {

            textView = new Button(SeekActivity.this);

            textView.setText(arrs[i]);

            textView.setTextSize(13);

//上下左右的距离

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(10, 7, 25, 7);

            fluidLayout.addView(textView, params);
              //搜索的字

            final String s = textView.getText().toString();
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeekBean seekBean = new SeekBean(null, s);
                    long insert = seekBeanDao.insert(seekBean);
                    if (insert == 0) {
                        Toast.makeText(SeekActivity.this, "没添加", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SeekActivity.this, "搜索成功", Toast.LENGTH_SHORT).show();
                    }
                    //展示
                    query();
                }
            });
        }
    }


}
