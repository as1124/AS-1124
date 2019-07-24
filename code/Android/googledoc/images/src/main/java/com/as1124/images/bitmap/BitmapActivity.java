package com.as1124.images.bitmap;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.as1124.images.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.WeakReference;

/**
 * 演示通过{@link BitmapFactory}和Glide加载网络图、应用内资源图的区别以及效率对比
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class BitmapActivity extends Activity {

    private static Bitmap bitmap = null;

    private static WeakReference<BitmapActivity> activityRef;

    private static Handler loadHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
                    BitmapActivity activity = activityRef.get();
                    if (activity != null && bitmap != null) {
                        activity.imageView.setImageBitmap(bitmap);
                        activity.imageView.invalidate();
                    }
                    break;
            }
        }
    };

    private AsyncTask<Void, Integer, Void> loadTask = new AsyncTask<Void, Integer, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            BitmapActivity activity = activityRef.get();
            if (activity == null) {
                return null;
            }
            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
//            decodeOptions.inSampleSize = 1;
//            decodeOptions.inTargetDensity = DisplayMetrics.DENSITY_MEDIUM;
            decodeOptions.inJustDecodeBounds = true;
            decodeOptions.inScaled = true;
            try {
                BitmapActivity.bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.test_glide, decodeOptions);

                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inSampleSize = decodeOptions.outWidth / getResources().getDisplayMetrics().widthPixels;
                BitmapActivity.bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.test_glide, options2);
                Message msg = loadHandler.obtainMessage(111);
                loadHandler.sendMessage(msg);
            } catch (Exception e) {
                Log.e(BitmapActivity.class.getSimpleName(), e.getMessage());
            }
            return null;
        }
    };

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        activityRef = new WeakReference<>(this);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        imageView = findViewById(R.id.img_bitmap);

        findViewById(R.id.but_use_bitmap).setOnClickListener(v -> showByBitmap());
        findViewById(R.id.but_use_glide).setOnClickListener(v -> showByGlide());
    }

    /**
     * 用普通的BitmapFactory提供的API显示图片
     */
    private void showByBitmap() {
        loadTask.execute();

        // 出现OOM问题
//        imageView.setImageDrawable(getResources().getDrawable(R.drawable.test_glide));
    }

    /**
     * 用Glide提供的API进行图像处理
     */
    private void showByGlide() {
//        Glide.with(imageView).load(imgUrl).into(imageView);

//        // 不设置Options直接加载会出现了OOM问题, 应用内的JPG图大小有1M多
        Glide.with(this).load(getResources().getDrawable(R.drawable.test_glide)).thumbnail(0.3f).into(imageView);

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions = requestOptions.placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.add_btn)
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(imageView).load(R.drawable.test_glide).apply(requestOptions).into(imageView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }

    }
}
