package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.MyCakeBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class MyAdapter extends RecyclerView.Adapter {
    Context context;
    List<MyCakeBean.DataBean> data;
    public MyAdapter(Context context, List<MyCakeBean.DataBean> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = View.inflate(context, R.layout.mylayout, null);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final MyHolder holder1 = (MyHolder) holder;
        holder1.name.setText(data.get(position).getTitle());

        String[] split = data.get(position).getImages().split("\\|");
        holder1.sim.setImageURI(split[0]);


        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //索引
                int layoutPosition = holder1.getLayoutPosition();

                //进行回调
                listener.itemClick(holder1.itemView,layoutPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();

    }
    /**
     * recyclerView的监听事件
     */
    OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    class MyHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final SimpleDraweeView sim;

        public MyHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            sim = view.findViewById(R.id.sim);
        }
    }
    //传进去点击事件和长按事件
    public interface OnItemClickListener{
        void  itemClick(View v,int position);
        void  itemLongClick(View v,int position);
    }


}
