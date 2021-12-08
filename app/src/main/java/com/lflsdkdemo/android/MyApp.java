package com.lflsdkdemo.android;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

/**
 * Created on 2021/12/8.
 *
 * @author suntinghui
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        UMConfigure.preInit(this, "61b04b30e014255fcba5a0d5", "channel");
        UMConfigure.init(this, "61b04b30e014255fcba5a0d5", "channel", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
    }
}
