package com.as1124.ui.layout.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.as1124.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类似于GMail收件箱的RecyclerView
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class RecyclerViewActivity extends Activity {

    private RecyclerView mEmailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        View decorRoot = window.getDecorView();
        decorRoot.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE);

        mEmailList = findViewById(R.id.recyclerview_like_gmail);

        // use this setting to improve performance if you know that changes in content
        // do not change the layout size of the RecyclerView
        mEmailList.setHasFixedSize(true);
        mEmailList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mEmailList.setAdapter(new EMailRecyclerListAdapter(getEMails()));
    }

    private List<EMailModel> getEMails() {
        List<EMailModel> initModels = new ArrayList<>();
        initModels.add(new EMailModel("Wishing you a Merry Chirstmas!", "Esprit", "12月25日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        initModels.add(new EMailModel("欢迎加入滴答清单", "滴答清单", "12月16日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        initModels.add(new EMailModel("@亲爱的同学，您有一份职位推荐，请查看", "前程无忧（51Job）", "12月15日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        initModels.add(new EMailModel("Wishing you a Merry Chirstmas!", "Esprit", "12月25日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        initModels.add(new EMailModel("Wishing you a Merry Chirstmas!", "Esprit", "12月25日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        initModels.add(new EMailModel("Wishing you a Merry Chirstmas!", "Esprit", "12月25日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        initModels.add(new EMailModel("Wishing you a Merry Chirstmas!", "Esprit", "12月25日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        initModels.add(new EMailModel("Wishing you a Merry Chirstmas!", "Esprit", "12月25日", "2件7折Pick你的圣诞Look！线上浏览ESPRIT商品链接"));
        return initModels;
    }
}
