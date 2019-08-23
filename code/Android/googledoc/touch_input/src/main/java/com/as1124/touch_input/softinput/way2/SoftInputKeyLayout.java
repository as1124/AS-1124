package com.as1124.touch_input.softinput.way2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.as1124.touch_input.R;
import com.as1124.touch_input.softinput.ExKey;

/**
 * 输入法键盘View中单据一个键（Key）的UI模型
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SoftInputKeyLayout extends LinearLayout {

    /**
     * 按键下在键盘中对应的坐标
     */
    private int mKeyIndex = -1;

    private TextView minorText;

    private TextView majorText;

    private ImageView keyImage;

    public SoftInputKeyLayout(Context context) {
        super(context);
    }

    public SoftInputKeyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        switch (child.getId()) {
            case R.id.text_key_low:
                minorText = (TextView) child;
                break;
            case R.id.text_key_major:
                majorText = (TextView) child;
                break;
            case R.id.image_key_icon:
                keyImage = (ImageView) child;
        }
    }

    public void setKeyIndex(int keyIndex) {
        this.mKeyIndex = keyIndex;
    }

    public int getKeyIndex() {
        return mKeyIndex;
    }

    public void showText(ExKey keyInfo) {
        minorText.setVisibility(View.GONE);
        majorText.setVisibility(View.VISIBLE);
        keyImage.setVisibility(View.GONE);
    }

    public void showIcon(Drawable drawable) {
        minorText.setVisibility(View.GONE);
        majorText.setVisibility(View.GONE);
        keyImage.setImageDrawable(drawable);
        keyImage.setVisibility(View.VISIBLE);
    }

    public void setMajorTextSize(float textSize) {
        majorText.setTextSize(textSize);
    }
}
