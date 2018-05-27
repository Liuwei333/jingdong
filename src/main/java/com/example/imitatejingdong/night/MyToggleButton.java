package com.example.imitatejingdong.night;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.imitatejingdong.R;

/**
 * Created by Administrator on 2018/5/26.
 */

public class MyToggleButton extends View {

    boolean state = false;

    OnButton listener;

    Bitmap buttonBackGroud ;
    Bitmap  buttonslidingBack;
    private int currentX;
    private boolean isSilding;


    //测量 --   测量的模式，加上测量的大小     measure  ---  onmeasure     setMeasuredDimension
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(buttonBackGroud.getWidth(),buttonBackGroud.getHeight());


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 第一个参数是要画的图片
         * 第二个参数是离左边的距离
         * 第三个参数是离顶部的距离
         * 第四个参娄是画笔
         */

        canvas.drawBitmap(buttonBackGroud,0,0,null);
        //我们要判断到  是

        if (isSilding){
            //  hua  dong de zhhuang tai

            int left = buttonBackGroud.getWidth()-buttonslidingBack.getWidth();

            int onLeft = currentX-buttonslidingBack.getWidth()/2;

            if (onLeft<0){   //

                onLeft=0;
            }else if (onLeft>left){
                onLeft =left;
            }

            canvas.drawBitmap(buttonslidingBack,onLeft,0,null);

        }else {

            //画滑动块
            if (state){
                //是开的状态
                canvas.drawBitmap(buttonslidingBack,buttonBackGroud.getWidth()-buttonslidingBack.getWidth(),0,null);
            }else{
                //关的状态
                canvas.drawBitmap(buttonslidingBack,0,0,null);
            }

        }





    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                //得到当前的点击的座标
                currentX = (int) event.getX();

                //
                isSilding = true;

                break;
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                currentX = (int) event.getX();
                isSilding = false;


                if (listener!=null){
                    //进行回调
                    listener.setOnButtonChangeState(state);
                }

                if (currentX>buttonBackGroud.getWidth()/2){
                    //kai

                    state = true;
                }else{
                    state = false;
                }

                break;
        }



        //onDraw的方法的一个绘
        invalidate();

        return true;
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 开关的状态   true是开的状态  false是关的状态
     */
    public void setState(boolean flag) {

        state = flag;
    }


    //开关的背影图片
    public void seButtonBackGround(int switch_background) {
        buttonBackGroud = BitmapFactory.decodeResource(getResources(),switch_background);
    }

    //滑动块的背景图片
    public void setSlidingBack(int slide_button_background) {

        buttonslidingBack = BitmapFactory.decodeResource(getResources(),slide_button_background);
    }


    //回调的方法
    public void setButtonListener(OnButton listener){

        this.listener = listener;
    }
}
