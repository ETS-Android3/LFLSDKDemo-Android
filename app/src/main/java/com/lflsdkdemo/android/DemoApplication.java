package com.lflsdkdemo.android;

import android.app.Application;

import com.alldls.lflsdk.LflSDK;
import com.alldls.lflsdk.listener.InitListener;


/**
 * Created on 2021/11/17.
 *
 * @author suntinghui
 */
public class DemoApplication extends Application {

    public static boolean lflInitSuccess = false;
    public static String AppId = "";

    @Override
    public void onCreate() {
        super.onCreate();
        LflSDK.init(this, AppId, new InitListener() {
            @Override
            public void initSuccess() {
                lflInitSuccess = true;
            }
        });
    }
}
