package com.as1124.ch6test.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.as1124.ch6test.R;

public class DatabaseMainActivity extends Activity implements View.OnClickListener {

    MyDatabaseHelper dbHelper = null;

    private static int version = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_main);

        dbHelper = new MyDatabaseHelper(getApplicationContext(), "BookStore.db", null, version);
        version++;
        findViewById(R.id.but_table_create).setOnClickListener(this);
        findViewById(R.id.but_table_update).setOnClickListener(this);
        findViewById(R.id.but_table_query).setOnClickListener(this);
        findViewById(R.id.but_table_insert).setOnClickListener(this);
        findViewById(R.id.but_table_delete).setOnClickListener(this);
        findViewById(R.id.but_database_drop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_table_create:
                createDatabase();
                break;
            case R.id.but_table_query:
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                Cursor cursor = database.query("book", null, null, null, null, null, null);
                if (cursor != null) {
                    try {
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String author = cursor.getString(cursor.getColumnIndex("author"));
                            double price = cursor.getDouble(cursor.getColumnIndex("price"));
                            android.util.Log.i("Database#Query", "查询数据==" + author + "," + name + "," + price);
                        }
                    } finally {
                        cursor.close();
                    }
                }
                break;
            case R.id.but_table_insert:
                insertData();
                break;
            case R.id.but_table_update:
                updateData();
                break;
            case R.id.but_table_delete:
                deleteData();
                break;
            case R.id.but_database_drop:
                break;
            default:
                break;
        }
    }

    private void createDatabase() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (database != null) {
            database.close();
        }
        database = dbHelper.getWritableDatabase();
        if (database != null) {
            database.close();
        }
    }

    private void insertData() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (database != null) {
            // ContentValues其实就相当于是一个Map，存放需要插入的数据的字段和对应值
            ContentValues rowData = new ContentValues();
            rowData.put("author", "你大爷");
            rowData.put("price", 22.22);
            rowData.put("pages", 500);
            rowData.put("name", "30天精通所有编程");
            long result = database.insert("book", null, rowData);
            rowData.put("author", "去你妈的");
            result = database.insert("book", null, rowData);
            android.util.Log.i("Database#Insert", "插入结果==" + result);
            database.close();
        }
    }

    private void updateData() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (database != null) {
            ContentValues rowData = new ContentValues();
            rowData.put("price", 666.66);
            long result = database.update("book", rowData, "price=?", new String[]{"22.22"});
            android.util.Log.i("Database#Update", "更新结果==" + result);
            database.close();
        }
    }

    private void deleteData() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (database != null) {
            long result = database.delete("book", "name=?", new String[]{"去你妈的"});
            android.util.Log.i("Database#Delete", "删除结果==" + result);
            database.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

}
