package com.lflsdkdemo.android;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.happytour.lflsdk.CustomTaskType;
import com.happytour.lflsdk.LflSDK;
import com.happytour.lflsdk.api.EventListener;
import com.happytour.lflsdk.api.LflCustomTaskListener;
//

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

        etAppId.setText(MyApp.appId);
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

                //添加自定义任务回调（可选）
                LflSDK.addListener(new LflCustomTaskListener() {
                    @Override
                    public void onCallCustomTask(CustomTaskType customTaskType) {
                        LflSDK.triggerSuccess(customTaskType);
                    }


                });
                //打开乐福乐页面
                LflSDK.show(MainActivity.this, appId, userId, new EventListener() {
                    @Override
                    public void onPageClose() {
                        Toast.makeText(MainActivity.this, "onPageClose", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}