package com.as1124.ch6test.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table book(" +
            "id INTEGER primary key autoincrement," +
            "author text," +
            "price double," +
            "pages int," +
            "name varchar(128));";

    public static final String CREATE_CATEGORY = "create table category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code int);";

    private Context context;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.context = context;
    }

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                            DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(this.context, "数据库表创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getDatabaseName() {
        String name = super.getDatabaseName();
        android.util.Log.i("MyDatabaseHelper", "数据库名称==" + name);
        return name;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book;");
        db.execSQL("drop table if exists category");
        android.util.Log.i("MyDatabaseHelper", "数据库操作方法onUpgrade方法执行");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        android.util.Log.i("MyDatabaseHelper", "数据库打开onOpen");
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
