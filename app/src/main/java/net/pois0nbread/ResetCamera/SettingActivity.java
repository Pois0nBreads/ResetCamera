package net.pois0nbread.ResetCamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private Context mContext;

    private ListView listView;
    private AppAdapter adapter;

    public static SharedPreferences preferences;

    private boolean SYSTEM_Tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;
        preferences = getSharedPreferences("settings", Context.MODE_WORLD_WRITEABLE);

        listView = (ListView) findViewById(R.id.setting_list);
        adapter = new AppAdapter(getAllAppInfos(), mContext, preferences);
        listView.setAdapter(adapter);

    }

    protected List<AppInfo> getAllAppInfos() {
        List<AppInfo> list = new ArrayList<AppInfo>();
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ResolveInfos = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo ri : ResolveInfos) {
            String packageName = ri.activityInfo.packageName;
            Drawable icon = ri.loadIcon(packageManager);
            String appName = ri.loadLabel(packageManager).toString();
            AppInfo appInfo = new AppInfo(icon, appName, packageName, preferences);
            list.add(appInfo);
        }
        if (SYSTEM_Tag) {
            List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(PackageManager.MATCH_SYSTEM_ONLY);
            for (ApplicationInfo info : applicationInfos) {
                String packagename = info.packageName;
                Drawable icon = info.loadIcon(packageManager);
                String appName = info.loadLabel(packageManager).toString();
                AppInfo appInfo = new AppInfo(icon, appName, packagename, preferences);
                list.add(appInfo);
            }
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settingmenu, menu);

        menu.findItem(R.id.menu_check).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(!item.isChecked());
                SYSTEM_Tag = item.isChecked();
                if (item.isChecked()) {
                    Toast.makeText(mContext, "已显示", Toast.LENGTH_SHORT).show();
                    item.setTitle("隐藏系统应用");
                } else {
                    Toast.makeText(mContext, "未显示", Toast.LENGTH_SHORT).show();
                    item.setTitle("显示系统应用");
                }
                adapter = new AppAdapter(getAllAppInfos(), mContext, preferences);
                listView.setAdapter(adapter);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
