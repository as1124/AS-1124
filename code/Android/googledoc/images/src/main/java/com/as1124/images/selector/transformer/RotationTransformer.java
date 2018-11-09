package com.as1124.images.selector.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * GridView页面切换的旋转特效
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class RotationTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < -1) {
            view.setPivotX(view.getWidth() * 0.5f);
            view.setPivotX(view.getHeight());
            view.setRotation(-20.0f);
        } else if (position <= 1) {
            view.setPivotX(view.getWidth() * 0.5f);
            view.setPivotX(view.getHeight());
            view.setRotation(20.0f * position);
        } else {
            view.setPivotX(view.getWidth() * 0.5f);
            view.setPivotX(view.getHeight());
            view.setRotation(20.0f);
        }
    }
}
