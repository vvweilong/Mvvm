package com.yezao.mvvm;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MultiTabLayoutView extends FrameLayout {
    public MultiTabLayoutView(Context context) {
        this(context, null);
    }

    public MultiTabLayoutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTabLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private TabLayout mainTabLayout;
    private TabLayout describeTabLayout;

    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.multitab_layout,this);
        mainTabLayout = findViewById(R.id.main_tablayout);
        describeTabLayout = findViewById(R.id.multitab_tablayout);
    }

    public void setmainTabs(String... tabNames) {
        for (String tabName : tabNames) {
            addMainTab(tabName);
        }
    }

    public void setDescribeTabs(String... tabNames) {
        for (String tabName : tabNames) {
            addDescribeTab(tabName);
        }
    }

    public void addMainTab(String tabStr) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(getContext().getResources().getColorStateList(R.color.custom_tab_colors));
        textView.setGravity(Gravity.CENTER);
        textView.setText(tabStr);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        mainTabLayout.addTab(mainTabLayout.newTab().setCustomView(textView));
    }

    public void addDescribeTab(String tabStr) {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getContext().getResources().getColorStateList(R.color.custom_tab_colors));
        textView.setText(tabStr);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        describeTabLayout.addTab(describeTabLayout.newTab().setCustomView(textView));
    }

    int describeState=0;
    long ANIM_DURATION= 500;
    public void hideDescribe(){
        if (describeState==1) {
            return;
        }
        describeState=1;
        ValueAnimator animator=new ValueAnimator();
        animator.setFloatValues(0f,1f);
        animator.setDuration(ANIM_DURATION);
        int distances=describeTabLayout.getHeight();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) describeTabLayout.getLayoutParams();

                float animatedFraction = animation.getAnimatedFraction();
                int nextMargin = Math.round(animatedFraction * distances);
                layoutParams.topMargin=-nextMargin;
                describeTabLayout.setLayoutParams(layoutParams);
                for (int i = 0; i < describeTabLayout.getTabCount(); i++) {
                    describeTabLayout.getTabAt(i).getCustomView().setAlpha(1-animatedFraction);
                }
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
        animator.setFloatValues(1f,0f);
        animator.setDuration(ANIM_DURATION);
        int distances=describeTabLayout.getHeight();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) describeTabLayout.getLayoutParams();
                float animatedFraction = animation.getAnimatedFraction();
                int nextMargin =distances- Math.round(animatedFraction * distances);
                layoutParams.topMargin=-nextMargin;
                describeTabLayout.setLayoutParams(layoutParams);
                for (int i = 0; i < describeTabLayout.getTabCount(); i++) {
                    describeTabLayout.getTabAt(i).getCustomView().setAlpha(animatedFraction);
                }
            }
        });
        animator.start();
    }



}
