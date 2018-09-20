package as1124.com.helloworld.ch3;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import as1124.com.helloworld.R;

public class TestListview extends Activity implements View.OnClickListener {

    private List<FruitItem> fruits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ch3_listview);

        ListView listView = findViewById(R.id.ch3_test_listview);
        initFruits();
        FruitAdapter dataAdapter = new FruitAdapter(this, R.layout.listview_item_fruit, fruits, this);
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // parent 是ListView的示例
            // view 是ListView中发生点击事件的Item
            // position 是被点击元素在ListView中的位置，从0开始
            // id 是
            FruitItem itemData = fruits.get(position);
            Toast.makeText(parent.getContext(), itemData.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    private void initFruits() {
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

    @Override
    public void onClick(View v) {
        int position = Integer.parseInt(v.getTag().toString());
        FruitItem itemData = fruits.get(position);
        new AlertDialog.Builder(v.getContext()).setTitle("ListView子项点击测试").setMessage("U click data at " + position + ", the name is "
                + itemData.getName() + ". The view class is " + v.getClass().getName())
                .show();
    }
}
