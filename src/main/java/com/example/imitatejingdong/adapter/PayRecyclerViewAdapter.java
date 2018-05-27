package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.api.PayApi;
import com.example.imitatejingdong.dingdanbean.JsonPayBean;
import com.example.imitatejingdong.dingdanbean.QuxiaoBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/24.
 */

public class PayRecyclerViewAdapter  extends RecyclerView.Adapter {
    Context context;
    List<JsonPayBean.DataBean> list;
    public PayRecyclerViewAdapter(Context context, List<JsonPayBean.DataBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_model, null);

        MyHolder myHolder = new MyHolder(inflate);


        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myViewHolder = (MyHolder) holder;
        final JsonPayBean.DataBean dataBean = list.get(position);
        myViewHolder.tvTitle.setText(dataBean.getTitle());
        int status = dataBean.getStatus();
        myViewHolder.tvBt.setText("查看订单");


        myViewHolder.tvStatus.setTextColor(Color.parseColor("#000000"));
        if (status == 0) {
            myViewHolder.tvStatus.setText("待支付");
            myViewHolder.tvBt.setText("取消订单");
            myViewHolder.tvStatus.setTextColor(Color.parseColor("#ff0000"));

            myViewHolder.tvBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://www.zhaoapi.cn/product/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    //请求  取消订单
                    PayApi api = retrofit.create(PayApi.class);
                    api.doGet("71","2",dataBean.getOrderid()).enqueue(new Callback<QuxiaoBean>() {
                        @Override
                        public void onResponse(Call<QuxiaoBean> call, Response<QuxiaoBean> response) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    context);
                            builder.setTitle("提示");
                            builder.setMessage("确定取消吗？");
                            builder.setIcon(R.drawable.umeng_socialize_fav);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context,"确定取消了",Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("取消了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context,"不取消了",Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.show();


                        }

                        @Override
                        public void onFailure(Call<QuxiaoBean> call, Throwable t) {

                        }
                    });
                }
            });
        } else if (status == 2) {
            myViewHolder.tvStatus.setText("已取消");
        } else if (status == 1) {
            myViewHolder.tvStatus.setText("已支付");

        }
        myViewHolder.tvPrice.setText("价格：" + dataBean.getOrderid());
        myViewHolder.tvPrice.setTextColor(Color.parseColor("#ff0000"));
        myViewHolder.tvTime.setText(dataBean.getCreatetime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvStatus;
        private final TextView tvPrice;
        private final TextView tvTime;
        private final Button tvBt;


        public MyHolder(View view) {
            super(view);

            tvStatus = view.findViewById(R.id.dingdan);
            tvTitle = view.findViewById(R.id.name);
            tvPrice = view.findViewById(R.id.price);
            tvBt = view.findViewById(R.id.quxiao);
            tvTime = view.findViewById(R.id.time);
        }
    }
    }
