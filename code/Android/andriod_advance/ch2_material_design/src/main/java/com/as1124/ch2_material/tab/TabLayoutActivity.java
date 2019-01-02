package com.as1124.ch2_material.tab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.as1124.ch2_material.R;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        Toolbar toolbar = findViewById(R.id.toolbar_material_design);
        setSupportActionBar(toolbar);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tab_material_design);

        initViewPager();
    }

    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");
        titles.add("购物");
        titles.add("明星");
        titles.add("视频");
        titles.add("健康");
        titles.add("励志");
        titles.add("搞笑");
        titles.add("动漫");
        titles.add("本地");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {
            fragments.add(new ListFragment());
        }
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        // 给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapter);
        // 将TabLayout 和 ViewPager 关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        // 给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);
    }
}
