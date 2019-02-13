package com.as1124.ch3.views.selfdefine;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.as1124.ch3.views.R;

/**
 * 自定义组合控件, 继承已有ViewGroup
 * <ul>
 * <li>填充布局时：attachToRoot = true</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfTitleBar extends RelativeLayout {

    private ImageView titlebarLeft;
    private ImageView titlebarRight;
    private TextView titlebarTitle;
    private int textColor = Color.MAGENTA;

    public SelfTitleBar(Context context) {
        super(context);
        initView(context, null);
    }

    public SelfTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SelfTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        // ATTENTION 这里attachToRoot要设置成 true
        View titleBar = LayoutInflater.from(context).inflate(R.layout.self_title_bar, this, true);
        titlebarLeft = titleBar.findViewById(R.id.title_image_back);
        titlebarRight = titleBar.findViewById(R.id.title_image_options);
        titlebarTitle = titleBar.findViewById(R.id.title_text);
        ViewGroup parentLayout = (ViewGroup) titlebarLeft.getParent();
        parentLayout.setBackgroundColor(Color.BLUE);

        if (attrs != null) {
            TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.SelfTitleBar);
            textColor = values.getColor(R.styleable.SelfTitleBar_title_color, Color.MAGENTA);
            String title = values.getString(R.styleable.SelfTitleBar_title_text);
            setTitle(title);
            values.recycle();
        }

        titlebarTitle.setTextColor(textColor);
    }

    public void setTitle(String title) {
        titlebarTitle.setText(title);
    }
}
