package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.ShouYeBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

public class MyTuiJian  extends RecyclerView.Adapter<MyTuiJian.Tuijianhodler> {
    private Context context;
    private List<ShouYeBean.TuijianBean.ListBean> list;

    public MyTuiJian(Context context, List<ShouYeBean.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Tuijianhodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tuijian, null);
        Tuijianhodler tuijianhodler=new Tuijianhodler(view);
        return tuijianhodler;
    }

    @Override
    public void onBindViewHolder(final Tuijianhodler holder, int position) {
        String images = list.get(position).getImages();
        String[] split = images.split(".jpg");
        holder.sim.setImageURI(split[0]+".jpg");
        holder.name.setText(list.get(position).getTitle());

        //设置
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //索引
               int layoutPosition = holder.getLayoutPosition();

               listener.itemClick(holder.itemView,layoutPosition);
           }
       });
    }
    /**
     * recyclerView的监听事件
     */
    OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class Tuijianhodler extends RecyclerView.ViewHolder{
        public SimpleDraweeView sim;
        public TextView name;
        public Tuijianhodler(View itemView) {
            super(itemView);
            this.sim=itemView.findViewById(R.id.sim);
            this.name=itemView.findViewById(R.id.name);
        }
        }

        //接口
    interface OnItemClickListener{
        void  itemClick(View v,int position);
        void  itemLongClick(View v,int position);
     }
    }
