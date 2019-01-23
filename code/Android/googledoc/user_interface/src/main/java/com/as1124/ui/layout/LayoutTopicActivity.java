package com.as1124.ui.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.ui.R;
import com.as1124.ui.layout.cardview.CardViewActivity;
import com.as1124.ui.layout.recycler.RecyclerViewActivity;

/**
 * 布局管理器
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class LayoutTopicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_topic);

        findViewById(R.id.but_to_recyclerview).setOnClickListener(v ->
                startActivity(new Intent(LayoutTopicActivity.this, RecyclerViewActivity.class)));
        findViewById(R.id.but_to_cardview).setOnClickListener(v ->
                startActivity(new Intent(LayoutTopicActivity.this, CardViewActivity.class)));
    }
}
