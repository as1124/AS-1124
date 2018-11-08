package com.as1124.images.selector.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * GridView切换效果，缩放切入切出
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ScaleTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < -1) {
            // 由左侧滑出屏幕
            view.setScaleX(0.7f);
            view.setScaleY(0.7f);
        } else if (position <= 0) {
            // 在往左滑
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleY(1);
            view.setScaleY(1);
        } else if (position <= 1) {
            // 在往右滑
            view.setAlpha(1.0f - position);
            view.setTranslationX(-view.getWidth() * position);
            float scale = 0.7f + 0.3f * (1.0f - position);
            view.setScaleY(scale);
            view.setScaleY(scale);
        } else {
            // 滑出右侧屏幕
            view.setScaleY(0.7f);
            view.setScaleY(0.7f);
        }
    }
}
