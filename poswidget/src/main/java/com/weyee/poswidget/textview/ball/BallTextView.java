package com.weyee.poswidget.textview.ball;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.appcompat.widget.AppCompatTextView;
import com.weyee.poswidget.R;

/**
 * 球形的TextView，border的颜色和TextView的颜色一致
 *
 * @author wuqi by 2019/5/26.
 */
public class BallTextView extends AppCompatTextView {
    private Paint mPaint;
    private int borderSize;

    public BallTextView(Context context) {
        this(context, null);
    }

    public BallTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BallTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BallTextView);
        int defaultColor = getTextColors().getDefaultColor();

        int color;
        try {
            borderSize = ta.getDimensionPixelSize(R.styleable.BallTextView_border, 1);
            color = ta.getResourceId(R.styleable.BallTextView_android_color, defaultColor);
        } finally {
            ta.recycle();
        }
        // 强制该View文字居中
        setGravity(Gravity.CENTER);
        // 强制该View的背景为透明的
        setBackground(null);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(borderSize);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 必须保证宽度和长度一致，不然画出来的圆有问题
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getCurrentTextColor());
        //@SuppressLint("DrawAllocation") Rect localRect = new Rect();
        //getLocalVisibleRect(localRect);
        float cx = getLeft() + getWidth() / 2;
        float cy = getTop() + getHeight() / 2;
        //float cx = localRect.left + getWidth() / 2;
        //float cy = localRect.top + getHeight() / 2;
        canvas.drawCircle(cx, cy, getWidth() / 2 - borderSize, mPaint);
    }
}
