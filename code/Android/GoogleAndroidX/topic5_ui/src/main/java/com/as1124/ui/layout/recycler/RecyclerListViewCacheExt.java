package com.as1124.ui.layout.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerListViewCacheExt extends RecyclerView.ViewCacheExtension {

    @Nullable
    @Override
    public View getViewForPositionAndType(@NonNull RecyclerView.Recycler recycler, int position, int type) {
        return null;
    }
}
