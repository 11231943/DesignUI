package com.wheat.test.tabhostframe.view.side;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.wheat.test.tabhostframe.R;

/**
 * Created by wheat on 16/2/23.
 * <p/>
 * 抽屉式  缩放效果
 * 属性动画 TraslationX
 * getScrollX :mMenuWidth ~0
 * 跳用动画时
 * <p/>
 * 1.内容区域 1.0～0.7的缩放效果
 * scale:1.0~0.1
 * 0.7 + 0.3*scale
 * <p/>
 * 2.菜单的偏移量修改
 * <p/>
 * 3.菜单的显示有缩放效果，以及透明度的变化
 * 缩放 0.7～1.0
 * 1.0- scale *0.3
 * 透明度 0.6～1.0
 * <p/>
 * 0.6 ＋ 0.4 ＊(1-scale)
 */
public class SideMenu extends HorizontalScrollView {

    private LinearLayout mWapper;

    private ViewGroup mMenu;

    private ViewGroup mContent;

    private int mScreenWidth;

    private int mMenuRightPadding = 50;

    private boolean once = false;

    private boolean isOpen = false;

    private int mMenuWidth;


    public SideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public SideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.side_menu, defStyleAttr, 0);
        mMenuRightPadding = a.getDimensionPixelSize(R.styleable.side_menu_right_padding, 50);
        a.recycle();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    public SideMenu(Context context) {
        super(context);
    }

    /**
     * 设置子类的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth =    mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 将Menu隐藏
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();
                if (scrollX > mScreenWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 打开菜单
     */
    public void closeMenu() {
        if (!isOpen) return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }


    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    /**
     * 滚动时发生
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth; // 1 ~ 0

        /**
         * 区别1：内容区域1.0~0.7 缩放的效果 scale : 1.0~0.0 0.7 + 0.3 * scale
         *
         * 区别2：菜单的偏移量需要修改
         *
         * 区别3：菜单的显示时有缩放以及透明度变化 缩放：0.7 ~1.0 1.0 - scale * 0.3 透明度 0.6 ~ 1.0
         * 0.6+ 0.4 * (1- scale) ;
         *
         */
        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);

        // 调用属性动画，设置TranslationX
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);

        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, leftAlpha);
        // 设置content的缩放的中心点
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);

    }
}
