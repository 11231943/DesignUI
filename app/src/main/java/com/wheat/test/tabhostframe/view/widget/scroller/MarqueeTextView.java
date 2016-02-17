package com.wheat.test.tabhostframe.view.widget.scroller;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wheat on 16/2/16.
 */
public class MarqueeTextView extends TextView
{
    /**
     * 构造函数
     *
     * @param context
     *            上下文
     */
    public MarqueeTextView(Context context)
    {
        super(context);
        init();
    }

    /**
     *
     * 构造函数
     *
     * @param context
     *            上下文
     * @param attrs
     *            属性
     */
    public MarqueeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    /**
     *
     * 构造函数
     *
     * @param context
     *            上下文
     * @param attrs
     *            属性
     * @param defStyle
     *            样式
     */
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    void init()
    {
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    @Override
    public boolean isFocused()
    {
        return true;
    }
}
