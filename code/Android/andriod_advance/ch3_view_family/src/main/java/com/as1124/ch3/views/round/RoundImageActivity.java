package com.as1124.ch3.views.round;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;

import com.as1124.ch3.views.R;

/**
 * 圆角/圆形图像的实现；图形相交模式.
 * <br/>
 * <p>
 * 多种方式实现圆形头像
 * <li>裁剪控件的画布区域, {@link android.graphics.Canvas#clipPath(Path)}</li>
 * <li>裁剪图片成圆形</li>
 * <li>设置图形相交模式</li>
 * </p>
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class RoundImageActivity extends Activity {

    private SelfRoundView selfView;

    private SelfRoundImageView roundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image);

        selfView = findViewById(R.id.view_self_round);
        roundImageView = findViewById(R.id.img_round_icon);

        findViewById(R.id.but_change_canvas).setOnClickListener(v -> {
            selfView.setActionID(selfView.getActionID() + 1);
            selfView.invalidate();
        });

        findViewById(R.id.but_change_xfermode).setOnClickListener(v -> {
            roundImageView.setDrawMode(roundImageView.getDrawMode() + 1);
            roundImageView.invalidate();
        });
    }
}
