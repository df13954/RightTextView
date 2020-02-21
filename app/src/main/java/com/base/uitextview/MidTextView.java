package com.base.uitextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MidTextView extends View {


    private String mContent = "";
    private static final String KEY_DOT = "...";
    private int mWidth;
    private int mHeight;

    private String rightContent = "";
    private int size;
    private int color;
    private Paint mPaint = new Paint();

    public MidTextView(Context context) {
        super(context);
        init(null);
    }

    public MidTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MidTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MidTextView);
            if (typedArray != null) {
                String c = typedArray.getString(R.styleable.MidTextView_midLeftText);
                if (!TextUtils.isEmpty(c)) {
                    mContent = c;
                }
                String r = typedArray.getString(R.styleable.MidTextView_midRightText);
                if (!TextUtils.isEmpty(r)) {
                    rightContent = r;
                }
                size = typedArray.getDimensionPixelSize(R.styleable.MidTextView_midLeftTextSize, 50);

                color = typedArray.getColor(R.styleable.MidTextView_midLeftTextColor, Color.BLACK);

                typedArray.recycle();
            }
        }
        mPaint.setColor(color);
        //定大小,才能确定宽度
        mPaint.setTextSize(size);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽高
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.EXACTLY == hMode) {
            sizeH = getPaddingTop() + sizeH + getPaddingBottom();
        } else {
            //文字高度
            Paint.FontMetrics metrics = mPaint.getFontMetrics();
            sizeH = (int) (metrics.bottom - metrics.top);
        }
        setMeasuredDimension(sizeW, sizeH);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float baseY = mHeight / 2 + Math.abs(mPaint.ascent() + mPaint.descent()) / 2;

        //计算右边文字宽度
        float rightLen = mPaint.measureText(rightContent);
        //左边长度
        float leftLen = mPaint.measureText(mContent);

        //正常情况,不满
        if ((leftLen + rightLen) < mWidth) {
            //直接写文字
            canvas.drawText(mContent + rightContent, 0, baseY, mPaint);
            return;
        }
        //当左边文字,和右边文字满宽度之后,需要裁减
        float dotLne = mPaint.measureText(KEY_DOT);
        // 计算可用宽度
        float maxLne = mWidth - rightLen - dotLne;
        String txt = "";
        for (int i = 1; i < mContent.length(); i++) {
            txt = mContent.substring(0, mContent.length() - i);
            if (mPaint.measureText(txt) < maxLne) {
                break;
            }
        }
        canvas.drawText(txt + KEY_DOT + rightContent, 0, baseY, mPaint);
    }

    public void setLeftContent(String content) {
        mContent = content;
        invalidate();
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent;
        invalidate();

    }


    public void setSize(int size) {
        //自己转换
        this.size = size;
        mPaint.setTextSize(size);
        invalidate();

    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(color);
        invalidate();

    }
}
