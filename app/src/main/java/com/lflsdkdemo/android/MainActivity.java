package com.lflsdkdemo.android;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alldls.lflsdk.CustomTaskType;
import com.alldls.lflsdk.LflSDK;
import com.alldls.lflsdk.listener.EventListener;
import com.alldls.lflsdk.listener.InitListener;
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
                // 初始化sdk
                LflSDK.init(getApplication(), appId, new InitListener() {
                    @Override
                    public void initSuccess() {
                        Toast.makeText(MainActivity.this, "initSuccess\nsdk ver: " + LflSDK.sdkVersion() + " \nweb ver: " + LflSDK.sdkAssetsVersion(), Toast.LENGTH_SHORT).show();
                        LflSDK.show(MainActivity.this, userId, new EventListener() {
                            @Override
                            public void onPageClose() {
                                Toast.makeText(null, "onPageClose", Toast.LENGTH_SHORT).show();
                            }
                        });
                        LflSDK.addListener(new LflCustomTaskListener() {
                            @Override
                            public void onCallCustomTask(Context context, int customTaskType) {
                                if (customTaskType == CustomTaskType.SHARE) {
                                    //调用媒体端分享逻辑
                                    new AlertDialog.Builder(context)
                                            .setTitle("分享")
                                            .setMessage("模拟分享过程")
                                            .setNegativeButton("分享失败", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerFail(customTaskType);
                                                }
                                            })
                                            .setPositiveButton("分享成功", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerSuccess(customTaskType);
                                                }
                                            }).show();
                                } else if (customTaskType == CustomTaskType.INVITE) {
                                    //调用媒体端邀请逻辑
                                    new AlertDialog.Builder(context)
                                            .setTitle("邀请")
                                            .setMessage("模拟邀请过程")
                                            .setNegativeButton("邀请失败", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerFail(customTaskType);
                                                }
                                            })
                                            .setPositiveButton("邀请成功", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerSuccess(customTaskType);
                                                }
                                            }).show();
                                } else if (customTaskType == CustomTaskType.TAKE_PHOTO) {
                                    //调用媒体端拍照逻辑
                                    new AlertDialog.Builder(context)
                                            .setTitle("拍照")
                                            .setMessage("模拟拍照过程")
                                            .setNegativeButton("拍照失败", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerFail(customTaskType);
                                                }
                                            })
                                            .setPositiveButton("拍照成功", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerSuccess(customTaskType);
                                                }
                                            }).show();
                                } else if (customTaskType == CustomTaskType.CHECK_LOGIN) { //调用媒体端检测登录逻辑
                                    new AlertDialog.Builder(context)
                                            .setTitle("检测登录")
                                            .setMessage("模拟检测登录过程")
                                            .setNegativeButton("未登录", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerFail(customTaskType);
                                                }
                                            })
                                            .setPositiveButton("已登录", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerSuccess(customTaskType);
                                                }
                                            }).show();
                                } else if (customTaskType == CustomTaskType.LOGIN) { //调用媒体端登录逻辑
                                    new AlertDialog.Builder(context)
                                            .setTitle("登录")
                                            .setMessage("模拟登录过程")
                                            .setNegativeButton("登录失败", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerFail(customTaskType);
                                                }
                                            })
                                            .setPositiveButton("登录成功", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    LflSDK.triggerSuccess(customTaskType);
                                                }
                                            }).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void initFail() {
                        Toast.makeText(MainActivity.this, "initFail", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}