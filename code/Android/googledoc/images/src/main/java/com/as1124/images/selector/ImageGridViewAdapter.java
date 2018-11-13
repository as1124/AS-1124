package com.as1124.images.selector;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.as1124.images.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 图片多选ItemAdapter
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ImageGridViewAdapter extends ArrayAdapter<String> {

    private int layout;

    private WeakReference<ImageSelectorActivity> activityReference;

    private RequestOptions requestOptions;

    public ImageGridViewAdapter(@NonNull ImageSelectorActivity context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        layout = resource;
        activityReference = new WeakReference<>(context);
        requestOptions = new RequestOptions().placeholder(R.drawable.mis_default_error)
                .error(R.drawable.mis_default_error)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        if (convertView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        } else {
            itemView = convertView;
        }
        itemView.setTag(position);
        ImageView itemPic = itemView.findViewById(R.id.image_selector_item);
        itemPic.setOnClickListener(activityReference.get());
        String path = getItem(position);
        Glide.with(getContext()).load(new File(path)).apply(requestOptions).into(itemPic);
        CheckBox itemCheck = itemView.findViewById(R.id.checkbox_selector_item);
        itemCheck.setTag(position);
        itemCheck.setOnClickListener(activityReference.get());
        itemView.findViewById(R.id.div_item_check).setOnClickListener(activityReference.get());

        return itemView;
    }

}
