package com.example.imitatejingdong.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitatejingdong.R;
import com.example.imitatejingdong.api.GouApi;
import com.example.imitatejingdong.myPre.MyPresenView;
import com.example.imitatejingdong.myPre.MyPresenter;
import com.example.imitatejingdong.shopBean.CountPriceBean;
import com.example.imitatejingdong.shopBean.DeleteBean;
import com.example.imitatejingdong.shopBean.QueryBean;
import com.example.imitatejingdong.shopBean.UpBean;
import com.example.imitatejingdong.utils.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/19.
 */

public class QueryAdapter extends BaseExpandableListAdapter {
    Context context;
    int childIndex;
    List<QueryBean.DataBean> data;
    MyPresenView presenter;
    int allIndex;
    Handler handler;

    public QueryAdapter(Context context, List<QueryBean.DataBean> data,  MyPresenView presenter, Handler handler) {
        this.context=context;
        this.data=data;
        this.presenter=presenter;
        this.handler = handler;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getList().get(childPosition);
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
        final MyHolder1 holder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.bujuimg,null);

            holder= new MyHolder1();
            holder.boxone = convertView.findViewById(R.id.groupCheck);
            holder.name = convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        }else{
            holder = (MyHolder1) convertView.getTag();
        }

        final QueryBean.DataBean dataBean = data.get(groupPosition);
        holder.name.setText(dataBean.getSellerName());
        holder.boxone.setChecked(dataBean.isGroup_check());

        //点击多选框  默认选中的话取消不了
        holder.boxone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义一个变量

                childIndex = 0;

                //传入点击事件和二级条目的bean集合

                childCheck(holder.boxone.isChecked(),dataBean);

            }
        });
        return convertView;
    }

    //默认选中的话取消不了
    private void childCheck(final boolean checked, final QueryBean.DataBean dataBean) {
        QueryBean.DataBean.ListBean listBean = dataBean.getList().get(childIndex);
        final Map<String,String> params = new HashMap<>();
        params.put("sellerid", String.valueOf(listBean.getSellerid()));
        params.put("pid", String.valueOf(listBean.getPid()));
        params.put("selected", String.valueOf(checked ? 1:0));
        params.put("num", String.valueOf(listBean.getNum()));
        GouApi api = HttpUtil.getInsance("http://120.27.23.105/product/").getApi();

        api.update(10447,params).enqueue(new Callback<UpBean>() {
            @Override
            public void onResponse(Call<UpBean> call, Response<UpBean> response) {
                //点击更新条目进行京东数据更新

                childIndex++;

                if (childIndex< dataBean.getList().size()){

                    childCheck(checked,dataBean);

                }else{

                    //传入到p城的网址

                    presenter.setNet("http://120.27.23.105/product/");

                }
            }

            @Override
            public void onFailure(Call<UpBean> call, Throwable t) {

            }
        });
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final MyHolder holder;

        if(convertView==null){

            convertView = View.inflate(context, R.layout.shoppingcart,null);

            holder = new MyHolder();

            holder.cb = convertView.findViewById(R.id.cb);

            holder.jia =convertView.findViewById(R.id.jia);

            holder.jian = convertView.findViewById(R.id.jian);

            holder.name = convertView.findViewById(R.id.name);

            holder.price = convertView.findViewById(R.id.price);

            holder.zong = convertView.findViewById(R.id.zong);

            holder.sim = convertView.findViewById(R.id.sim);

            holder.text_delete = convertView.findViewById(R.id.shan);

            convertView.setTag(holder);

        }else{

            holder = (MyHolder) convertView.getTag();

        }
//赋值
        final QueryBean.DataBean.ListBean listBean = data.get(groupPosition).getList().get(childPosition);

        holder.cb.setChecked(listBean.getSelected()==0 ? false:true);

        String[] split = listBean.getImages().split("\\|");

        Uri uri = Uri.parse(split[0]);

        holder.sim.setImageURI(uri);

        holder.name.setText(listBean.getTitle());

        holder.price.setText("￥"+listBean.getBargainPrice());

        holder.zong.setText(listBean.getNum()+"");


        //点击子条目的二级列表进行子条目所有全选
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params=new HashMap<>();

                params.put("sellerid", String.valueOf(listBean.getSellerid()));

                params.put("pid", String.valueOf(listBean.getPid()));

                params.put("selected", String.valueOf(listBean.getSelected()==0 ? 1 : 0));

                params.put("num", String.valueOf(listBean.getNum()));

                GouApi api = HttpUtil.getInsance("http://120.27.23.105/product/").getApi();

                api.update(10447,params).enqueue(new Callback<UpBean>() {

                    @Override

                    public void onResponse(Call<UpBean> call, Response<UpBean> response) {

                        if (response.isSuccessful()){

                            //调用网址
                            presenter.setNet("http://120.27.23.105/product/");
                        }
                    }
                    @Override

                    public void onFailure(Call<UpBean> call, Throwable t) { } }); } });


        //加

        holder.jia.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Map<String, String> params=new HashMap<>();

                params.put("sellerid", String.valueOf(listBean.getSellerid()));

                params.put("pid", String.valueOf(listBean.getPid()));

                params.put("selected", String.valueOf(listBean.getSelected()));

                params.put("num", String.valueOf(listBean.getNum()+1));

                GouApi api = HttpUtil.getInsance("http://120.27.23.105/product/").getApi();

                api.update(10447,params).enqueue(new Callback<UpBean>() {

                    @Override

                    public void onResponse(Call<UpBean> call, Response<UpBean> response) {

                        if (response.isSuccessful()){

                            presenter.setNet("http://120.27.23.105/product/");

                        }

                    }



                    @Override

                    public void onFailure(Call<UpBean> call, Throwable t) { } }); } });




//减

        holder.jian.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                int num= listBean.getNum();

                if (num==1){

                    return;

                }




                Map<String, String> params=new HashMap<>();

                params.put("sellerid", String.valueOf(listBean.getSellerid()));

                params.put("pid", String.valueOf(listBean.getPid()));

                params.put("selected", String.valueOf(listBean.getSelected()));

                params.put("num", String.valueOf(num-1));

                GouApi api = HttpUtil.getInsance("http://120.27.23.105/product/").getApi();

                api.update(10447,params).enqueue(new Callback<UpBean>() {

                    @Override

                    public void onResponse(Call<UpBean> call, Response<UpBean> response) {

                        if (response.isSuccessful()){

                            presenter.setNet("http://120.27.23.105/product/");

                        }

                    }

                    @Override

                    public void onFailure(Call<UpBean> call, Throwable t) { } }); } });

//删除

        holder.text_delete.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                if(holder.cb.isChecked()){
                    String pid = String.valueOf(listBean.getPid());

                    GouApi api = HttpUtil.getInsance("http://120.27.23.105/product/").getApi();

                    api.del("10447",pid).enqueue(new Callback<DeleteBean>() {

                        @Override

                        public void onResponse(Call<DeleteBean> call, Response<DeleteBean> response) {

                            if(response.isSuccessful()){
                                presenter.setNet("http://120.27.23.105/product/");
                            }

                        }
                        @Override
                        public void onFailure(Call<DeleteBean> call, Throwable t) {

                        }
                    });
                }else{
                    Toast.makeText(context,"未选中",Toast.LENGTH_SHORT).show();

                }
            } });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //总价 返回到主界面

    public void sendPriceAndCount() {

//价格和数量

        double price=0;
        int count=0;
        for (int i=0;i<data.size();i++){

            List<QueryBean.DataBean.ListBean> listBeans = data.get(i).getList();

            for (int j=0;j<listBeans.size();j++){
                if (listBeans.get(j).getSelected()==1) {
                    //价格和数量相乘
                    price +=listBeans.get(j).getBargainPrice() * listBeans.get(j).getNum();
                    //数量
                    count +=listBeans.get(j).getNum();
                }
            }
        }
        //创建bean类 把总价和数量传入

        //到时候我们会返回到主界面进行handler赋值

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        String priceString = decimalFormat.format(price);

        CountPriceBean countPriceBean = new CountPriceBean(priceString, count);

        Message msg= Message.obtain();

        msg.what=0;
        msg.obj=countPriceBean;

        handler.sendMessage(msg);

    }


    //主界面全选  添加到集合里面 返回到主界面
    public void isAllCheckChild(boolean checked) {
        List<QueryBean.DataBean.ListBean> allList = new ArrayList<>();
        for (int i=0;i<data.size();i++){
            QueryBean.DataBean dataBean = data.get(i);
            for (int j=0;j<dataBean.getList().size();j++){
                QueryBean.DataBean.ListBean Bean = dataBean.getList().get(j);
                allList.add(Bean);
            }
        }

        allIndex = 0;

        updateAllChildCheck(checked,allList);

    }

    //更新

    private void updateAllChildCheck(final boolean checked, final List<QueryBean.DataBean.ListBean> allList) {

        final QueryBean.DataBean.ListBean Bean = allList.get(allIndex);

        Map<String,String> params = new HashMap<>();

        //查询

        params.put("sellerid", String.valueOf(Bean.getSellerid()));

        params.put("pid", String.valueOf(Bean.getPid()));

        params.put("selected", String.valueOf(checked ? 1 : 0));

        params.put("num", String.valueOf(Bean.getNum()));

        GouApi api = HttpUtil.getInsance("http://120.27.23.105/product/").getApi();

        api.update(10447,params).enqueue(new Callback<UpBean>() {


            @Override

            public void onResponse(Call<UpBean> call, Response<UpBean> response) {

                allIndex++;

                if (allIndex< allList.size()){
                    updateAllChildCheck(checked,allList);

                }else{
                    presenter.setNet("http://120.27.23.105/product/");
                }

            }
            @Override
            public void onFailure(Call<UpBean> call, Throwable t) { } });
    }



    class MyHolder1{

        CheckBox boxone;
        TextView name;
    }
    class MyHolder{

        CheckBox cb;

        TextView name;

        TextView price;

        TextView zong;

        TextView jia,jian;

        TextView text_delete;

        SimpleDraweeView sim;

    }
}

