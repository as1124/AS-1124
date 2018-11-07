package com.as1124.images.selector;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.as1124.images.R;
import com.as1124.images.utils.WindowUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 图片多选Activity
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ImageSelectorActivity extends Activity implements View.OnClickListener {

    public static final String KEY_FOR_ORIGINAL = "is_original";
    public static final String KEY_FOR_PREVIEW = "can_preview";
    public static final String KEY_FOR_NUMBER = "max_count";
    public static final String KEY_RESULT = "image_paths";

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

    private CheckBox mIsOriginalBut;
    private TextView mFinishSelectBut;
    private TextView mPreviewBut;

    /**
     * @param from        提供Context的调用方
     * @param requestCode 请求码
     * @param canPreview  是否可以预览
     * @param isOriginal  是否原图
     * @param number      最大选择张数
     */
    public static void startForResult(Activity from, int requestCode, boolean canPreview, boolean isOriginal, int number) {
        Intent intent = new Intent(from, ImageSelectorActivity.class);
        intent.putExtra(KEY_FOR_ORIGINAL, isOriginal);
        intent.putExtra(KEY_FOR_PREVIEW, canPreview);
        intent.putExtra(KEY_FOR_NUMBER, number);
        from.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.hide();
        }
        WindowUtils.fullScreen(this);

        Intent postedIntent = getIntent();
        if (postedIntent != null) {
            isOriginal = postedIntent.getBooleanExtra(KEY_FOR_ORIGINAL, false);
            canPreview = postedIntent.getBooleanExtra(KEY_FOR_PREVIEW, false);
            maxCount = postedIntent.getIntExtra(KEY_FOR_NUMBER, 3);
        }
        GridView mGridView = findViewById(R.id.images_grid);
        queryAlbums(this);
        ListAdapter adapter = new ImageGridViewAdapter(this, R.layout.item_picture_selector, mAllImages);
        mGridView.setAdapter(adapter);

        findViewById(R.id.but_image_back).setOnClickListener(this);
        mFinishSelectBut = findViewById(R.id.but_finish_select);
        mFinishSelectBut.setOnClickListener(this);
        findViewById(R.id.panel_folder).setOnClickListener(this);
        mIsOriginalBut = findViewById(R.id.checkbox_is_original);
        mIsOriginalBut.setChecked(isOriginal);
        mIsOriginalBut.setOnClickListener(this);
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
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DESCRIPTION};
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                int dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                int bucketNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                int size = cursor.getCount();
                for (int i = 0; i < size; i++) {
                    cursor.moveToPosition(i);
                    long id = cursor.getLong(idIndex);
                    String path = cursor.getString(dataIndex);
                    // 文件夹名称
                    String bucketName = cursor.getString(bucketNameIndex);
                    Log.i("IMAGE-INFO", id + " = " + path + ", " + bucketName);
                    if (!mAllImages.contains(path)) {
                        mAllImages.add(path);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(RESULT_CANCELED);
        this.finish();
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
            case R.id.checkbox_is_original:
                this.isOriginal = mIsOriginalBut.isChecked();
                break;
            case R.id.but_to_preview:
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
            ImagePreviewActivity.startForResult(this, 2233, this.mAllImages,
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
        Integer position = (Integer) ((View) itemCheckbox.getParent()).getTag();
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
