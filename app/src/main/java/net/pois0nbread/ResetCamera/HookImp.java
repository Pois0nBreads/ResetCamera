package net.pois0nbread.ResetCamera;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class HookImp implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    @SuppressLint("NewApi")
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

        if (lpparam.packageName.equals(BuildConfig.APPLICATION_ID)) {
            findAndHookMethod(findClass(MainActivity.class.getName(), lpparam.classLoader),
                    "isHooked", XC_MethodReplacement.returnConstant(true));
        }

        if (!Setting.getEnable()) return;

        if  (!Setting.getAppEnable(lpparam.packageName)) return;

        findAndHookMethod("android.hardware.Camera", lpparam.classLoader, "open",
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam arg0) throws Throwable {
                        XposedBridge.log("AAinfo_open()");
                        return Camera.open(0);
                    }
                });
        findAndHookMethod("android.hardware.Camera", lpparam.classLoader, "open",
                int.class , new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            XposedBridge.log("AAinfo_open(" + param.args[0] + ")");
                            if ((Integer) param.args[0] == 0) {
                                if (Setting.getSet("sw2")) {
                                    param.args[0] = 1;
                                }
                            } else if ((Integer) param.args[0] == 1) {
                                if (Setting.getSet("sw1")) {
                                    param.args[0] = 0;
                                }
                            }
                        } catch (Exception e) {}
                    }
                });
        findAndHookMethod("android.hardware.camera2.CameraCharacteristics", lpparam.classLoader, "get",
                CameraCharacteristics.Key.class , new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            XposedBridge.log("AAinfo_get(" + param.getResult() + ")");
                            if ((Integer) param.getResult() == 0 || param.getResult() == null) {
                                if (Setting.getSet("sw2")) {
                                    param.setResult(1);
                                }
                            } else if ((Integer) param.getResult() == 1) {
                                if (Setting.getSet("sw1")) {
                                    param.setResult(0);
                                }
                            }
                        } catch (Exception e) {}
                    }
                });
    }

    @Override
    public void initZygote(StartupParam startupParam) {
    }
}
