package com.as1124.images.selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.as1124.images.R;
import com.as1124.images.utils.WindowUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 图片预览界面，类似{@link android.widget.Gallery}
 * <ol>
 * <li>{@link ViewPager#setAdapter(PagerAdapter)} 设置数据提供器</li>
 * <li>{@link ViewPager#setOffscreenPageLimit(int)} 设置默认预加载的滑动页数量</li>
 * <li>{@link ViewPager#setCurrentItem(int)} 设置第一个展示页在内容提供器中的index位置</li>
 * <li>{@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)} 设置切换特效</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ImagePreviewActivity extends Activity implements ViewPager.OnPageChangeListener {

    public static final String KEY_FOR_ORIGINAL = "is_original";
    public static final String KEY_FOR_NUMBER = "max_count";
    public static final String KEY_FOR_ALL_ITEM = "all_items";
    public static final String KEY_FOR_SELECTED_ITEM = "selected_items";
    public static final String KEY_FOR_SHOW_INDEX = "show_index";
    public static final String KEY_RESULT = "preview_result";

    private int maxCount = 3;
    private boolean isOriginal = false;
    private List<String> mAllPaths = new ArrayList<>();
    private Set<String> mCheckedPaths = new HashSet<>();

    private ViewPager mPager;

    /**
     * @param from          提供Context的调用方
     * @param requestCode   请求码
     * @param allItems      要展现的所有项
     * @param selectedItems 选中项
     * @param max           最大张数
     * @param isOriginal    是否原图
     * @param showIndex     第一张展现的图片在原数组中的位置
     */
    public static void startForResult(Activity from, int requestCode, ArrayList<String> allItems,
                                      Set<String> selectedItems, int max, boolean isOriginal, int showIndex) {
        Intent intent = new Intent(from, ImagePreviewActivity.class);
        intent.putStringArrayListExtra(KEY_FOR_ALL_ITEM, allItems);
        intent.putExtra(KEY_FOR_SELECTED_ITEM, selectedItems.toArray(new String[selectedItems.size()]));
        intent.putExtra(KEY_FOR_NUMBER, max);
        intent.putExtra(KEY_FOR_ORIGINAL, isOriginal);
        intent.putExtra(KEY_FOR_SHOW_INDEX, showIndex);
        from.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        WindowUtils.fullScreen(this);

        int showIndex = 0;
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            this.maxCount = receivedIntent.getIntExtra(KEY_FOR_NUMBER, 3);
            this.isOriginal = receivedIntent.getBooleanExtra(KEY_FOR_ORIGINAL, false);
            this.mAllPaths = receivedIntent.getStringArrayListExtra(KEY_FOR_ALL_ITEM);
            String[] selected = receivedIntent.getStringArrayExtra(KEY_FOR_SELECTED_ITEM);
            mCheckedPaths.addAll(Arrays.asList(selected));
            showIndex = receivedIntent.getIntExtra(KEY_FOR_SHOW_INDEX, 0);
        }

        mPager = findViewById(R.id.viewpager);
        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(this);
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(this);
        mPager.setCurrentItem(showIndex);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    public List<String> getAllPaths() {
        return mAllPaths;
    }

    public Set<String> getCheckedPaths() {
        return mCheckedPaths;
    }

}
