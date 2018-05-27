package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.ClassifyBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class ClassifyAdapter extends RecyclerView.Adapter {
    Context context;
    List<ClassifyBean.DataBean> data;
    public ClassifyAdapter(Context context, List<ClassifyBean.DataBean> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //导入
        View view = View.inflate(context, R.layout.classify_item, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final MyHolder holder1 = (MyHolder) holder;
        String icon = data.get(position).getIcon();
        holder1.sim.setImageURI(icon);
        holder1.text.setText(data.get(position).getName());

        //给recycleView的item设置点击事件
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        private final SimpleDraweeView sim;
        private final TextView text;

        public MyHolder(View view) {
            super(view);
            sim = view.findViewById(R.id.sim);
            text = view.findViewById(R.id.text);
        }
    }
//传进去点击事件和长按事件


    public interface OnItemClickListener{
        void  itemClick(View v,int position);
        void  itemLongClick(View v,int position);
    }
}
