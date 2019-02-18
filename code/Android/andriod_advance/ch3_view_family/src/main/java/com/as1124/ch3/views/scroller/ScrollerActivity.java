package com.as1124.ch3.views.scroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.as1124.ch3.views.R;

/**
 * 了解Android种Scroller
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ScrollerActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        TextView textView = findViewById(R.id.text_scroller_impl);
        textView.setText("Scroller的使用是为了替代View自身scrollTo、scrollBy瞬间完成滑动诞生的, "
                + "它的作用就是实现平滑的滑动过度，类比translate动画;将位移distance分割成小段放到duration中，通过累积实现看似平滑的过程. "
                + "注意Scroller自身并不处理滑动, 而是通过记录view滑动时相关的参数信息供"
                + "开发者调用(getCurrX、getCurrY)然后仍然执行View自身的scroll以及draw实现View的平移");

    }

}
