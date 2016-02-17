package com.wheat.test.tabhostframe.view.widget.scroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wheat on 16/2/16.
 */
public class TestView extends View {


    //DIFFERENCE是第一次不同于第二次的部分显示出来
//REPLACE是显示第二次的
//REVERSE_DIFFERENCE 是第二次不同于第一次的部分显示

//INTERSECT交集显示
//UNION全部显示
//XOR补集 就是全集的减去交集生育部分显示


    private Paint mPaint;

    private Path mPath;

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setStyle(Paint.Style.FILL);//设置为空心
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPath = new Path();
    }

    private void drawScene(Canvas canvas) {

        canvas.clipRect(0, 0, 100, 100);

        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 100, 100, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(30, 70, 30, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawText("Clipping", 100, 30, mPaint);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        mPath.moveTo(0, 0);
        mPath.lineTo(360 * 2 - 1 + 20 - 1, 0);
        mPath.lineTo(360 * 2 - 1 - 20 - 1, 145);
        mPath.lineTo(0, 145);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

    }
}
