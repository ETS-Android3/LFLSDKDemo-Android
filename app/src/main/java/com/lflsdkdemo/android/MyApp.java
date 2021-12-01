package com.lflsdkdemo.android;

import android.app.Application;

import com.happytour.lflsdk.LflSDK;


/**
 * Created on 2021/11/26.
 *
 * @author suntinghui
 */
public class MyApp extends Application {
    public static String appId="";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化sdk
        LflSDK.init(this, appId);
    }
}
