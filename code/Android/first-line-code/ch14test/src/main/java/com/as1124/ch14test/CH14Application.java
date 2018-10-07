package com.as1124.ch14test;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.as1124.ch14test.db.DaoMaster;
import com.as1124.ch14test.db.DaoSession;

import org.greenrobot.greendao.database.Database;

public class CH14Application extends Application {

    public static final String DATABASE_NAME = "ch14test.db";

    static Context appContext = null;

    static DaoSession daoSession = null;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(appContext, DATABASE_NAME);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (daoSession != null) {
            Database db = daoSession.getDatabase();
            if (db != null) {
                db.close();
            }
        }
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
