package com.wheat.test.tabhostframe.view.widget.scroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wheat.test.tabhostframe.R;

/**
 * Created by wheat on 16/2/16.
 */
public class RelationMenu extends LinearLayout {

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

    private int select;

    private TextView leftTextView, rightTextView;

    private int selectBgColor, normalBgColor;

    private Paint mPaint;

    private Drawable leftSelector, rightSelector;

    public RelationMenu(Context context) {
        super(context, null);
        init();
    }

    public RelationMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.relation_menu);
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
        leftTextView = new TextView(getContext());
        rightTextView = new TextView(getContext());

        leftTextView.setBackgroundDrawable(leftSelector);
        leftTextView.setGravity(Gravity.CENTER);
        leftTextView.setTextColor(textColor);
        leftTextView.setTextSize(textSize);

        rightTextView.setBackgroundDrawable(rightSelector);
        rightTextView.setGravity(Gravity.CENTER);
        rightTextView.setTextColor(Color.BLACK);
        rightTextView.setTextSize(textSize);

        if (leftText != null) {
            leftTextView.setText(leftText);
        }
        if (rightText != null) {
            rightTextView.setText(rightText);
        }
        addView(leftTextView);
        addView(rightTextView);
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
        mPaint.setStyle(Paint.Style.FILL);
    }

}
