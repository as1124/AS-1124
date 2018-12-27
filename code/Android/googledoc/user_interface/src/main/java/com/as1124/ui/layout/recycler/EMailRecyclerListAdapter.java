package com.as1124.ui.layout.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.as1124.ui.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 数据处理Adapter
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class EMailRecyclerListAdapter extends RecyclerView.Adapter<EMailRecyclerListAdapter.EMailItemHolder> {

    private List<EMailModel> itemArray = null;

    public EMailRecyclerListAdapter(List<EMailModel> itemArray) {
        this.itemArray = itemArray;
    }

    @NonNull
    @Override
    public EMailItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_email, viewGroup, false);
        return new EMailItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EMailItemHolder viewHolder, int i) {
        EMailModel modelSource = itemArray.get(i);

        int resourceID = 0;
        switch (i % 7) {
            case 0:
                resourceID = R.drawable.x1;
                break;
            case 1:
                resourceID = R.drawable.x2;
                break;
            case 2:
                resourceID = R.drawable.x3;
                break;
            case 3:
                resourceID = R.drawable.x4;
                break;
            case 4:
                resourceID = R.drawable.x5;
                break;
            case 5:
                resourceID = R.drawable.x6;
                break;
            case 6:
            default:
                resourceID = R.drawable.x7;
                break;
        }
        Glide.with(viewHolder.itemView.getContext()).load(resourceID).into(viewHolder.contactIcon);
        viewHolder.senderText.setText(modelSource.getFrom());
        viewHolder.topicText.setText(modelSource.getTopic());
        viewHolder.summaryText.setText(modelSource.getSummary());
        viewHolder.dateText.setText(modelSource.getReceiveDate());
    }

    @Override
    public int getItemCount() {
        return itemArray.size();
    }

    class EMailItemHolder extends RecyclerView.ViewHolder {

        protected ImageView contactIcon;
        protected TextView senderText;
        protected TextView topicText;
        protected TextView summaryText;
        protected TextView dateText;

        public EMailItemHolder(@NonNull View itemView) {
            super(itemView);

            contactIcon = itemView.findViewById(R.id.image_email_contact);
            senderText = itemView.findViewById(R.id.text_email_sender);
            topicText = itemView.findViewById(R.id.text_email_topic);
            summaryText = itemView.findViewById(R.id.text_email_summary);
            dateText = itemView.findViewById(R.id.text_email_date);
        }
    }
}
