package com.as1124.images.selector;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.as1124.images.R;
import com.as1124.selflib.MediaUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * 通过{@link Loader}实现的手机图片多选的Adapter, Loader会自动监听数据源（本例为图片资源库），
 * 当数据发生变动时会触发重新加载<br/>
 * 不过没有测试删除会发生什么...
 *
 * @author as-1124(as1124huang@gmail.com)
 */
public class ImageGridViewAdapterWithLoader extends SimpleCursorAdapter implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 1;

    private WeakReference<ImageSelectorActivity> activityReference;
    private RequestOptions requestOptions;

    public ImageGridViewAdapterWithLoader(@NonNull ImageSelectorActivity context, int resource) {
        // 如果参数中的 to:int[] 为null则需要重写bindView方法，否则空指针
        super(context, resource, null, MediaUtils.IMAGE_PROJECTION,
                new int[]{R.id.image_selector_item}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setViewBinder((view, cursor, columnIndex) -> {
            // 这里的columnIndex 和构造函数中传递的 from的数组顺序对应
            ImageView imgView = (ImageView) view;
            String path = cursor.getString(columnIndex);
            Glide.with(view).load(new File(path)).apply(requestOptions).into(imgView);
            return true;
        });
        activityReference = new WeakReference<>(context);
        requestOptions = new RequestOptions().placeholder(R.drawable.mis_default_error)
                .error(R.drawable.mis_default_error)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
        // This is called when a new Loader needs to be created. If just one loader,
        // you don't need to care about the loaderID
        // 在这里绑定Loader, 如果存在同id的loader, 该方法不会调用
        if (LOADER_ID == loaderID) {
            return new CursorLoader(activityReference.get(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaUtils.IMAGE_PROJECTION, null, null, null);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // 将在先前创建的加载器重置且其数据因此不可用时调用swap
        // 可以在这里释放数据
        Log.i(getClass().getSimpleName(), "CursorLoader reset!!");
        swapCursor(null);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // 和普通ArrayAdapter, 这里不用去重写getView方法; CursorAdapter将View创建过程拆成两端
        // 以下摘自源码
//        View v;
//        if (convertView == null) {
//            v = newView(mContext, mCursor, parent);
//        } else {
//            v = convertView;
//        }
//        bindView(v, mContext, mCursor);

        View itemView = super.newView(context, cursor, parent);
        int position = cursor.getPosition();
        View.OnClickListener clickListener = activityReference.get();

        itemView.setTag(position);
        ImageView itemPic = itemView.findViewById(R.id.image_selector_item);
        itemPic.setOnClickListener(clickListener);
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        Glide.with(context).load(new File(path)).apply(requestOptions).into(itemPic);
        CheckBox itemCheck = itemView.findViewById(R.id.checkbox_selector_item);
        itemCheck.setTag(position);
        itemCheck.setOnClickListener(clickListener);
        itemView.findViewById(R.id.div_item_check).setOnClickListener(clickListener);
        return itemView;
    }

}
