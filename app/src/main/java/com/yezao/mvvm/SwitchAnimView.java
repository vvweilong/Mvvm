package com.yezao.mvvm;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwitchAnimView extends RelativeLayout {


    private final String TAG = getClass().getSimpleName();

    public SwitchAnimView(Context context) {
        this(context, null);
    }

    public SwitchAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(Color.TRANSPARENT);//设置整体为背景透明
        //设置backAnimView的 背景view
        backAnimView = new View(context);
        backAnimView.setBackgroundColor(Color.WHITE);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(backAnimView, layoutParams);

        LayoutParams tagLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagLayoutParams.addRule(CENTER_IN_PARENT);
        tagTextView = new TextView(context);
        tagTextView.setBackgroundResource(R.drawable.tag_bg);
        addView(tagTextView, tagLayoutParams);
        shadowTagTextView = new TextView(context);
        shadowTagTextView.setLayoutParams(tagLayoutParams);
        shadowTagTextView.setBackgroundResource(R.drawable.tag_bg);
        addView(shadowTagTextView,tagLayoutParams);

        tagTextView.setVisibility(INVISIBLE);
        shadowTagTextView.setVisibility(INVISIBLE);

        backAnimView.setVisibility(INVISIBLE);
    }

    private View backAnimView;

    private TextView tagTextView;
    private TextView shadowTagTextView;


    private int viewHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.getMode(heightMeasureSpec));
        viewHeight = MeasureSpec.getSize(measuredHeight);
    }

    /**
     * 功能有两个  一： 在通知需要展示的时候首先展示TAG view  并提供一个 扩展的动画
     * <p>
     * 扩展动画 采用一个View 来进行  进行高度动画 @{@link SwitchAnimView#backAnimView}
     */
    final long ANIM_DURATION = 300;
    final long ANIM_DURATION_SHOT = 300;

    private void doSwitchTagAnim(String newTagStr,boolean up) {
        //记录原始位置
        int top = tagTextView.getTop();
        int bottom = tagTextView.getBottom();
        //将shadowTagView 进行向上的隐藏动画

        // 设置动画需求的初始状态
        shadowTagTextView.setVisibility(VISIBLE);
        tagTextView.setAlpha(0f);

        //向上的隐藏动画
        ValueAnimator hideSwitchAnimator = createHideSwitchAnimator(top,bottom,shadowTagTextView,up);
        ValueAnimator showSwitchAnimator = createShowSwitchAnimator(top, bottom, newTagStr);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(hideSwitchAnimator, showSwitchAnimator);
        animatorSet.start();
    }
    /**
     * 操作 shadow 显示
     *
     * */
    private ValueAnimator createHideSwitchAnimator(int top,int bottom,View targetView,boolean up) {
        ValueAnimator upHideAnim = new ValueAnimator();
        upHideAnim.setDuration(ANIM_DURATION_SHOT);
        upHideAnim.setFloatValues();
        //两步  第一步 向上移动 一定的距离 并隐藏
        upHideAnim.setFloatValues(1f, 0f);
        int distance=up?200:-200;
        upHideAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                //首先是 透明度的变化
                int dd =  Math.round(animatedFraction*distance);
                int nextTop= top -dd;
                int nextBottom = bottom- dd;


                targetView.setAlpha(1 - animatedFraction);// 渐隐过程
                //然后是位移变化
                targetView.setTop(nextTop);
                targetView.setBottom(nextBottom);
//                ViewCompat.offsetTopAndBottom(tagTextView, -2);
            }
        });

        upHideAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //重置位置与可见
                targetView.setVisibility(INVISIBLE);
                targetView.setTop(top);
                targetView.setBottom(bottom);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return upHideAnim;
    }
    /**
     * 操作 tagview
     *
     * */
    private ValueAnimator createShowSwitchAnimator(int originTop, int originBottom, String newTagStr) {


        ValueAnimator upShowAnim = new ValueAnimator();
        upShowAnim.setDuration(ANIM_DURATION_SHOT);
        upShowAnim.setFloatValues();
        //两步  第一步 向上移动 一定的距离 并隐藏
        upShowAnim.setFloatValues(0f, 1f);
        upShowAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                //首先是 透明度的变化
                tagTextView.setAlpha(animatedFraction);// 渐隐过程
            }
        });

        upShowAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tagTextView.setText(newTagStr);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        return upShowAnim;
    }

    private void doExtandBackdrawAnim() {
        ValueAnimator heightValueAnimator = new ValueAnimator();
        ((ValueAnimator) heightValueAnimator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (backAnimView == null) {
                    return;
                }
                float animatedFraction = animation.getAnimatedFraction();
                int nextHeight = Math.round(animatedFraction * viewHeight);

                ViewGroup.LayoutParams layoutParams = backAnimView.getLayoutParams();
                layoutParams.height = nextHeight;
                Log.i(TAG, "onAnimationUpdate: nextHeight = " + nextHeight);
                backAnimView.setLayoutParams(layoutParams);
            }
        });
        heightValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                backAnimView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        heightValueAnimator.setDuration(ANIM_DURATION);
        heightValueAnimator.setFloatValues(0f, 1f);
        heightValueAnimator.setInterpolator(new DecelerateInterpolator());
        heightValueAnimator.start();
    }

    private void doCollapsBackDrawAnim() {
        ValueAnimator heightValueAnimator = new ValueAnimator();
        ((ValueAnimator) heightValueAnimator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (backAnimView == null) {
                    return;
                }
                float animatedFraction = animation.getAnimatedFraction();
                int nextHeight = Math.round(animatedFraction * viewHeight);

                ViewGroup.LayoutParams layoutParams = backAnimView.getLayoutParams();
                layoutParams.height = viewHeight - nextHeight;
                Log.i(TAG, "onAnimationUpdate: nextHeight = " + nextHeight);
                backAnimView.setLayoutParams(layoutParams);
            }
        });
        heightValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                backAnimView.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        heightValueAnimator.setDuration(ANIM_DURATION);
        heightValueAnimator.setFloatValues(1f, 0f);
        heightValueAnimator.start();
    }

    public void showTagView(String tagContent,boolean direction) {
        //四种情况 首先是 如果tag 目前没有显示  并且 tagContent 不为空 需要转为显示状态 并显示动画
        boolean currentIsShow = tagTextView.getVisibility() == VISIBLE;
        boolean hasNewTag = !TextUtils.isEmpty(tagContent);
        CharSequence currentTag = tagTextView.getText();//当前现实的内容
        //情况 一  控制 出现或隐藏
        if (!currentIsShow && hasNewTag) {//当前未显示 并有新的tag需要显示
            Log.i(TAG, "showTagView: 显示");
            doExtandBackdrawAnim();
            tagTextView.setVisibility(VISIBLE);
            tagTextView.setText(tagContent);
            shadowTagTextView.setText(tagContent);
            return;
        }
        if (currentIsShow && !hasNewTag) {//当前正在显示，新的tag 为空 需要进行隐藏操作
            Log.i(TAG, "showTagView: 隐藏");
            doCollapsBackDrawAnim();
            tagTextView.setVisibility(INVISIBLE);
            tagTextView.setText("");
            shadowTagTextView.setText("");
            return;
        }

        //情况二  进行标签的切换显示
        if (currentIsShow && hasNewTag && !TextUtils.equals(currentTag, tagContent)) {//当前正在显示 并且有新的tag 新tag与现有tag内容不同
            Log.i(TAG, "showTagView: 切换");
            doSwitchTagAnim(tagContent,direction);
            return;
        }
        Log.i(TAG, "showTagView: default");
    }

}
