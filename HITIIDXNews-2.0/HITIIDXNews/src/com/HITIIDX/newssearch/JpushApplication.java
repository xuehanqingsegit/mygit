package com.HITIIDX.newssearch;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK

 */
public class JpushApplication extends Application {
    private static final String TAG = "JPush";

    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();
         
         JPushInterface.setDebugMode(true);
         JPushInterface.init(this);     		
    }
}
