package as1124.com.helloworld.ch3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import as1124.com.helloworld.R;
import as1124.com.helloworld.ch3.list.ExpandableRecycleList;
import as1124.com.helloworld.ch3.list.TestRecyclerListView;
import as1124.com.helloworld.ch3.list.TestWechatActivity;

/**
 * android布局管理器学习
 *
 * @author huangjw(as1124huang @ primeton.com)
 */
public class LayoutTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3_main);

        findViewById(R.id.ch3_test_linear).setOnClickListener(v -> {
            Intent intent = new Intent(LayoutTestActivity.this, TestLinearLayout.class);
            startActivity(intent);
        });
        findViewById(R.id.ch3_test_relative).setOnClickListener(v -> {
            Intent intent = new Intent(LayoutTestActivity.this, TestRelativeLayout.class);
            startActivity(intent);
        });
        findViewById(R.id.ch3_test_titleimport).setOnClickListener(v -> {
            Intent intent = new Intent(LayoutTestActivity.this, TestTitleImport.class);
            startActivity(intent);
        });
        findViewById(R.id.ch3_test_basic_listview).setOnClickListener(v -> {
            Intent intent = new Intent(LayoutTestActivity.this, TestListview.class);
            startActivity(intent);
        });
        findViewById(R.id.ch3_test_recyclerlist_but).setOnClickListener(v -> {
            Intent intent = new Intent(LayoutTestActivity.this, TestRecyclerListView.class);
            startActivity(intent);
        });
        findViewById(R.id.ch3_test_pubulist_but).setOnClickListener(v -> {
            Intent intent = new Intent(LayoutTestActivity.this, ExpandableRecycleList.class);
            startActivity(intent);
        });
        findViewById(R.id.ch3_test_wechatlist_but).setOnClickListener(v -> {
            Intent intent = new Intent(LayoutTestActivity.this, TestWechatActivity.class);
            startActivity(intent);
        });
    }
}
