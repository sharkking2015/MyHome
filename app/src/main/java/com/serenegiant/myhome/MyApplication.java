package com.serenegiant.myhome;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.serenegiant.myhome.bean.DaoMaster;
import com.serenegiant.myhome.bean.DaoSession;

public class MyApplication extends Application{
    private DaoSession daoSession;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //实例化一个OpenHelper实例，类似于使用的SQLiteOpenHelper类
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "movie-db");

        //获取一个SQLiteDatabase
        SQLiteDatabase database = helper.getWritableDatabase();

        //使用数据库对象构造一个DaoMaster
        DaoMaster daoMaster = new DaoMaster(database);

        //开启DoaSession
        daoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
