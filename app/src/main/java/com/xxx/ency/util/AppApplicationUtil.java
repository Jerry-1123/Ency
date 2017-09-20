package com.xxx.ency.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * APP应用信息工具类
 * Created by xiarh on 2017/8/15.
 */

public class AppApplicationUtil {

    /**
     * 获取APP名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName;
        try {
            PackageManager p = context.getPackageManager();
            PackageInfo info = p.getPackageInfo(context.getPackageName(), 0);
            int labelRes = info.applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            appName = "unKnown";
        }
        return appName;
    }

    /**
     * 获取APP版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取APP版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取本地应用大小
     *
     * @param context
     * @return 大小(字节单位) 1MB = 1024KB  1KB = 1024Byte
     */
    public static long getAppSize(Context context) {
        long appSize = 0;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            appSize = new File(applicationInfo.sourceDir).length();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appSize;
    }

    /**
     * 安装应用(兼容Android 7.0及以上)
     * <p>
     * Android N步骤：
     * 1、在res文件夹下面创建xml文件夹，在xml文件夹中创建file_paths.xml文件
     * <paths>
     * <external-path
     * name="XXX名字"
     * path="XXX路径" />
     * </paths>
     * 2、在Manifest中添加
     * <provider
     * android:name="android.support.v4.content.FileProvider"
     * android:authorities="包名.fileProvider"
     * android:exported="false"
     * android:grantUriPermissions="true">
     * <meta-data
     * android:name="android.support.FILE_PROVIDER_PATHS"
     * android:resource="@xml/file_paths" />
     * </provider>
     *
     * @param context
     * @param apkFile
     */
    public static void installApk(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}