package net.pois0nbread.ResetCamera;

import de.robv.android.xposed.XSharedPreferences;

public class Setting {
    private static XSharedPreferences xSharedPreferences = null;

    private static XSharedPreferences getxSharedPreferences(){
        if (xSharedPreferences == null) {
            xSharedPreferences = new XSharedPreferences("net.pois0nbread.ResetCamera", "settings");
            xSharedPreferences.makeWorldReadable();
        } else {
            xSharedPreferences.reload();
        }
        return xSharedPreferences;
    }

    public static boolean getEnable(){return  getxSharedPreferences().getBoolean("enable", false);}

    public static boolean getAppEnable(String packageName){
        if (getxSharedPreferences().getBoolean("getAppEnable", false)) {
            return true;
        } else {
            return  getxSharedPreferences().getBoolean(packageName, false);
        }
    }

    public static boolean getSet(String i){ return getxSharedPreferences().getBoolean(i, false);}
}
