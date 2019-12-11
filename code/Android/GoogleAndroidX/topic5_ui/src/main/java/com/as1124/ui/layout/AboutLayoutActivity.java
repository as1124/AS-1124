package com.as1124.ui.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.ui.R;
import com.as1124.ui.layout.card.AboutCardviewActivity;
import com.as1124.ui.layout.recycler.AboutRecyclerView;
import com.as1124.ui.layout.recycler.tb.TBRecyclerListActivity;

public class AboutLayoutActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_layout);

        findViewById(R.id.btn_recycler_view).setOnClickListener(v -> startActivity(new Intent(this, AboutRecyclerView.class)));
        findViewById(R.id.btn_tb_recycler).setOnClickListener(v -> startActivity(new Intent(this, TBRecyclerListActivity.class)));
        findViewById(R.id.btn_card_view).setOnClickListener(v -> startActivity(new Intent(this, AboutCardviewActivity.class)));

    }


}
