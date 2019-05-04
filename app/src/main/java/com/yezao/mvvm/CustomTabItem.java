package com.yezao.mvvm;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class CustomTabItem extends LinearLayout {
    public CustomTabItem(Context context) {
        this(context,null);
    }

    public CustomTabItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mainText = new TextView(context);
        describe = new TextView(context);

        LayoutParams mainParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainParams.gravity= Gravity.CENTER;
        mainText.setLayoutParams(mainParams);
        LayoutParams describeParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        describeParams.gravity= Gravity.CENTER;
        describe.setLayoutParams(describeParams);

        mainText.setTextColor(context.getResources().getColorStateList(R.color.custom_tab_colors));
        describe.setTextColor(context.getResources().getColorStateList(R.color.custom_tab_colors));

        mainText.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        describe.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);

        addView(mainText);
        addView(describe);
    }
    private TextView mainText;
    private TextView describe;

    public void setTabText(String tabText){
        mainText.setText(tabText);
    }
    public void setDescribeText(String describeText){
        describe.setText(describeText);
    }
    private final long ANIM_DURATION=500;

    private int describeState=0 ; //0 显示中 1 隐藏中

    public void hideDescribe(){
        if (describeState==1) {
            return;
        }
        describeState=1;
        ValueAnimator animator=new ValueAnimator();
        animator.setFloatValues(1f,0f);
        animator.setDuration(ANIM_DURATION);

        int distance=describe.getHeight();
        int originTop=mainText.getTop();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
//                int nextTop = originTop- Math.round(animatedFraction*distance);
//                int nextBottom = nextTop-distance;
//                describe.setTop(nextTop);
//                describe.setBottom(nextBottom);
                describe.setAlpha(1-animatedFraction);
                ViewCompat.offsetTopAndBottom(describe,-1);
            }
        });
        animator.start();
    }

    public void showDescribe(){
        if (describeState==0) {
            return;
        }
        describeState=0;
        ValueAnimator animator=new ValueAnimator();
        animator.setFloatValues(0f,1f);
        animator.setDuration(ANIM_DURATION);

        int distance=describe.getHeight();
        int targetTop=mainText.getTop();
        int originTop = describe.getTop();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
//                int nextTop = originTop + Math.round(animatedFraction*distance);
//                int nextBottom = nextTop-distance;
//                describe.setTop(nextTop);
//                describe.setBottom(nextBottom);
                describe.setAlpha(animatedFraction);
                ViewCompat.offsetTopAndBottom(describe,1);
            }
        });
        animator.start();
    }

}
