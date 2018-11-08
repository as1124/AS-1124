package com.as1124.images.selector;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.as1124.images.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * 图片多选预览界面{@linkplain android.support.v4.view.ViewPager} 控件的Adapter处理类
 * <ul>
 * <li>注意{@link android.support.v4.view.ViewPager#addView(View, int)}出现数组越界问题</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private WeakReference<ImagePreviewActivity> contextReference;

    private RequestOptions requestOptions;

    protected ImageViewPagerAdapter(ImagePreviewActivity context) {
        contextReference = new WeakReference<>(context);

        requestOptions = new RequestOptions().placeholder(R.drawable.mis_default_error)
                .error(R.drawable.mis_default_error)
                .fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public int getCount() {
        return contextReference.get().getAllPaths().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // 用来判断是否需要重绘
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(contextReference.get());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        File imageFile = new File(contextReference.get().getAllPaths().get(position));
        Glide.with(container).load(imageFile).apply(requestOptions).into(imageView);
        // 不要调用 container.addView(imageView, position) 去处理起始位置不为0的情况，这样会产生
        // IndexOutOfBounds异常，ViewPager会自己去处理ItemInfo和View数组的映射关系的
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
