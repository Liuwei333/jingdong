package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.RightBean;
import com.example.imitatejingdong.view.FenLieActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class FrnlieRightAdapter extends RecyclerView.Adapter{
    Context context;
    List<RightBean.DataBean.ListBean> list;


    public FrnlieRightAdapter(Context context,List<RightBean.DataBean.ListBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyHolder7 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fenlieright, null);
        MyHolder7 holder = new MyHolder7(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyHolder7 holder7= (MyHolder7) holder;
        holder7.text.setText(list.get(position).getName());
        Uri uri = Uri.parse(list.get(position).getIcon());
        holder7.img.setImageURI(uri);



       holder7.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int pscid = list.get(position).getPscid();
               String name1 = list.get(position).getName();
               String name = list.get(position).getName();
               Intent intent = new Intent(context, FenLieActivity.class);
               intent.putExtra("pscid",pscid);
               intent.putExtra("inName",name1);
               context.startActivity(intent);
               Toast.makeText(context,name1+""+pscid,Toast.LENGTH_SHORT).show();
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

    class MyHolder7 extends RecyclerView.ViewHolder {
        public TextView text;
        public SimpleDraweeView img;
        public MyHolder7(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            text = itemView.findViewById(R.id.text);
        }
    }

//传进去点击事件和长按事件


    interface OnItemClickListener{
        void  itemClick(View v,int position);
        void  itemLongClick(View v,int position);
    }
}
