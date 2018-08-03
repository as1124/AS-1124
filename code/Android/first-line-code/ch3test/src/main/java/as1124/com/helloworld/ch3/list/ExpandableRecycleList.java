package as1124.com.helloworld.ch3.list;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import as1124.com.helloworld.R;

/**
 * 通过{@link RecyclerView} 和瀑布流布局器{@link StaggeredGridLayoutManager}创建多级展开的列表
 *
 * @author as-1124(as1124huang@gmail.com)
 */
public class ExpandableRecycleList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3_recyclerlist);

        TestRecyclerListView.initFruits();
        RecyclerView recycleList = findViewById(R.id.ch3_test_recyclerlist);
        // 瀑布流式布局管理，3列，纵向
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
        RecycleItemAdapter dataAdapter = new RecycleItemAdapter(TestRecyclerListView.fruits);
        recycleList.setAdapter(dataAdapter);
    }
}
