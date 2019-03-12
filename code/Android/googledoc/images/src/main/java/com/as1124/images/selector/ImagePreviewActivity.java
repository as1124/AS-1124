package com.as1124.images.selector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.as1124.images.R;
import com.as1124.images.selector.transformer.ScaleTransformer;
import com.as1124.selflib.WindowUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 图片预览界面，类似 android.widget.Gallery
 * <ol>
 * <li>{@link ViewPager#setAdapter(PagerAdapter)} 设置数据提供器</li>
 * <li>{@link ViewPager#setOffscreenPageLimit(int)} 设置默认预加载的滑动页数量</li>
 * <li>{@link ViewPager#setCurrentItem(int)} 设置第一个展示页在内容提供器中的index位置</li>
 * <li>{@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)} 设置切换特效</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ImagePreviewActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String KEY_FOR_ALL_ITEM = "all_items";
    public static final String KEY_FOR_SELECTED_ITEM = "selected_items";
    public static final String KEY_FOR_SHOW_INDEX = "show_index";
    public static final String KEY_RESULT = "preview_result";
    public static final int RESULT_FINISH_ALL = -7;
    public static final int REQUEST_CODE = 2233;

    private static int maxCount = 3;
    private static boolean isOriginal = false;
    private List<String> mAllPaths = new ArrayList<>();
    private Set<String> mCheckedPaths = new HashSet<>();

    private ViewPager mPager;
    private TextView positionIndicator;
    private TextView confirmBut;
    private CheckBox originalBut;
    private CheckBox selectCheck;


    class Abc extends ViewPager{

        public Abc(@NonNull Context context) {
            super(context);
        }

        @Override
        public void setOffscreenPageLimit(int limit) {
            super.setOffscreenPageLimit(limit);
        }

        @Override
        public int getOffscreenPageLimit() {
            return super.getOffscreenPageLimit();
        }
    }


    /**
     * @param from          提供Context的调用方
     * @param allItems      要展现的所有项
     * @param selectedItems 选中项
     * @param max           最大张数
     * @param isOriginal    是否原图
     * @param showIndex     第一张展现的图片在原数组中的位置
     */
    public static void startForResult(Activity from, ArrayList<String> allItems, Set<String> selectedItems,
                                      int max, boolean isOriginal, int showIndex) {
        Intent intent = new Intent(from, ImagePreviewActivity.class);
        intent.putStringArrayListExtra(KEY_FOR_ALL_ITEM, allItems);
        intent.putExtra(KEY_FOR_SELECTED_ITEM, selectedItems.toArray(new String[selectedItems.size()]));
        intent.putExtra(KEY_FOR_SHOW_INDEX, showIndex);
        ImagePreviewActivity.maxCount = max;
        ImagePreviewActivity.isOriginal = isOriginal;
        from.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        WindowUtils.fullScreen(this);

        int showIndex = 0;
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            this.mAllPaths = receivedIntent.getStringArrayListExtra(KEY_FOR_ALL_ITEM);
            String[] selected = receivedIntent.getStringArrayExtra(KEY_FOR_SELECTED_ITEM);
            mCheckedPaths.addAll(Arrays.asList(selected));
            showIndex = receivedIntent.getIntExtra(KEY_FOR_SHOW_INDEX, 0);
        }

        findViewById(R.id.but_image_back).setOnClickListener(this);
        positionIndicator = findViewById(R.id.textview_title);
        confirmBut = findViewById(R.id.but_finish_select);
        confirmBut.setOnClickListener(this);
        originalBut = findViewById(R.id.checkbox_is_original);
        originalBut.setOnClickListener(this);
        findViewById(R.id.textview_original).setOnClickListener(this);
        selectCheck = findViewById(R.id.checkbox_preview_select);
        selectCheck.setOnClickListener(this);

        mPager = findViewById(R.id.viewpager);
        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(this);
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(adapter);
        mPager.setPageTransformer(true, new ScaleTransformer());
        mPager.addOnPageChangeListener(this);
        mPager.setCurrentItem(showIndex);

        updateIndicator(showIndex);
        updateConfirmButton();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_image_back:
                // 保存选择结果返回概览界面
                Intent data = new Intent();
                data.putExtra(KEY_RESULT, getCheckedPaths().toArray(new String[]{}));
                setResult(RESULT_OK, data);
                this.finish();
                break;
            case R.id.but_finish_select:
                // 不反回，直接完成图片多选结果
                Intent data2 = new Intent();
                data2.putExtra(KEY_RESULT, getCheckedPaths().toArray(new String[]{}));
                setResult(RESULT_FINISH_ALL, data2);
                this.finish();
                break;
            case R.id.textview_original:
                originalBut.setChecked(!originalBut.isChecked());
                break;
            case R.id.checkbox_is_original:
                ImagePreviewActivity.isOriginal = originalBut.isChecked();
                break;
            case R.id.checkbox_preview_select:
                int currentPosition = mPager.getCurrentItem();
                String imgPath = getAllPaths().get(currentPosition);
                if (((CheckBox) v).isChecked()) {
                    if (getCheckedPaths().size() >= ImagePreviewActivity.maxCount) {
                        Toast.makeText(this,
                                MessageFormat.format("最多只能选择 {0} 张图片", maxCount), Toast.LENGTH_SHORT).show();
                        selectCheck.setChecked(false);
                    } else {
                        getCheckedPaths().add(imgPath);
                        updateConfirmButton();
                    }
                } else {
                    getCheckedPaths().remove(imgPath);
                    updateConfirmButton();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        Log.i("页面滑动......", v + "===" + i1);
    }

    @Override
    public void onPageSelected(int i) {
        updateIndicator(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(KEY_RESULT, getCheckedPaths().toArray(new String[]{}));
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }

    private void updateConfirmButton() {
        String string = getResources().getString(R.string.picture_selector_send);
        confirmBut.setText(MessageFormat.format(string, getCheckedPaths().size(), maxCount));
    }

    private void updateIndicator(int currentPosition) {
        positionIndicator.setText(MessageFormat.format("{0}/{1}", currentPosition + 1, getAllPaths().size()));
        String imgPath = getAllPaths().get(currentPosition);
        selectCheck.setChecked(getCheckedPaths().contains(imgPath));
    }

    public List<String> getAllPaths() {
        return mAllPaths;
    }

    public Set<String> getCheckedPaths() {
        return mCheckedPaths;
    }

}
