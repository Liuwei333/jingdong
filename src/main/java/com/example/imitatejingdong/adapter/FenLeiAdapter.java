package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.FenBean;
import com.example.imitatejingdong.view.TransformActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/26.
 */

public class FenLeiAdapter extends RecyclerView.Adapter {
    Context context;
    List<FenBean.DataBean> data;
    public FenLeiAdapter(Context context, List<FenBean.DataBean> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = View.inflate(context, R.layout.fenleiadapter, null);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder holder1= (MyHolder) holder;

        String images = data.get(position).getImages();
        holder1.sim.setImageURI(images);
        holder1.name.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sim;
        private final TextView name;

        public MyHolder(View view) {
            super(view);
            sim = view.findViewById(R.id.sim);
            name = view.findViewById(R.id.name);
        }
    }
}
