package com.aiitec.greedaostudydemo.base;

import android.app.Application;

import com.aiitec.greedaostudydemo.model.DaoMaster;
import com.aiitec.greedaostudydemo.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * @Author: ailibin
 * @Time: 2019/04/18
 * @Description: 测试App
 * @Email: ailibin@qq.com
 */
public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // regular SQLite database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();

        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db-encrypted");
        // Database db = helper.getEncryptedWritableDb("encryption-key");

        daoSession = new DaoMaster(db).newSession();

        initLifecycle();

    }

    private void initLifecycle() {

        registerActivityLifecycleCallbacks(new MyLifecycleHandler());

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}
