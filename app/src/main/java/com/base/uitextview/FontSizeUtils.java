package com.base.uitextview;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

/**
 * @author dr
 * create at 2019-10-23 15:17
 */
public class FontSizeUtils {
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 同一个TextView设置分段,前后段字体大小不一致
     *
     * @param content   全部内容
     * @param key       分隔符
     * @param textView  tv
     * @param startSize 左边文字大小
     * @param endSize   右边文字大小
     */
    public static void setSubsection(String content, String key, TextView textView, int startSize, int endSize) {
        if (textView == null) {
            return;
        }
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(key)) {
            textView.setText(content + "");
            return;
        }
        int keyIndex = content.indexOf(key);
        if (keyIndex == -1) {
            textView.setText(content);
        } else {
            SpannableStringBuilder sb = new SpannableStringBuilder(content);
            sb.setSpan(new AbsoluteSizeSpan(FontSizeUtils.sp2px(startSize)), 0, keyIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(new AbsoluteSizeSpan(FontSizeUtils.sp2px(endSize)), keyIndex, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(sb);
        }
    }
}
