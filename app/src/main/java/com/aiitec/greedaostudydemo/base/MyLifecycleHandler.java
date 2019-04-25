package com.aiitec.greedaostudydemo.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.aiitec.greedaostudydemo.util.LogUtil;

/**
 * @Author: ailibin
 * @Time: 2019/04/19
 * @Description: 监控应用前后台切换
 * @Email: ailibin@qq.com
 */
public class MyLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private int count = 0;

    private static final String TAG = "ailibin";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (count == 0) {
            //后台切换到前台
            LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>>App切到前台");
        }
        count++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        //前台切换到后台
        count--;
        if (count == 0) {
            LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>>App切到后台");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
