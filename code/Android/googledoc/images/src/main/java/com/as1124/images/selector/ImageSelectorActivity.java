package com.as1124.images.selector;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.as1124.images.R;
import com.as1124.selflib.MediaUtils;
import com.as1124.selflib.WindowUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 图片多选Activity, 包含了两种图片资源查询方式
 * <ol>
 * <li>通过基础的 {@link android.content.ContentResolver#query(Uri, String[], Bundle, CancellationSignal)},
 * 功能完整,查询完成后释放{@link Cursor}, 系统图片资源库发生变化时当前APP不会收到通知</li>
 * <li>通过 {@link Loader}实现，可以实现异步查询，查询完成后资源库变化能接收通知</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ImageSelectorActivity extends Activity implements View.OnClickListener {

    public static final String KEY_FOR_ORIGINAL = "is_original";
    public static final String KEY_FOR_PREVIEW = "can_preview";
    public static final String KEY_FOR_NUMBER = "max_count";
    public static final String KEY_RESULT = "image_paths";
    public static final int REQUEST_CODE = 1122;

    /**
     * 是否原图
     */
    private boolean isOriginal = false;

    /**
     * 是否可预览大图
     */
    private boolean canPreview = false;

    /**
     * 最大可选图片数量
     */
    private int maxCount = 3;
    private ArrayList<String> mAllImages = new ArrayList<>();
    private Set<String> mSelectedImages = new HashSet<>();

    private GridView mGridView;
    private CheckBox mIsOriginalBut;
    private TextView mFinishSelectBut;
    private TextView mPreviewBut;

    /**
     * @param from       提供Context的调用方
     * @param canPreview 是否可以预览
     * @param isOriginal 是否原图
     * @param number     最大选择张数
     */
    public static void startForResult(Activity from, boolean canPreview, boolean isOriginal, int number) {
        Intent intent = new Intent(from, ImageSelectorActivity.class);
        intent.putExtra(KEY_FOR_ORIGINAL, isOriginal);
        intent.putExtra(KEY_FOR_PREVIEW, canPreview);
        intent.putExtra(KEY_FOR_NUMBER, number);
        from.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.hide();
        }
        WindowUtils.fullScreen(this, Color.TRANSPARENT, true);

        Intent postedIntent = getIntent();
        if (postedIntent != null) {
            isOriginal = postedIntent.getBooleanExtra(KEY_FOR_ORIGINAL, false);
            canPreview = postedIntent.getBooleanExtra(KEY_FOR_PREVIEW, false);
            maxCount = postedIntent.getIntExtra(KEY_FOR_NUMBER, 3);
        }
        mGridView = findViewById(R.id.images_grid);
        {
            // 通过正常ContentResolver实现的图片结果查询
//            queryAlbums(this);
//            ListAdapter adapter = new ImageGridViewAdapter(this, R.layout.item_picture_selector, mAllImages);
//            mGridView.setAdapter(adapter);
        }
        {
            // 通过Loader实现的图片结果查询
            ImageGridViewAdapterWithLoader adapter = new ImageGridViewAdapterWithLoader(this, R.layout.item_picture_selector);
            getLoaderManager().initLoader(ImageGridViewAdapterWithLoader.LOADER_ID, null, adapter);
            mGridView.setAdapter(adapter);
        }


        findViewById(R.id.but_image_back).setOnClickListener(this);
        mFinishSelectBut = findViewById(R.id.but_finish_select);
        mFinishSelectBut.setOnClickListener(this);
        findViewById(R.id.panel_folder).setOnClickListener(this);
        mIsOriginalBut = findViewById(R.id.checkbox_is_original);
        mIsOriginalBut.setChecked(isOriginal);
        mIsOriginalBut.setOnClickListener(this);
        findViewById(R.id.textview_original).setOnClickListener(this);
        mPreviewBut = findViewById(R.id.but_to_preview);
        if (isCanPreview()) {
            mPreviewBut.setOnClickListener(this);
        } else {
            findViewById(R.id.but_to_preview).setVisibility(View.INVISIBLE);
        }

        updateInfoButtons();

//        showRecyclerTest();
    }

    /**
     * 查询文件存储存储中的图片
     *
     * @param context 上下文
     */
    public void queryAlbums(Context context) {
        this.mAllImages.addAll(MediaUtils.queryImages(context));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePreviewActivity.REQUEST_CODE) {
            String[] paths = data.getStringArrayExtra(ImagePreviewActivity.KEY_RESULT);
            if (resultCode == RESULT_OK) {
                this.mSelectedImages.clear();
                for (String path : paths) {
                    this.mSelectedImages.add(path);
                    int position = mAllImages.indexOf(path);
                    View itemView = mGridView.getChildAt(position);
                    CheckBox itemCheckbox = itemView.findViewById(R.id.checkbox_selector_item);
                    itemCheckbox.setChecked(true);
                }
                updateInfoButtons();
            } else if (resultCode == ImagePreviewActivity.RESULT_FINISH_ALL) {
                Intent retIntent = new Intent();
                retIntent.putExtra(KEY_RESULT, paths);
                setResult(RESULT_OK, retIntent);
                this.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(ImageGridViewAdapterWithLoader.LOADER_ID);
    }

    @Override
    public void onBackPressed() {
        this.setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    /**
     * 演示带Item分割线的{@linkplain RecyclerView}
     */
    public void showRecyclerTest() {
        setContentView(R.layout.activity_recyclerview_divider);
        String[] items = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        RecyclerView recyclerList = findViewById(R.id.recycler_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        MyRecyclerListAdapter adapter = new MyRecyclerListAdapter(Arrays.asList(items), this);
        recyclerList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.image_selector_item:
                handleImageClicked((ImageView) v);
                break;
            case R.id.div_item_check:
                CheckBox itemCheck = v.findViewById(R.id.checkbox_selector_item);
                itemCheck.setChecked(!itemCheck.isChecked());
                handleCheckboxClicked(itemCheck);
                updateInfoButtons();
                break;
            case R.id.checkbox_selector_item:
                handleCheckboxClicked((CheckBox) v);
                updateInfoButtons();
                break;
            case R.id.but_image_back:
                // 取消, 返回上一界面
                this.setResult(RESULT_CANCELED);
                this.finish();
                break;
            case R.id.but_finish_select:
                // 确认选择结果
                Intent data = new Intent();
                data.putExtra(KEY_RESULT, getSelectedImages().toArray(new String[]{}));
                setResult(RESULT_OK, data);
                this.finish();
                break;
            case R.id.panel_folder:
                //ATTENTION 切换folder功能
                break;
            case R.id.textview_original:
                mIsOriginalBut.setChecked(!mIsOriginalBut.isChecked());
                break;
            case R.id.checkbox_is_original:
                this.isOriginal = mIsOriginalBut.isChecked();
                break;
            case R.id.but_to_preview:
                // ATTENTION 只预览选中的图
                break;
            default:
                break;
        }
    }

    /**
     * GridView中Item上的图片被点击时
     *
     * @param itemImage Item-View on GridView
     */
    private void handleImageClicked(ImageView itemImage) {
        View parentView = (View) itemImage.getParent();
        Integer position = (Integer) parentView.getTag();
        if (isCanPreview()) {
            ImagePreviewActivity.startForResult(this, this.mAllImages,
                    this.mSelectedImages, this.maxCount, this.isOriginal, position);
        } else {
            // 不可预览的时候点击图片认为是选中操作
            CheckBox itemCheck = parentView.findViewById(R.id.checkbox_selector_item);
            String imgPath = getAllImages().get(position);
            if (mSelectedImages.contains(imgPath)) {
                mSelectedImages.remove(imgPath);
                itemCheck.setChecked(false);
            } else {
                if (checkMaxCount()) {
                    mSelectedImages.add(imgPath);
                    itemCheck.setChecked(true);
                } else {
                    itemCheck.setChecked(false);
                }
            }
            updateInfoButtons();
        }
    }

    /**
     * GridView中Item上的Checkbox被点击时
     *
     * @param itemCheckbox Item-View on GridView
     */
    private void handleCheckboxClicked(CheckBox itemCheckbox) {
        Integer position = (Integer) itemCheckbox.getTag();
        String imgPath = getAllImages().get(position);
        if (itemCheckbox.isChecked()) {
            if (checkMaxCount()) {
                mSelectedImages.add(imgPath);
            } else {
                itemCheckbox.setChecked(false);
            }
        } else {
            mSelectedImages.remove(imgPath);
        }
    }

    private boolean checkMaxCount() {
        if (getSelectedImages().size() >= maxCount) {
            Toast.makeText(this,
                    MessageFormat.format("最多只能选择 {0} 张图片", getMaxCount()), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void updateInfoButtons() {
        String str = getResources().getString(R.string.picture_selector_send);
        mFinishSelectBut.setText(MessageFormat.format(str, getSelectedImages().size(), maxCount));
        if (isCanPreview()) {
            str = getResources().getString(R.string.picture_selector_preview);
            mPreviewBut.setText(MessageFormat.format(str, getSelectedImages().size()));
        }
    }

    public boolean isCanPreview() {
        return canPreview;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public List<String> getAllImages() {
        return mAllImages;
    }

    public Set<String> getSelectedImages() {
        return mSelectedImages;
    }
}
