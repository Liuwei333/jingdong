package com.example.imitatejingdong.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imitatejingdong.R;
import com.example.imitatejingdong.api.IconApi;
import com.example.imitatejingdong.bean.MessageBean;
import com.example.imitatejingdong.bean.NameBean;
import com.example.imitatejingdong.dengzhunbean.DengluBean;
import com.example.imitatejingdong.filePresenter.NewsPresenter;
import com.example.imitatejingdong.fileView.NewsView;
import com.example.imitatejingdong.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProofActivity extends AppCompatActivity implements NewsView {

    @BindView(R.id.fanhui)
    Button fanhui;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.nicheng)
    EditText nicheng;

    private PopupWindow popupWindow;
    private View parent;
    private Button btn_xiangji;
    private Button btn_xiangce;
    private Button btn_cancel;
    private File tempFile;
    private static final String PHOTO_FILE_NAME = "/storage/emulated/legacy/liuwei.png";
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private NewsPresenter presenter;
    private int uid;
    private String nickName;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof);
        ButterKnife.bind(this);

        presenter = new NewsPresenter();
        presenter.attachView(this);


        //接受到的值
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= getSharedPreferences("proof", ProofActivity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        nickName = sharedPreferences.getString("name", "");
        uid = sharedPreferences.getInt("uid",10447);
        name.setText(nickName);
        nicheng.setText(nickName);


        //返回
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nick();

            }
        });


        //头像
        init();

        //名字点击
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProofActivity.this,"用户名不支持修改呦~",Toast.LENGTH_SHORT).show();
            }
        });

        //popu
        //打气操作  查找新建的layout布局
        View contentView = View.inflate(this, R.layout.popu_layout, null);
        //new popupWindow
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //设置
        //获得焦点
        popupWindow.setFocusable(true);
        //点击弹窗
        popupWindow.setTouchable(true);
        //点击空格外弹窗
        popupWindow.setOutsideTouchable(true);
        //背景图点击
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //创建父窗体的视图
        //配合下面的登录点击事件里面的操作
        parent = View.inflate(this, R.layout.activity_proof, null);

        //找到popUpWindown里面的控件
        //减少findview使用
        btn_xiangji =  contentView.findViewById(R.id.btn_xiangji);

        btn_xiangce=  contentView.findViewById(R.id.btn_xiangce);
        btn_cancel = contentView.findViewById(R.id.btn_cancel);

        btn_xiangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                // 激活相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (hasSdcard()) {
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);

                    // 从文件中创建uri
                    Uri uri = Uri.fromFile(tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    //保存图片
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File( PHOTO_FILE_NAME)));
                }
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                startActivityForResult(intent, PHOTO_REQUEST_CAREMA);

                popupWindow.dismiss();


            }
        });
        btn_xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                // 激活系统图库，选择一张图片
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intent1,PHOTO_REQUEST_GALLERY);
                popupWindow.dismiss();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //这是点击任何一个地方都返回
        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //消失
                popupWindow.dismiss();
            }
        });

    }

    //修改
    private void nick() {
        string = nicheng.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IconApi iconApi = retrofit.create(IconApi.class);
        iconApi.doName(uid,string).enqueue(new Callback<NameBean>() {
            @Override
            public void onResponse(Call<NameBean> call, Response<NameBean> response) {
                NameBean body = response.body();
                String msg = body.getMsg();
                if(msg.equals("昵称修改成功")){
                  name.setText(string);

                  Toast.makeText(ProofActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<NameBean> call, Throwable t) {

            }
        });
    }

    private void init() {
        //请求数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IconApi iconApi = retrofit.create(IconApi.class);
        iconApi.doGet(uid).enqueue(new Callback<DengluBean>() {
            @Override
            public void onResponse(Call<DengluBean> call, Response<DengluBean> response) {
                DengluBean body = response.body();
                if(body.getMsg().equals("获取用户信息成功")){
                    String icon = body.getData().getIcon();
                    Glide.with(ProofActivity.this).load(icon).into(img);
                }else{
                    Toast.makeText(ProofActivity.this,"空",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DengluBean> call, Throwable t) {

            }
        });
    }


    public void login(View view) {
        //在父窗体的某个位置进行展示....parent代表父窗体的视图,gravity表示弹出的位置/方向,x轴的偏移,  y轴的偏移
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    //拍照片自动在SD卡生成这个照片
    private String path = Environment.getExternalStorageDirectory()+"/liuwei.png";

    private boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }

    }
    /*
    * 剪切图片
    */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(ProofActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                /**
                 * 获得图片
                 */
                img.setImageBitmap(bitmap);
                setImgByStr( bitmap);

            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    //取出自己照片保存到位置
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onSuccess(MessageBean messageBean) {
        String msg = messageBean.getMsg();
        Log.e("zxz",msg);

    }


    public void setImgByStr(Bitmap bitmap) {
        if(bitmap != null){
            // 拿着imagePath上传了
            // ...
        }
        String imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.d("zxz","imagePath:"+imagePath);
        if(imagePath!=null){
            File file=new File(imagePath);//将要保存图片的路径

            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
            presenter.getData(uid+"",photo);
        }


    }
}
