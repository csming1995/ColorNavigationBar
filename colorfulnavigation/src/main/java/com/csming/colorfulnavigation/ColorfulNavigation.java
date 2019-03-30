package com.csming.colorfulnavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Created by csming on 2019/3/16.
 */
public class ColorfulNavigation extends LinearLayout {

    private List<ColorfulNavigationItem> mColorfulNavigationItems;
    private List<Item> mItems;

    private int mIndex;
    private int mDrawIndex = 0;

    private int mWidth;
    private int mHeight;
    private int mItemWidth;
    private RectF mRectF;
    private RectF mRightRectF;
    private Paint mPaint;
    private Paint mRightPaint;

    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemSelectedListener mRealOnItemSelectedListener;

    private ValueAnimator mValueAnimator;
    private ValueAnimatorListener mValueAnimatorListener;

    public ColorfulNavigation(Context context) {
        this(context, null);
    }

    public ColorfulNavigation(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorfulNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mColorfulNavigationItems = new LinkedList<>();
        mItems = new LinkedList<>();
        setOrientation(LinearLayout.HORIZONTAL);
        setWillNotDraw(false);
        init();

        mOnItemSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Item item) {
                startAnimator(item.index);

                if (mRealOnItemSelectedListener != null) {
                    mRealOnItemSelectedListener.onItemSelected(item);
                }
            }
        };
    }

    private void init() {
        mPaint = new Paint();
        mRightPaint = new Paint();

        mPaint.setAntiAlias(true);
        mRightPaint.setAntiAlias(true);

        mRectF = new RectF();
        mRightRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);

        mItemWidth = mWidth / mItems.size();
        mRectF.left = mItemWidth * mIndex;
        mRectF.top = 0;
        mRectF.right = mRectF.left + mItemWidth;
        mRectF.bottom = mHeight;

        mRightRectF.left = mRectF.left;
        mRightRectF.top = 0;
        mRightRectF.right = mRectF.right;
        mRightRectF.bottom = mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRectF, mPaint);
        canvas.drawRect(mRightRectF, mRightPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null) {
            mValueAnimator.removeAllUpdateListeners();
        }
    }

    public void add(@NonNull Item item) {
        ColorfulNavigationItem colorfulNavigationItem = new ColorfulNavigationItem(getContext());
        colorfulNavigationItem.setItem(item);

        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        colorfulNavigationItem.setLayoutParams(layoutParams);
        addView(colorfulNavigationItem);
        colorfulNavigationItem.setOnSelectedListener(mOnItemSelectedListener);

        mIndex = mItems.size();
        item.index = mIndex;
        mItems.add(item);
        mColorfulNavigationItems.add(colorfulNavigationItem);

        mPaint.setColor(getResources().getColor(item.color));
        mRightPaint.setColor(getResources().getColor(item.color));
        requestLayout();
    }

    public void setOnItemSelectedListener(final OnItemSelectedListener onItemSelectedListener) {
        this.mRealOnItemSelectedListener = onItemSelectedListener;
    }

    public void setSelectedItem(int index) {
        if (index >= mItems.size()) return;
        mPaint.setColor(getResources().getColor(mItems.get(index).color));
        mRightPaint.setColor(getResources().getColor(mItems.get(index).color));
        invalidate();
        startAnimator(index);
    }

    private void startAnimator(int targetIndex) {
        boolean isRight = mIndex < targetIndex;
        mIndex = targetIndex;
        int targetLeft = mItemWidth * mIndex;
        if (targetLeft != mRectF.left) {
            if (mValueAnimator == null) {
                mValueAnimator = new ValueAnimator();
                mValueAnimatorListener = new ValueAnimatorListener();
                mValueAnimator.addUpdateListener(mValueAnimatorListener);
            }
            mValueAnimator.setFloatValues(mRectF.left, targetLeft/*, targetLeft + (isRight? 20: - 20), targetLeft*/);
            mValueAnimator.setDuration(300);
            mValueAnimator.cancel();
            mValueAnimator.start();
        }
    }

    private class ValueAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            mRectF.left = (float) valueAnimator.getAnimatedValue();
            mRectF.right = mRectF.left + mItemWidth;
            mDrawIndex =  (int) mRectF.left / mItemWidth;
            mRightRectF.left = (mDrawIndex + 1) * mItemWidth;
            mRightRectF.right = mRectF.right;
            mPaint.setColor(getResources().getColor(mItems.get(mDrawIndex).getColor()));
            if (mDrawIndex + 1 < mItems.size()){
                mRightPaint.setColor(getResources().getColor(mItems.get(mDrawIndex + 1).getColor()));
            }
            invalidate();
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Item item);
    }

    public static class Item {
        private int id;
        @DrawableRes
        private int icon;
        @ColorRes
        private int color;
        private String title;
        private int index = 0;


        public Item(int id, @DrawableRes int icon, @ColorRes int color) {
            this(id, icon, color, null);
        }

        public Item(int id, @DrawableRes int icon, @ColorRes int color, String title) {
            super();
            this.id = id;
            this.icon = icon;
            this.color = color;
            this.title = title;
        }

        public int getId() {
            return this.id;
        }

        int getIcon() {
            return this.icon;
        }

        String getTitle() {
            return this.title;
        }

        int getColor() {
            return this.color;
        }
    }
}
