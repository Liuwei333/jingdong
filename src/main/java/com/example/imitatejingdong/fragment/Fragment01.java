package com.example.imitatejingdong.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imitatejingdong.R;
import com.example.imitatejingdong.adapter.Myadapte;
import com.example.imitatejingdong.bean.JiuGongGe_bean;
import com.example.imitatejingdong.bean.MessageEvent;
import com.example.imitatejingdong.bean.ShouYeBean;
import com.example.imitatejingdong.mapView.MapActivity;
import com.example.imitatejingdong.model.MyModel;
import com.example.imitatejingdong.presenter.MyPresenter;
import com.example.imitatejingdong.presenter.MyPresenterView;
import com.example.imitatejingdong.view.GlideImageLoader;
import com.example.imitatejingdong.view.LogView;
import com.example.imitatejingdong.view.SeekActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/17.
 */

public class Fragment01 extends Fragment implements LogView {

    private static final String TAG = "Fragment01";
    List<JiuGongGe_bean.DataBean> data;
    TextView news;
    Unbinder unbinder;
    private Myadapte myadapter;
    Handler handler = new Handler();
    private int mDistanceY;

    int REQUEST_CODE = 1001;
    int REQUEST_IMAGE = 1002;
    private XRecyclerView xrc;
    private MyPresenterView myPresenter;
   /* private Banner banner;*/
    private View view;
    private List<ShouYeBean.DataBean> dataType;
    private LinearLayout lin;
    private TextView sousuo;
    private View inflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //导入
        view = View.inflate(getActivity(), R.layout.fragment01, null);
        sousuo = view.findViewById(R.id.sousuo);
        ImageView code = view.findViewById(R.id.f1_erweima);
        TextView codeSao = view.findViewById(R.id.sao);
        news = view.findViewById(R.id.news);
        lin = view.findViewById(R.id.lin);
        xrc = view.findViewById(R.id.xrc);

        inflate = View.inflate(getActivity(), R.layout.shouye_banner, null);

        xrc.addHeaderView(inflate);

        //注册
        EventBus.getDefault().register(this);

        //点击事件调到地图
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });

        //点击事件历史记录
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeekActivity.class);
                startActivity(intent);
            }
        });



        MyPresenter mypresenter = new MyPresenter(this);
        //p的方法
        mypresenter.getmv1(this, new MyModel());
        mypresenter.getmv(getContext(), this, new MyModel());

       /* banner = view.findViewById(R.id.myxbanner);*/


        //二维码相册的点击事件
        codeSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        //二维码的点击事件
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);

                startActivityForResult(intent, REQUEST_CODE);
/*//打开闪光灯
                CodeUtils.isLightEnable(true);*/

            }
        });

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

/** * 处理二维码扫描结果 */

        if (requestCode == REQUEST_CODE) {

//处理扫描结果（在界面上显示）

            if (null != data) {

                Bundle bundle = data.getExtras();

                if (bundle == null) {

                    return;

                }

                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {

                    String result = bundle.getString(CodeUtils.RESULT_STRING);

                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {

                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();

                }

            }

        }

        //对图片中的二维码选择进行解析
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getActivity().getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片

                    CodeUtils.analyzeBitmap(String.valueOf(mBitmap), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
//这里是调用Bean类 并且接受回调回来的数据

    @Subscribe(threadMode = ThreadMode.MAIN)

    public void Event(MessageEvent messageEvent) {

        sousuo.setText(messageEvent.getName());

    }
    //解除注册
    List<String> mListImage = new ArrayList<>();
    @Override
    public void setadapter(Context context, ShouYeBean shouye_bean) {
        Log.d(TAG, "setadapter:========= " + shouye_bean);
        if (shouye_bean != null) {

            List<ShouYeBean.DataBean> data1 = shouye_bean.getData();
            for (int i = 0; i < data1.size(); i++) {
                mListImage.add(data1.get(i).getIcon());
            }

            XBanner myxbanner = inflate.findViewById(R.id.myxbanner);
            myxbanner.setData(mListImage, null);
            myxbanner.setmAdapter(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, View view, int position) {
                    Glide.with(getContext()).load(mListImage.get(position)).into((ImageView) view);
                }
            });


            xrc.setLayoutManager(new LinearLayoutManager(context));
            xrc.setLoadingMoreEnabled(false);

            //上下拉刷新
            xrc.setLoadingListener(new XRecyclerView.LoadingListener() {

                private FrameLayout fg;

                @Override
                public void onRefresh() {
                    fg = view.findViewById(R.id.fg);
                    fg.setVisibility(View.GONE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fg.setVisibility(View.VISIBLE);
                            xrc.refreshComplete();
                        }
                    }, 1000);
                }

                @Override
                public void onLoadMore() {
                    Toast.makeText(getActivity(), "皮一下", Toast.LENGTH_SHORT).show();
                    xrc.refreshComplete();
                }
            });

            myadapter = new Myadapte(context, this.data, shouye_bean);
            xrc.setAdapter(myadapter);
            Log.d(TAG, "setadapter:+++++++++++++++++++++++ " + this.data);

            xrc.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    //滑动的距离
                    mDistanceY += dy;
                    //toolbar的高度
                    FrameLayout f1_frag = view.findViewById(R.id.fg);
                    int toolbarHeight = f1_frag.getBottom();

                    //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                    if (mDistanceY <= 300) {
                        float scale = (float) mDistanceY / 300;
                        float alpha = scale * 255;
                        f1_frag.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));

                    } else {
                        //将标题栏的颜色设置为完全不透明状态
                        f1_frag.setBackgroundResource(R.color.xian);

                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "空的", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setadapter1(List<JiuGongGe_bean.DataBean> data) {
        this.data = data;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
        if (myPresenter != null) {
            myPresenter.onDestroy();
            myPresenter = null;
            System.gc();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
