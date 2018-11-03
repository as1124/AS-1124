package com.as1124.images.selector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.as1124.images.R;

import java.util.List;

public class MyRecyclerListAdapter extends RecyclerView.Adapter<MyRecyclerListAdapter.MyViewHolder> {

    private List<String> mItems;

    private Context mContext;

    public MyRecyclerListAdapter(List<String> items, Context context) {
        this.mItems = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_divider, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (holder != null) {
            holder.itemView.setTag(Integer.toString(position));
            TextView textView = holder.itemView.findViewById(R.id.textview_list_item);
            textView.setText(mItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void removeData(int position) {
        mItems.remove(position);
        notifyItemChanged(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
