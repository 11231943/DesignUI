/*
 * File Name：SlideMenu.java
 * Copyright：Copyright 2008-2013 CiWong.Inc. All Rights Reserved.
 * Description： SlideMenu.java
 * Modify By：PLA-ZJLIU
 * Modify Date：2013-5-10
 * Modify Type：Add
 */
package com.wheat.test.tabhostframe.view.widget.scroller;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.wheat.test.tabhostframe.R;

/**
 * 联系人菜单
 *
 * @author PLA-ZJLIU
 * @version ciwong v.1.0 2013-5-10
 * @since ciwong v.1.0
 */
public class RelationMenu extends LinearLayout implements OnClickListener {
    /**
     * 选择左边菜单
     */
    public static final int SELECT_LEFT = 0;

    /**
     * 选择右边菜单
     */
    public static final int SELECT_RIGHT = 1;

    // private static final int DEF_COLOR = Color.GREEN;

    private int textColor;

    private float textSize;

    private String leftText, rightText;

    private MarqueeTextView leftTextView, rightTextView;

    private Drawable leftSelector, rightSelector;

    private Scroller mScroller;

    private int select;

    private int selectBgColor, normalBgColor;

    private int deltaX, mLastDeltaX;

    private List<Integer> mLastX = new ArrayList<Integer>();

    private Quadrilateral left, right;

    private OnSelectChangedListener mOnSelectChangedListener;

    private Paint mPaint;

    private boolean isAnimStop;

    public RelationMenu(Context context) {
        super(context);
        init();
    }

    public RelationMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.relation_menu);
        select = a.getInt(R.styleable.relation_menu_select, SELECT_LEFT);
        leftText = a.getString(R.styleable.relation_menu_left_text);
        rightText = a.getString(R.styleable.relation_menu_right_text);
        leftSelector = a.getDrawable(R.styleable.relation_menu_left_selector);
        rightSelector = a.getDrawable(R.styleable.relation_menu_right_selector);
        textSize = a.getDimension(R.styleable.relation_menu_text_size, 16);
        textColor = a.getColor(R.styleable.relation_menu_text_color,
                Color.WHITE);
        selectBgColor = a.getColor(R.styleable.relation_menu_select_background,
                0xfff1de5e);
        normalBgColor = a.getColor(R.styleable.relation_menu_normal_background,
                0xff24a4e1);
        a.recycle();
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        leftTextView = new MarqueeTextView(getContext());
        rightTextView = new MarqueeTextView(getContext());

        leftTextView.setBackgroundDrawable(leftSelector);
        leftTextView.setGravity(Gravity.CENTER);
        leftTextView.setTextSize(textSize);
        leftTextView.setTextColor(textColor);

        rightTextView.setBackgroundDrawable(rightSelector);
        rightTextView.setGravity(Gravity.CENTER);
        rightTextView.setTextSize(textSize);
        rightTextView.setTextColor(Color.BLACK);

        if (leftText != null) {
            leftTextView.setText(leftText);
        }
        if (rightText != null) {
            rightTextView.setText(rightText);
        }
        addView(leftTextView);
        addView(rightTextView);
        leftTextView.setOnClickListener(this);
        rightTextView.setOnClickListener(this);
        LayoutParams lp = new LayoutParams(0, LayoutParams.FILL_PARENT, 2.0f);
        if (select == SELECT_LEFT) {
            leftTextView.setLayoutParams(lp);
            lp = new LayoutParams(0, LayoutParams.FILL_PARENT, 1.0f);
            rightTextView.setLayoutParams(lp);
        } else {
            rightTextView.setLayoutParams(lp);
            lp = new LayoutParams(0, LayoutParams.FILL_PARENT, 1.0f);
            leftTextView.setLayoutParams(lp);
        }
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
    }

    /**
     * 设置菜单选项变化事件
     *
     * @param listener
     *            {@link OnSelectChangedListener}
     */
    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        mOnSelectChangedListener = listener;
    }

    /**
     * 设置左侧菜单名
     *
     * @param text
     *            左侧菜单名
     */
    public void setLeftText(CharSequence text) {
        leftTextView.setText(text);
    }

    /**
     * 设置左侧菜单名
     *
     * @param
     *
     */
    public void setLeftText(int resId) {
        leftTextView.setText(resId);
    }

    /**
     * 设置右侧菜单名
     *
     * @param text
     *            右侧菜单名
     */
    public void setRightText(CharSequence text) {
        rightTextView.setText(text);
    }

    /**
     * 设置右侧菜单名
     *
     * @param  
     *
     */
    public void setRightText(int resId) {
        rightTextView.setText(resId);
    }

    /**
     * 设置选中的状态颜色
     *
     * @param color
     *            颜色值
     */
    public void setSelectColor(int color) {
        selectBgColor = color;
    }

    /**
     * 设置没有选中状态
     *
     * @param color
     *            颜色值
     */
    public void setUnSelectColor(int color) {
        normalBgColor = color;
    }

    @SuppressLint({ "DrawAllocation", "DrawAllocation" })
    @Override
    protected void onDraw(Canvas canvas) {

        Log.e("debug", "onDraw  getMeasuredWidth=" + getMeasuredWidth());

        final int width = getMeasuredWidth() / 3;
        final int height = getMeasuredHeight();
        Log.e("debug", "onDraw  width=" + width + ";height=" + height);
        if (width > 0 && height > 0) {
            if (left == null || right == null) {
                System.out.println("走了这里...." + width);
                if (select == SELECT_LEFT) {
                    left = new Quadrilateral(0, 0, width * 2 - 1, height, true,
                            selectBgColor);
                    right = new Quadrilateral(width * 2, 0,
                            getMeasuredWidth() + 1, height, false,
                            normalBgColor);
                } else {
                    left = new Quadrilateral(0, 0, width + 1, height, true,
                            normalBgColor);
                    right = new Quadrilateral(width, 0, getMeasuredWidth() - 1,
                            height, false, selectBgColor);
                }
            } else {
                if (!isAnimStop) {
                    // for (int i = 0; i < mLastX.size(); i++) {
                    // System.out.println("mLastX :" + mLastX.get(i));
                    // }
                    if (mLastX.isEmpty()) {
                        return;
                    }
                    final int deltaX = mLastX.remove(0);
                    if(deltaX==0){
                        return;
                    }
                    left.change(deltaX);
                    right.change(deltaX);

                    LayoutParams lp = (LayoutParams) leftTextView
                            .getLayoutParams();
                    lp.width = leftTextView.getWidth() + deltaX;
                    leftTextView.setLayoutParams(lp);

                    lp = (LayoutParams) rightTextView.getLayoutParams();
                    lp.width = rightTextView.getWidth() - deltaX;
                    rightTextView.setLayoutParams(lp);
                    Log.d("debug", "lp.width2:" + lp.width);
                }
            }
            right.draw(canvas);
            left.draw(canvas);
        }
    }

    @Override
    public void computeScroll() {
        // try {
        // Thread.sleep(50);
        //
        // } catch (Exception e) {
        // }
        Log.e("debug",
                "computeScrollstart :" + mScroller.computeScrollOffset()
                        + ";isAnimStop=" + isAnimStop);
        if (mScroller.computeScrollOffset()) {
            int tmpDeltaX = mScroller.getCurrX() - mLastDeltaX;
            // Log.i("debug", "deltaX:" + tmpDeltaX );
            mLastX.add(tmpDeltaX);
            // Log.i("debug", "     mLastX:" + mLastX);
            mLastDeltaX = mScroller.getCurrX();
            if (tmpDeltaX != 0) {
                deltaX = tmpDeltaX;
            }
            postInvalidate();
        } else {
            isAnimStop = true;

        }
        Log.e("debug", "computeScrollend");
    }

    /**
     * 设置当前项
     *
     * @param index
     *            SELECT_LEFT ， SELECT_LEFT
     */
    public void setCurItem(int index) {
        Log.e("debug", "setCurItemstart");
        // if(mLastX.size()>0){
        mLastX.clear();
        // }
        if (index != SELECT_LEFT && index != SELECT_RIGHT) {
            return;
        }
        if (index == select) {
            return;
        }
        Log.i("debug", "setCurItem");
        isAnimStop = false;
        final int width;
        if (index == SELECT_LEFT) {
            width = getMeasuredWidth() / 3;
            left.setColor(selectBgColor);
            right.setColor(normalBgColor);
            leftTextView.setTextColor(Color.WHITE);
            rightTextView.setTextColor(Color.BLACK);
        } else {
            width = -getMeasuredWidth() / 3;
            right.setColor(selectBgColor);
            left.setColor(normalBgColor);
            rightTextView.setTextColor(Color.WHITE);
            leftTextView.setTextColor(Color.BLACK);
        }
        mLastDeltaX = mScroller.getCurrX();
        mScroller.startScroll(mLastDeltaX, 0, width, 0, 400);
        invalidate();
        select = index;
        if (mOnSelectChangedListener != null) {
            mOnSelectChangedListener.onSelectedChanged(select);
        }
        Log.e("debug", "setCurItemend");
    }

    public int getCurItem() {
        return select;
    }

    @Override
    public void onClick(View v) {
        int temp = -1;
        Log.i("debug", "onClick");
        if (v == leftTextView) {
            temp = SELECT_LEFT;
        } else if (v == rightTextView) {
            temp = SELECT_RIGHT;
        }
        if (temp != -1) {
            if (select == temp || !isAnimStop) {
                return;
            }
            setCurItem(temp);
        }
    }

    private class Quadrilateral {
        private static final int margin = 20;

        private static final int duration = 2;

        private int left, top, right, bottom, color;

        private Path mPath;

        private boolean isLeft;

        public Quadrilateral(int left, int top, int right, int bottom,
                             boolean isLeft, int color) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.isLeft = isLeft;
            this.color = color;
            mPath = new Path();
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void change(int deltaX) {
            if (isLeft) {
                right += deltaX;
            } else {
                left += deltaX;
            }
        }

        public void draw(Canvas canvas) {
            if (left >= 0 && top >= 0 && right >= 0 && bottom >= 0) {
                mPaint.setAntiAlias(true);
                mPaint.setColor(color);
                mPath.reset();
                if (isLeft) {
                    mPath.moveTo(left, top);
                    mPath.lineTo(right + margin - duration, top);
                    mPath.lineTo(right - margin - duration, bottom);
                    mPath.lineTo(left, bottom);
                } else {
                    mPath.moveTo(left + margin + duration, top);
                    mPath.lineTo(right, top);
                    mPath.lineTo(right, bottom);
                    mPath.lineTo(left - margin + duration, bottom);
                }
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                mPath.reset();
            }
        }
    }

    public static interface OnSelectChangedListener {

        /**
         * 选择项发生变化
         *
         * @param select
         *            {@link RelationMenu#SELECT_LEFT}<br />
         *            {@link RelationMenu#SELECT_RIGHT}
         */
        void onSelectedChanged(int select);
    }

}
