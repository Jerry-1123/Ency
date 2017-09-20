package com.xxx.ency.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络相关工具类
 * Created by xiarh on 2017/8/16.
 */

public class AppNetWorkUtil {

    //WIFI
    public static final String NETWORK_WIFI = "WIFI";
    //2G
    public static final String NETWORK_4G = "4G";
    //3G
    public static final String NETWORK_3G = "3G";
    //2G
    public static final String NETWORK_2G = "2G";
    //未知网络
    public static final String NETWORK_UNKNOWN = "UNKNOWN";
    //没有网络
    public static final String NETWORK_NO = "NO NETWORK";

    //类型：没有网络
    public static final int TYPE_NO = -1;
    //类型：移动网络
    public static final int TYPE_MOBILE = 0;
    //类型：WIFI
    public static final int TYPE_WIFI = 1;
    //类型：未知
    public static final int TYPE_UNKNOWN = 2;

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return true:连接 false:未连接
     */
    public static boolean isNetworkConnected(Context context) {
        //获取ConnectivityManager对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获得当前网络信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean flag = false;
        if (info != null) {
            flag = info.isConnected();
        }
        return flag;
    }

    /**
     * 获取当前网络状态类型
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        int type = TYPE_NO;
        if (info != null && info.isAvailable()) {
            //获取网络类型
            int netWorkType = info.getType();
            if (netWorkType == ConnectivityManager.TYPE_WIFI) {
                type = TYPE_WIFI;
            } else if (netWorkType == ConnectivityManager.TYPE_MOBILE) {
                type = TYPE_MOBILE;
            } else {
                type = TYPE_UNKNOWN;
            }
        }
        return type;
    }

    /**
     * 获取当前网络具体类型
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return 网络类型
     */
    public static String getNetworkSubType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        String subtype = NETWORK_NO;

        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                subtype = NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        subtype = getOperatorName(context).concat(NETWORK_2G);
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        subtype = getOperatorName(context).concat(NETWORK_3G);
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        subtype = getOperatorName(context).concat(NETWORK_4G);
                        break;
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            subtype = getOperatorName(context).concat(NETWORK_3G);
                        } else {
                            subtype = NETWORK_UNKNOWN;
                        }
                        break;
                }
            } else {
                subtype = NETWORK_UNKNOWN;
            }
        }
        return subtype;
    }

    /**
     * 获取运行商名称
     *
     * @param context
     * @return 运营商名称
     */
    private static String getOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telephonyManager.getSimOperator();
        String operatorName = "";
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                operatorName = "移动";
            } else if (operator.equals("46001")) {
                operatorName = "联通";
            } else if (operator.equals("46003")) {
                operatorName = "电信";
            }
        }
        return operatorName;
    }

    /**
     * 获取IP地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @param useIPv4 是否用IPv4
     * @return IP地址
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) {
                                return hostAddress;
                            }
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打开网络设置界面(兼容Android 3.0以上版本)
     *
     * @param context
     */
    public static void openNetworkSetting(Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT > 10) {
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(cm);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }
}