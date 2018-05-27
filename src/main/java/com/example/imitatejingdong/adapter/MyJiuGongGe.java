package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.JiuGongGe_bean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

public class MyJiuGongGe extends RecyclerView.Adapter<MyJiuGongGe.Myjiugonggehodler>{
    private Context context;
    private List<JiuGongGe_bean.DataBean> list;

    public MyJiuGongGe(Context context,List<JiuGongGe_bean.DataBean>  list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public Myjiugonggehodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.shouye_jiugongge, null);
        Myjiugonggehodler myjiugonggehodler=new Myjiugonggehodler(inflate);
        return myjiugonggehodler;
    }

    @Override
    public void onBindViewHolder(Myjiugonggehodler holder, int position) {
        holder.sd.setImageURI(Uri.parse(list.get(position).getIcon()));
        holder.jiugongge_tv.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myjiugonggehodler extends RecyclerView.ViewHolder{
        public SimpleDraweeView sd;
        public TextView jiugongge_tv;
        public Myjiugonggehodler(View itemView) {
            super(itemView);
            this.sd=itemView.findViewById(R.id.sd);
            this.jiugongge_tv=itemView.findViewById(R.id.jiugongge_tv);
        }
    }

}
