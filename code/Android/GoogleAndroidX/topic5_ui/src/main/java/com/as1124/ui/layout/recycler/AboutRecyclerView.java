package com.as1124.ui.layout.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.as1124.ui.R;

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
public class AboutRecyclerView extends AppCompatActivity {

    private RecyclerView recyclerList;
    private RecyclerListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_recycler_view);

        recyclerList = findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new RecyclerListLayoutManager());
        recyclerList.setItemAnimator(new RecyclerListItemAnimator());
        recyclerList.addItemDecoration(new RecyclerListItemDecoration());
        recyclerList.setEdgeEffectFactory(new RecyclerListEdgeEffectFactory());
        listAdapter = new RecyclerListAdapter();
        recyclerList.setAdapter(listAdapter);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class RecyclerListAdapter extends RecyclerView.Adapter<ItemHolder> {

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
