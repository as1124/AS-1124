package com.as1124.ui.layout.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.as1124.ui.R;

import java.util.Random;

/**
 * 进阶学习{@link RecyclerView}的详细使用和原理，制作类似于淘宝商品列表的界面
 * <ul>
 * <li>自定义列表布局</li>
 * <li>自定义分割装饰</li>
 * <li>自定义Item特效</li>
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class AboutRecyclerView extends Activity {

    private RecyclerView recyclerList;
    private RecyclerListAdapter listAdapter;

    private SparseArray<ArrayMap<String, Object>> itemsArray;

    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_recycler_view);

        layoutInflater = getLayoutInflater();
        recyclerList = findViewById(R.id.recycler_list);

//        recyclerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
//        recyclerList.setLayoutManager(new RecyclerListLayoutManager());
//        recyclerList.setItemAnimator(new RecyclerListItemAnimator());
//        recyclerList.addItemDecoration(new RecyclerListItemDecoration());
//        recyclerList.setEdgeEffectFactory(new RecyclerListEdgeEffectFactory());

        initItemList();
        listAdapter = new RecyclerListAdapter();
        recyclerList.setAdapter(listAdapter);

//        TelephonyManager manager = (TelephonyManager) getSystemService(TelephonyManager.class);
//        manager.
    }

    private void initItemList() {
        itemsArray = new SparseArray<>();
        Random random = new Random(5000);
        for (int i = 0; i < 13; i++) {
            ArrayMap<String, Object> data = new ArrayMap<>();
            data.put("itemName", "Adidas 三叶草/ 真品沙雕士大夫艰苦零零零零哈哈哈裤子，牛批" + i);
            data.put("itemPrice", "￥1122.9");
            data.put("saleNum", random.nextInt() + "已付款");

            itemsArray.put(i, data);
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
        ImageButton feedbackBtn;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text_name);
            priceText = itemView.findViewById(R.id.text_price);
            numText = itemView.findViewById(R.id.text_sale_num);
            feedbackBtn = itemView.findViewById(R.id.btn_feedback);
        }
    }
}
