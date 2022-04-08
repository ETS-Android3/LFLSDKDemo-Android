package com.happytour.lflsdk.demo;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

/**
 * Created on 2021/10/27.
 *
 * @author suntinghui
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 用于测试，不要出现在生产环境
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }
}
