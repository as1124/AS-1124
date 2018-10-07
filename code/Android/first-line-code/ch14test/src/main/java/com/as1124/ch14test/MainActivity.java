package com.as1124.ch14test;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.as1124.ch14test.db.ProvinceDao;
import com.as1124.ch14test.util.GSONUtil;
import com.as1124.ch14test.util.HttpUtil;

import org.greenrobot.greendao.database.Database;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_province_all).setOnClickListener(this);
        findViewById(R.id.but_province_city).setOnClickListener(this);
        findViewById(R.id.but_province_counry).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_province_all:
                saveAllProvince();
                break;
            case R.id.but_province_city:
                saveSHCity();
                break;
            case R.id.but_province_counry:
                saveCountry();
                break;
            default:
                break;
        }
    }

    private void saveAllProvince() {
        okhttp3.Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                android.util.Log.e(MainActivity.class.getSimpleName(), "拉取全部省份信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                response.close();
                GSONUtil.handleProvinceResponse(result);
            }
        };
        HttpUtil.sendHttpRequest("http://guolin.tech/api/china", callback);
    }

    private void saveSHCity() {
        okhttp3.Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                android.util.Log.e(MainActivity.class.getSimpleName(), "拉取上海市辖区信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                response.close();
                GSONUtil.handleCityResponse(result, Long.valueOf(2));
            }
        };
        HttpUtil.sendHttpRequest("http://guolin.tech/api/china/2", callback);


        okhttp3.Callback callback2 = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                android.util.Log.e(MainActivity.class.getSimpleName(), "拉取浙江省市级信息失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                response.close();
                GSONUtil.handleCityResponse(result, Long.valueOf(17));
            }
        };
        HttpUtil.sendHttpRequest("http://guolin.tech/api/china/17", callback2);
    }

    private void saveCountry() {
        Database db = CH14Application.getDaoSession().getDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from " + ProvinceDao.TABLENAME, null);
            if (cursor != null) {
                try {
                    while (cursor.moveToNext()) {
                        String provinceName = cursor.getString(cursor.getColumnIndex("name"));
                        android.util.Log.i("aaaaaaa", provinceName);
                    }
                } finally {
                    cursor.close();
                }
            }
        }
    }
}
