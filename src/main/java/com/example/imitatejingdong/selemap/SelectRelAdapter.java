package com.example.imitatejingdong.selemap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.ShouYeBean;
import com.example.imitatejingdong.view.ParticularsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class SelectRelAdapter extends RecyclerView.Adapter {
    Context context;
    List<ShouYeBean.TuijianBean.ListBean> data;


    public SelectRelAdapter(Context context,List<ShouYeBean.TuijianBean.ListBean> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.gridxml, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyHolder holder1 = (MyHolder) holder;

        holder1.text.setText(data.get(position).getTitle());

        //截取图片
        String[] split = data.get(position).getImages().split("\\|");
        Uri uri = Uri.parse(split[0]);

        //展示图片
        holder1.img.setImageURI(uri);
        //给recycleView的item设置点击事件
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //传值
                Intent intent = new Intent(context, ParticularsActivity.class);
                intent.putExtra("photo", data.get(position).getImages());
                intent.putExtra("name",data.get(position).getTitle());
                intent.putExtra("price",data.get(position).getPrice()+"");
                int pid = (int) data.get(position).getPid();
                intent.putExtra("marKet",data.get(position).getSalenum());
                intent.putExtra("wang",data.get(position).getDetailUrl());
                intent.putExtra("pid",pid);

                context.startActivity(intent);
            }
        });
    }
    /**
     * recyclerView的监听事件
     */
    MyLinAdapter.OnItemClickListener listener;
    public void setOnItemClickListener(MyLinAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private  TextView text;
        private  SimpleDraweeView img;
        public MyHolder(View view) {
            super(view);

            img = view.findViewById(R.id.img);
            text = view.findViewById(R.id.text);
        }
    }
    //传进去点击事件和长按事件


    interface OnItemClickListener{
        void  itemClick(View v,int position);
        void  itemLongClick(View v,int position);
    }
}
