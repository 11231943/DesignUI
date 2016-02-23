package com.wheat.test.tabhostframe.view.side;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.wheat.test.tabhostframe.R;

/**
 * Created by wheat on 16/2/23.
 * <p/>
 * 左至右  推出来
 */
public class SideMenu1 extends HorizontalScrollView {

    private LinearLayout mWapper;

    private ViewGroup mMenu;

    private ViewGroup mContent;

    private int mScreenWidth;

    private int mMenuRightPadding = 50;

    private boolean once = false;

    private boolean isOpen = false;

    public SideMenu1(Context context, AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public SideMenu1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.side_menu, defStyleAttr, 0);
        mMenuRightPadding = a.getDimensionPixelSize(R.styleable.side_menu_right_padding, 50);
        a.recycle();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    public SideMenu1(Context context) {
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
            mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
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
            this.scrollTo(mScreenWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();
                if (scrollX > mScreenWidth / 2) {
                    this.smoothScrollTo(mScreenWidth, 0);
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
        this.smoothScrollTo(mScreenWidth, 0);
        isOpen = false;
    }


    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
