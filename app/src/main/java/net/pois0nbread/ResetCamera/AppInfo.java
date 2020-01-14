package net.pois0nbread.ResetCamera;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

/**
 * <pre>
 *     author : Pois0nBread
 *     e-mail : pois0nbreads@gmail.com
 *     time   : 2019/12/02
 *     desc   : AppInfo
 *     version: 1.0
 * </pre>
 */


public class AppInfo {

    private Drawable icon;// 应用图标
    private String appName;// 应用名称
    private String packageName;// 包名
    private boolean checkStatus;

    public AppInfo(Drawable icon, String appName, String packageName, SharedPreferences preferences) {
        super();
        this.icon = icon;
        this.appName = appName;
        this.packageName = packageName;
        this.checkStatus = preferences.getBoolean(packageName, false);
    }

    public AppInfo() {
        super();
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public String toString() {
        return "AppInfo [icon=" + icon + ", appName=" + appName
                + ", packageName=" + packageName + "]";
    }
}
