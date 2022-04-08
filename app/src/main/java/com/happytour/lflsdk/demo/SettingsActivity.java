package com.happytour.lflsdk.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IGetter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    private EditText etUserId;
    private EditText etAppId;
    private Button btnSave;

    private SharedPreferences sp;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("设置");
        progress = new ProgressDialog(this);
        progress.setMessage("加载中...");
        progress.setCancelable(false);

        sp = getSharedPreferences("demo_lfl_config", Context.MODE_PRIVATE);

        etAppId = findViewById(R.id.et_app_id);
        etUserId = findViewById(R.id.et_user_id);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString("app_id", etAppId.getText().toString().trim()).apply();
                sp.edit().putString("user_id", etUserId.getText().toString().trim()).apply();
                setResult(RESULT_OK);
                finish();
            }
        });

        loadAppId();
        loadUserId();
    }

    private void loadUserId() {
        String user_id = sp.getString("user_id", "");
        if (TextUtils.isEmpty(user_id)) {
            progress.show();
            DeviceID.getOAID(this, new IGetter() {
                @Override
                public void onOAIDGetComplete(String result) {
                    etUserId.setText(result);
                    progress.dismiss();
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    Toast.makeText(SettingsActivity.this, "OAID为空，请填入UserId", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            });
        } else {
            etUserId.setText(user_id);
        }
    }

    private void loadAppId() {
        String app_id = sp.getString("app_id", "");
        if (TextUtils.isEmpty(app_id)) {
            progress.show();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    String line;
                    try {
                        HttpURLConnection con = (HttpURLConnection) new URL("http://211.154.195.102:8003/lfl/android/appid.html").openConnection();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        line = reader.readLine();
                        reader.close();
                        con.disconnect();
                    } catch (Exception e) {
                        line = null;
                    }
                    final String f_line = line;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(f_line)) {
                                Toast.makeText(SettingsActivity.this, "获取默认AppId失败，请手动填入", Toast.LENGTH_LONG).show();
                            } else {
                                etAppId.setText(f_line);
                            }
                            progress.dismiss();
                        }
                    });

                }
            });
        } else {
            etAppId.setText(app_id);
        }
    }
}