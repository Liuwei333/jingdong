package com.example.imitatejingdong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ExpanableView extends ExpandableListView {
    public ExpanableView(Context context) {
        super(context);
    }

    public ExpanableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpanableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, i);
    }
}
