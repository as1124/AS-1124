package com.as1124.app_manifest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.as1124.app_manifest.views.SelfLinearLayout;

/**
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class AnotherActivity extends Activity implements View.OnScrollChangeListener {


    private SelfLinearLayout allContainer;

    private RelativeLayout refreshArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_scrollable1);

        allContainer = findViewById(R.id.layout_self_linear);
        allContainer.setOnScrollChangeListener(this);
        refreshArea = findViewById(R.id.layout_refresh_area);


    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.i("Scroll_View", "scrollY==" + scrollY + ",  oldScrollY==" + oldScrollY);
    }
}
