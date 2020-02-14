package com.base.uitextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author dr
 * @date 2020-02-14
 * @description 当文字 + 图片占满一行之后,文字需要...处理
 */
public class RightTextView extends View {
    private static final String TAG = "HotView";

    /**
     * 画笔
     */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 右边的图片
     */
    private Bitmap mBitmap;
    /**
     * 点点点的长度
     */
    private float mDotLen;

    private static final String KEY_DOT = "...";


    /**
     * 我字测试字测试字测试试试试l
     */
    private String mContent = "";
    private static final int KEY_MARGIN_LEFT = 12;
    private static final int KEY_MARGIN_RIGHT = 12;


    private int size = 50;


    private int color = Color.BLACK;


    private int defRes = -1;//R.mipmap.ic_launcher;
    /**
     * 控件的宽高
     */
    private int width;
    private int height;

    public RightTextView(Context context) {
        super(context);
        init(null);
    }

    public RightTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RightTextView);
            if (typedArray != null) {
                String c = typedArray.getString(R.styleable.RightTextView_leftText);
                if (!TextUtils.isEmpty(c)) {
                    mContent = c;
                }
                size = typedArray.getDimensionPixelSize(R.styleable.RightTextView_leftTextSize, 50);

                color = typedArray.getColor(R.styleable.RightTextView_leftTextColor, Color.BLACK);

                defRes = typedArray.getResourceId(R.styleable.RightTextView_rightRes, -1);

                typedArray.recycle();
            }
        }
        mPaint.setColor(color);
        mPaint.setTextSize(size);
        if (defRes != -1) {
            mBitmap = BitmapFactory.decodeResource(getContext()
                    .getResources(), defRes);
        }
        mDotLen = mPaint.measureText(KEY_DOT);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
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
            //文字高度, 和 图片高度,取最大
            Paint.FontMetrics metrics = mPaint.getFontMetrics();
            sizeH = (int) (getPaddingTop() + Math.max(metrics.bottom - metrics.top,
                    mBitmap == null ? 0 : mBitmap.getHeight()));
        }
        setMeasuredDimension(sizeW, sizeH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画文字
        float baseY = height / 2 + Math.abs(mPaint.ascent() + mPaint.descent()) / 2;

        float measureTextLen = mPaint.measureText(mContent);

        if (mBitmap == null) {
            //只处理文字
            if (measureTextLen > width) {
                String sub = "";
                //截取文字
                for (int i = 1; i < mContent.length(); i++) {
                    String substring = mContent.substring(0, mContent.length() - i);
                    if (mPaint.measureText(substring) < (width - mDotLen - KEY_MARGIN_RIGHT)) {
                        //最后追加...
                        sub = substring + KEY_DOT;
                        break;
                    }
                }
                canvas.drawText(sub, 0, baseY, mPaint);
            } else {
                canvas.drawText(mContent, 0, baseY, mPaint);
            }
            return;
        }

        //如果文字宽度 +  图片宽度 大于控件宽度,裁切文字,裁剪多少呢?

        //先计算出文字显示的最大宽度(宽 - 图片宽 - 点点点的宽 - 左右边距)

        float textMaxLen = width - mBitmap.getWidth() - mDotLen - KEY_MARGIN_LEFT - KEY_MARGIN_RIGHT;

        if (measureTextLen + mBitmap.getWidth() + KEY_MARGIN_LEFT + KEY_MARGIN_RIGHT > width) {
            String sub = "";
            //截取文字
            for (int i = 1; i < mContent.length(); i++) {
                String substring = mContent.substring(0, mContent.length() - i);
                if (mPaint.measureText(substring) < textMaxLen) {
                    sub = substring + KEY_DOT;
                    break;
                }
            }
            canvas.drawText(sub, 0, baseY, mPaint);
            canvas.drawBitmap(mBitmap, mPaint.measureText(sub) + KEY_MARGIN_LEFT, height / 2 - mBitmap.getHeight() / 2, mPaint);
        } else {
            canvas.drawText(mContent, 0, baseY, mPaint);
            canvas.drawBitmap(mBitmap, measureTextLen + KEY_MARGIN_LEFT, height / 2 - mBitmap.getHeight() / 2, mPaint);
        }
    }

    public void setContent(String content) {
        mContent = content;
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(color);
        invalidate();
    }

    public void setSize(int size) {
        this.size = size;
        mPaint.setTextSize(size);
        invalidate();
    }

    /**
     * 还没做空处理
     *
     * @param defRes
     */
    public void setDefRes(int defRes) {
        this.defRes = defRes;
        if (-1 == defRes) {
            mBitmap = null;
            invalidate();
            return;
        }
        mBitmap = BitmapFactory.decodeResource(getContext()
                .getResources(), defRes);
        invalidate();
    }
}
