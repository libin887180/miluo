package com.zhongdi.miluo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lcodecore on 2017/3/7.
 */

public class LoadBullView extends View {

    public static final int DEFAULT_SIZE = 20; //dp
    private float circleSpacing;

    private float[] scaleFloats = new float[]{1f, 1f, 1f};

    private ArrayList<ValueAnimator> mAnimators;
    private Map<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();
    private Paint mPaint;

    public LoadBullView(Context context) {
        this(context, null);
    }

    public LoadBullView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadBullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int default_size = DensityUtil.dp2px(context, DEFAULT_SIZE);
        LayoutParams params = new LayoutParams(default_size, default_size, Gravity.CENTER);
        setLayoutParams(params);

        circleSpacing = DensityUtil.dp2px(context, 4);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public void setIndicatorColor(int color) {
        mPaint.setColor(color);
    }

    private int normalColor = 0xffeeeeee;
    private int animatingColor = 0xffe75946;

    public void setNormalColor(@ColorInt int color) {
        normalColor = color;
    }

    public void setAnimatingColor(@ColorInt int color) {
        animatingColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 10;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        for (int i = 0; i < 3; i++) {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }
        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimators != null) for (int i = 0; i < mAnimators.size(); i++) {
            mAnimators.get(i).cancel();
        }
    }

    public void startAnim() {
        if (mAnimators == null) createAnimators();
        if (mAnimators == null) return;
        if (isStarted()) return;

        for (int i = 0; i < mAnimators.size(); i++) {
            ValueAnimator animator = mAnimators.get(i);

            //when the animator restart , add the updateListener again because they was removed by animator stop .
            ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(animator);
            if (updateListener != null) {
                animator.addUpdateListener(updateListener);
            }
            animator.start();
        }
        setIndicatorColor(animatingColor);
    }

    public void stopAnim() {
        if (mAnimators != null) {
            for (ValueAnimator animator : mAnimators) {
                if (animator != null && animator.isStarted()) {
                    animator.removeAllUpdateListeners();
                    animator.end();
                }
            }
        }
        setIndicatorColor(normalColor);
    }

    private boolean isStarted() {
        for (ValueAnimator animator : mAnimators) {
            return animator.isStarted();
        }
        return false;
    }

    private void createAnimators() {
        mAnimators = new ArrayList<>();
        int[] delays = new int[]{120, 240, 360};
        for (int i = 0; i < 3; i++) {
            final int index = i;

            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnim.setStartDelay(delays[i]);

            mUpdateListeners.put(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimators.add(scaleAnim);
        }
    }


}
