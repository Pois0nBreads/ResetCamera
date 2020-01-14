package net.pois0nbread.ResetCamera;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    SharedPreferences shared = null;

    Switch mSwitch = null, mSwitch1 = null, mSwitch2 = null, mSwitch3 = null, mSwitch4 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isHooked()) {
            Toast.makeText(this, "模块已激活", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "模块未激活", Toast.LENGTH_SHORT).show();
        }
        shared = getSharedPreferences("settings", Context.MODE_WORLD_READABLE);
        initView();
        mSwitch.setChecked(shared.getBoolean("enable", false));
        mSwitch1.setChecked(shared.getBoolean("sw1", false));
        mSwitch2.setChecked(shared.getBoolean("sw2", false));
        mSwitch3.setChecked(shared.getBoolean("isHide", false));
        mSwitch4.setChecked(shared.getBoolean("getAppEnable", false));
        bindListen();
    }

    private void initView() {
        mSwitch = (Switch) findViewById(R.id.main_switch);
        mSwitch1 = (Switch) findViewById(R.id.main_switch1);
        mSwitch2 = (Switch) findViewById(R.id.main_switch2);
        mSwitch3 = (Switch) findViewById(R.id.main_switch3);
        mSwitch4 = (Switch) findViewById(R.id.main_switch4);
    }

    private void bindListen() {
        mSwitch.setOnCheckedChangeListener(this);
        mSwitch1.setOnCheckedChangeListener(this);
        mSwitch2.setOnCheckedChangeListener(this);
        mSwitch3.setOnCheckedChangeListener(this);
        mSwitch4.setOnCheckedChangeListener(this);
        findViewById(R.id.main_button1).setOnClickListener(this);
        findViewById(R.id.main_button2).setOnClickListener(this);
        findViewById(R.id.main_rl1).setOnClickListener(this);
    }

    private boolean isHooked(){return false;}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button1:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.coolapk.com/u/2108563")));
                break;
            case R.id.main_button2:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Pois0nBreads/ResetCamera")));
                break;
            case R.id.main_rl1:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.main_switch:
                shared.edit().putBoolean("enable", isChecked).apply();
                break;
            case R.id.main_switch1:
                shared.edit().putBoolean("sw1", isChecked).apply();
                break;
            case R.id.main_switch2:
                shared.edit().putBoolean("sw2", isChecked).apply();
                break;
            case R.id.main_switch3:
                if (isChecked) {
                    getPackageManager().setComponentEnabledSetting(new ComponentName(MainActivity.this, "net.pois0nbread.ResetCamera.MainAlias"),
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                } else {
                    getPackageManager().setComponentEnabledSetting(new ComponentName(MainActivity.this, "net.pois0nbread.ResetCamera.MainAlias"),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                }
                shared.edit().putBoolean("isHide", isChecked).apply();
                Toast.makeText(MainActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_switch4:
                shared.edit().putBoolean("getAppEnable", isChecked).apply();
                break;
        }
    }
}
