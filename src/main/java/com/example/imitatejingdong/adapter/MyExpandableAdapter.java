package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.bean.RightBean;
import com.example.imitatejingdong.view.FenLieActivity;
import com.example.imitatejingdong.view.ParticularsActivity;
import com.example.imitatejingdong.view.TransformActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class MyExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    List<RightBean.DataBean> data1;
    private List<RightBean.DataBean.ListBean> list;

    public MyExpandableAdapter(Context context, List<RightBean.DataBean> data1) {
        this.context=context;;
        this.data1=data1;
    }

    @Override
    public int getGroupCount() {
        return data1.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data1.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data1.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setTextSize(15);
        textView.setTextColor(Color.RED);
        textView.setText(data1.get(groupPosition).getName());
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item,null);
            holder = new ViewHolder();
            holder.rcy = convertView.findViewById(R.id.rcy);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        list = data1.get(groupPosition).getList();
        FrnlieRightAdapter adapter1 = new FrnlieRightAdapter(context, list);

        holder.rcy.setLayoutManager(new GridLayoutManager(context,3, OrientationHelper.VERTICAL,false));
        holder.rcy.setAdapter(adapter1);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ViewHolder{
        RecyclerView rcy;
    }
}
