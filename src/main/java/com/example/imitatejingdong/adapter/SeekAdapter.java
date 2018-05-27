package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.SeekBean;
import com.example.imitatejingdong.view.SeekActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */

public class SeekAdapter extends RecyclerView.Adapter {
    Context context;
    List<SeekBean> list;
    public SeekAdapter(Context context, List<SeekBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = View.inflate(context, R.layout.seek_item, null);
        MyHolder myHolder = new MyHolder(inflate);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final MyHolder holder1 = (MyHolder) holder;
        holder1.text.setText(list.get(position).getName());

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder1.getLayoutPosition();
                listener.itemClick(holder1.itemView,adapterPosition);
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

    class MyHolder extends RecyclerView.ViewHolder {

        private final TextView text;

        public MyHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
        }
    }

//传进去点击事件和长按事件


    public interface OnItemClickListener{
        void  itemClick(View v,int position);
        void  itemLongClick(View v,int position);
    }
}
