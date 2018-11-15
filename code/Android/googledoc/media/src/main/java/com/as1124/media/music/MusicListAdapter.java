package com.as1124.media.music;

import android.media.MediaDescription;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.as1124.media.R;

import java.lang.ref.WeakReference;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {

    private static final int ITEM_TYPE_SONG_ITEM = 0;
    private static final int ITEM_TYPE_ALBUM_INFO = 1;
    private static final int ITEM_TYPE_SEARCH_ITEM = 2;
    private static final int ITEM_TYPE_ALBUM_ACTION = 3;

    private WeakReference<MusicPlayerActivity> activityWeakReference;

    public MusicListAdapter(MusicPlayerActivity context) {
        super();
        this.activityWeakReference = new WeakReference<>(context);
    }

    @Override
    public int getItemViewType(int position) {
//        switch (position) {
//            case 0:
//                return ITEM_TYPE_ALBUM_INFO;
//            case 1:
//                return ITEM_TYPE_SEARCH_ITEM;
//            case 2:
//                return ITEM_TYPE_ALBUM_ACTION;
//            case 3:
//                return ITEM_TYPE_SONG_ITEM;
//            default:
//                break;
//        }

        switch (position) {
            case 0:
                return ITEM_TYPE_SEARCH_ITEM;
            case 1:
                return ITEM_TYPE_ALBUM_ACTION;
            case 2:
                return ITEM_TYPE_SONG_ITEM;
            default:
                break;
        }

        return ITEM_TYPE_SONG_ITEM;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (ITEM_TYPE_ALBUM_INFO == viewType) {
            // 歌单信息栏
            itemView = LayoutInflater.from(activityWeakReference.get())
                    .inflate(R.layout.item_album_info, viewGroup, false);
        } else if (ITEM_TYPE_SEARCH_ITEM == viewType) {
            // 搜索栏
            itemView = LayoutInflater.from(activityWeakReference.get())
                    .inflate(R.layout.item_album_search, viewGroup, false);
        } else if (ITEM_TYPE_ALBUM_ACTION == viewType) {
            // 歌单列表管理栏
            itemView = LayoutInflater.from(activityWeakReference.get())
                    .inflate(R.layout.item_play_all, viewGroup, false);
        } else {
            // 歌曲信息
            itemView = LayoutInflater.from(activityWeakReference.get())
                    .inflate(R.layout.item_song_info, viewGroup, false);
        }
        return new MusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder musicViewHolder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_SONG_ITEM) {
            int songIndex = position - 2;
            MediaDescription songInfo = activityWeakReference.get().getSongs().get(songIndex).getDescription();
            String songID = songInfo.getMediaId();
            musicViewHolder.itemView.setTag(songID);

            // 一定要转成String值，否则是要去读取resID对应的String而导致异常
            musicViewHolder.mIndexText.setText(Integer.toString(songIndex + 1));
            musicViewHolder.mNameText.setText(songInfo.getTitle());
            musicViewHolder.mArtistText.setText(songInfo.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        int size = activityWeakReference.get().getSongs().size();
        return size + 2;// 算上歌单信息、搜索栏、歌单列表管理栏
    }

    class MusicViewHolder extends RecyclerView.ViewHolder {

        private TextView mIndexText;
        private TextView mNameText;
        private TextView mArtistText;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            mIndexText = itemView.findViewById(R.id.text_song_index);
            mNameText = itemView.findViewById(R.id.text_song_name);
            mArtistText = itemView.findViewById(R.id.text_song_singer);
        }
    }
}
