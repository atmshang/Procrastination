package com.atmshang.toolkit.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by cnwan on 2016/11/24.
 */

public class PackageUtil {

    public static int getVersionCode(Context context) {
        PackageInfo info = getPackageInfo(context.getApplicationContext());
        return info.versionCode;
    }

    public static PackageInfo getPackageInfo(Context context) {
        Context aContext = context.getApplicationContext();
        PackageInfo info = null;
        try {
            info = aContext.getPackageManager().
                    getPackageInfo(aContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo
                    (context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static CharSequence getUpdateInfo(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().
                    getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String dataName = appInfo.metaData.getString("updateInfo");
        return dataName;
    }
}
