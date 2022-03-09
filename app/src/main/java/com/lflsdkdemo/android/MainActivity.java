package com.lflsdkdemo.android;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alldls.lflsdk.CustomTaskType;
import com.alldls.lflsdk.LflSDK;
import com.alldls.lflsdk.listener.EventListener;
import com.alldls.lflsdk.listener.LflCustomTaskListener;

public class MainActivity extends AppCompatActivity {

    private EditText etAppId;
    private EditText etUserId;
    private Button btnGo;

    private String appId = "";
    private String userId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        etAppId = (EditText) findViewById(R.id.et_app_id);
        etUserId = (EditText) findViewById(R.id.et_user_id);
        btnGo = (Button) findViewById(R.id.btn_go);


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etAppId.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, "please set app id", Toast.LENGTH_SHORT).show();
                    return;
                }
                appId = etAppId.getText().toString().trim();
                if (TextUtils.isEmpty(etUserId.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, "please set user id", Toast.LENGTH_SHORT).show();
                    return;
                }
                userId = etUserId.getText().toString().trim();
                //初始化sdk
                LflSDK.init((Application) MainActivity.this.getApplicationContext(), appId);
                //添加自定义任务回调（可选）
                LflSDK.addListener(new LflCustomTaskListener() {
                    @Override
                    public void onCallCustomTask(CustomTaskType customTaskType) {

                    }

                    @Override
                    public void onCallCustomTask(Context context, CustomTaskType customTaskType) {
                        if (customTaskType == CustomTaskType.SHARE) {
                            //调用媒体端分享逻辑
                        } else if (customTaskType == CustomTaskType.INVITE) {
                            //调用媒体端邀请逻辑
                        } else if (customTaskType == CustomTaskType.TAKE_PHOTO) {
                            //调用媒体端拍照逻辑
                        } else if (customTaskType == CustomTaskType.CHECK_LOGIN) { //调用媒体端检测登录逻辑
                            if (true) {
                                LflSDK.triggerSuccess(customTaskType);
                            } else {

                                LflSDK.triggerFail(customTaskType);
                            }
                        } else if (customTaskType == CustomTaskType.LOGIN) { //调用媒体端登录逻辑
                        }
                    }
                });
                //打开乐福乐页面
                LflSDK.show(MainActivity.this, userId, new EventListener() {
                    @Override
                    public void onPageClose() {
                        Toast.makeText(MainActivity.this, "onPageClose", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}