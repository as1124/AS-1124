package com.as1124.ui.layout.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.as1124.ui.R;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 进阶学习{@link RecyclerView}的详细使用和原理，制作类似于淘宝商品列表的界面
 * <ul>
 * <li>列表布局
 * <p>
 * <ol><li> {@link androidx.recyclerview.widget.LinearLayoutManager}: 单一方向直线布局
 * <li> {@link GridLayoutManager}: 网格布局，每个Item一定是等高等宽的，不能形成像瀑布流那样
 * <li> {@link StaggeredGridLayoutManager}: 瀑布流式布局
 * </ol>
 * </p>
 * </li>
 * <li>自定义分割装饰</li>
 * <li>自定义Item特效</li>
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class AboutRecyclerView extends Activity implements View.OnClickListener {

    private RecyclerView recyclerList;

    private RecyclerListAdapter listAdapter;

    private List<ArrayMap<String, Object>> itemsArray;

    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_recycler_view);

        layoutInflater = getLayoutInflater();
        recyclerList = findViewById(R.id.recycler_list);


        // 1. 单方向直线布局
//        recyclerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        // 2. 网格布局，每个Item一定是等宽等高的，不能像瀑布流那样
//        recyclerList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        recyclerList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerList.setLayoutManager(new RecyclerListLayoutManager());

        recyclerList.setItemAnimator(new RecyclerListItemAnimator());
        recyclerList.addItemDecoration(new RecyclerListItemDecoration());
        recyclerList.setEdgeEffectFactory(new RecyclerListEdgeEffectFactory());

        initItemList();
        listAdapter = new RecyclerListAdapter();
        recyclerList.setAdapter(listAdapter);

        findViewById(R.id.btn_add_item).setOnClickListener(this);
        findViewById(R.id.btn_remove_item).setOnClickListener(this);
        findViewById(R.id.btn_update_item).setOnClickListener(this);
        findViewById(R.id.btn_delete_decor).setOnClickListener(this);
    }

    private void initItemList() {
        itemsArray = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        {
            ArrayMap<String, Object> data = new ArrayMap<>();
            data.put("itemName", "夫苦零");
            data.put("itemPrice", "￥1122.9");
            data.put("saleNum", random.nextInt(5000) + "已付款");
            itemsArray.add(data);
        }

        for (int i = 0; i < 50; i++) {
            ArrayMap<String, Object> data = new ArrayMap<>();
            StringBuilder sbName = new StringBuilder("Adidas 三叶草/ 真品沙");
            data.put("itemName", sbName.toString() + i);
            data.put("itemPrice", "￥1122.9");
            data.put("saleNum", random.nextInt(5000) + "已付款");
            itemsArray.add(data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_item:
                ArrayMap<String, Object> data = new ArrayMap<>();
                int newPosition = itemsArray.size();
                data.put("itemName", "这是新的Item--" + newPosition);
                data.put("itemPrice", "￥1122.9");
                data.put("saleNum", "1122已付款");
                itemsArray.add(newPosition, data);
                listAdapter.notifyItemInserted(newPosition);
                break;
            case R.id.btn_remove_item:
                itemsArray.remove(0);
                listAdapter.notifyItemRemoved(0);
                break;
            case R.id.btn_update_item:
                itemsArray.get(3).put("itemName", "更新内容11");
                itemsArray.get(3).put("itemPrice", "$2233");
                listAdapter.notifyItemChanged(3);
                break;
            case R.id.btn_delete_decor:
                // 删除装饰器对象，不是删除装饰器效果
                recyclerList.removeItemDecorationAt(0);
            default:
                break;
        }
    }

    class RecyclerListAdapter extends RecyclerView.Adapter<ItemHolder> {

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View contentView = layoutInflater.inflate(R.layout.item_recycler_list, parent, false);
            return new ItemHolder(contentView);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            ArrayMap<String, Object> itemData = itemsArray.get(position);
            holder.nameText.setText(itemData.get("itemName").toString());
            holder.priceText.setText(itemData.get("itemPrice").toString());
            holder.numText.setText(itemData.get("saleNum").toString());
            holder.feedbackBtn.setTag(position);
            holder.feedbackBtn.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "点击了Button", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            if (itemsArray != null) {
                return itemsArray.size();
            } else {
                return 0;
            }
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView nameText, priceText, numText;
        ImageView feedbackBtn;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text_name);
            priceText = itemView.findViewById(R.id.text_price);
            numText = itemView.findViewById(R.id.text_sale_num);
            feedbackBtn = itemView.findViewById(R.id.img_more);
        }
    }

}


