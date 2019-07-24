package com.as1124.ui.layout.cardview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.SeekBar;

import com.as1124.selflib.WindowUtils;
import com.as1124.ui.R;

/**
 * 学习使用Android v7 Support库中的{@link android.support.v7.widget.CardView}
 * <ul>
 * <li>修改CardView的圆角大小</li>
 * <li>修改CardView的阴影（投影）面积大小(实际是修改了Z轴高度)</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class CardViewActivity extends Activity {

    private static final String TAG = CardViewActivity.class.getSimpleName();

    private CardView mCardView;
    private SeekBar mRadiusSeekBar;
    private SeekBar mElevationSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        WindowUtils.fullScreen(this, Color.TRANSPARENT, true);
        WindowUtils.lightStatusBar(this);

        mCardView = findViewById(R.id.card_view);
        mRadiusSeekBar = findViewById(R.id.seekbar_changer_radius);
        // 修改CardView的圆角大小
        mRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, String.format("SeekBar Radius progress : %d", progress));
                mCardView.setRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mElevationSeekBar = findViewById(R.id.seekbar_changer_elevation);
        // 修改CardView的投影大小（实际是修改了Z轴高度）
        mElevationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, String.format("SeekBar Elevation progress : %d", progress));
                mCardView.setElevation(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

}
