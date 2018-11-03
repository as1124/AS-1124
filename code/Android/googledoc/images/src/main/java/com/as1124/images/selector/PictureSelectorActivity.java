package com.as1124.images.selector;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.as1124.images.R;

import java.util.Arrays;

public class PictureSelectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.hide();
        }

//        showRecyclerTest();
    }

    public void queryAlbums(Context context) {
    }

    /**
     * 演示带Item分割线的{@linkplain RecyclerView}
     */
    private void showRecyclerTest() {
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
}
