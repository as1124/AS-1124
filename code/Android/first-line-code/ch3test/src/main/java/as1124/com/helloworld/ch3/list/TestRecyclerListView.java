package as1124.com.helloworld.ch3.list;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import as1124.com.helloworld.R;
import as1124.com.helloworld.ch3.FruitItem;

/**
 * 学习使用android-support中的RecyclerListView.
 * <br/>和ListView不同的在于，{@link RecyclerView}初始化的时候需要设置一个{@link RecyclerView.LayoutManager}
 * 否则数据无法展现
 *
 * @author as-1124(as1124huang@gmail.com)
 */
public class TestRecyclerListView extends Activity {

    protected static List<FruitItem> fruits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ch3_recyclerlist);

        initFruits();
        RecyclerView recyclerList = findViewById(R.id.ch3_test_recyclerlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // 关键步骤
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new RecycleItemAdapter(fruits));
    }

    protected static void initFruits() {
        fruits.add(new FruitItem("Apple", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Banana", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Cherry", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Apple", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Banana", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Cherry", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Apple", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Banana", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Cherry", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Apple", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange", R.mipmap.ic_launcher_round));

        fruits.add(new FruitItem("Orange2", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange1", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange3", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange4", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange5", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange6", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange7", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange8", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange9", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange11", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange12", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange13", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange14", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange15", R.mipmap.ic_launcher_round));
        fruits.add(new FruitItem("Orange22", R.mipmap.ic_launcher_round));
    }
}
