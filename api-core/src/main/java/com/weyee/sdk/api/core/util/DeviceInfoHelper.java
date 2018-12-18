package com.weyee.sdk.api.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;

import java.util.UUID;

/**
 * <p>设备信息帮助类。</p>
 *
 * @author moguangjian
 * @date 2017/12/5
 */
public class DeviceInfoHelper {
    /**
     * 唯衣宝app
     */
    public static final String APP_TYPE_SUPPLIER = "supplier";
    /**
     * pos
     */
    public static final String APP_TYPE_POS = "pos";
    /**
     * mpos
     */
    public static final String APP_TYPE_MPOS = "mpos";

    /**
     * 应用类型
     */
    private String appType = "未知";
    /**
     * 应用版本号
     */
    private String appVersion;

    /**
     * 手机系统版本
     */
    private String os;
    /**
     * sdk数字版本
     */
    private String sdkVersionNumber;

    /**
     * 手机品牌
     */
    private String brand;
    /**
     * 手机型号
     */
    private String deviceName;
    /**
     * 设备厂商
     */
    private String manufacturer;
    /**
     * IMEI码
     */
    private String IMEI;
    private String IMSI;
    /**
     * 硬件序列号(Android系统2.3版本以上)
     */
    private String serial;
    /**
     * 设备号
     */
    private String deviceNo;

    /**
     * 屏幕尺寸
     */
    private String screen;

    /**
     * 设备唯一UUID
     */
    private String uId;

    private static String deviceInfo;

    /**
     * @param appType @{@link #APP_TYPE_SUPPLIER}
     */
    private DeviceInfoHelper(Context context, String appType) {
        init(context, appType);
    }

    private void init(Context context, String appType) {
        this.appType = appType;
        appVersion = getAppVersionName(context);

        os = "Android" + Build.VERSION.RELEASE;
        sdkVersionNumber = String.valueOf(Build.VERSION.SDK_INT);

        brand = Build.BRAND;
        deviceNo = serial = Build.SERIAL;
        deviceName = Build.MODEL;
        manufacturer = Build.MANUFACTURER;

        try {
            IMEI = getDeviceUniqueCode(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (IMEI == null) {
            IMEI = "";
        }

        int mobileWidth = context.getResources().getDisplayMetrics().widthPixels;
        int mobileHeight = context.getResources().getDisplayMetrics().heightPixels;
        screen = mobileHeight + "x" + mobileWidth;

        uId = UUID.nameUUIDFromBytes(serial.getBytes()).toString();
    }

    /**
     * 获取设备信息json
     */
    public static String getDeviceInfo(Context context, String appType) {
        if (deviceInfo == null || deviceInfo.trim().length() == 0) {
            synchronized (DeviceInfoHelper.class) {
                DeviceInfoHelper deviceInfoHelper = new DeviceInfoHelper(context, appType);
                deviceInfo = new Gson().toJson(deviceInfoHelper);
            }
        }

        return deviceInfo;
    }


    public static String getDeviceUniqueCode(Context context) {
        String uniqueCode = getIMEI(context);
        if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
            uniqueCode = getAndroidId(context);
        }

        return uniqueCode;
    }


    /**
     * 获取手机IMEI
     * 注：
     * 非手机设备： 没有这个DEVICE_ID
     *
     * @return 获得手机deviceId
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imei;
    }

    /**
     * 获取安卓id
     * 注：
     * ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置
     * 它在Android <=2.1 or Android >=2.3的版本是可靠、稳定的，但在2.2的版本并不是100%可靠的
     * 在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = "";
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return androidId;
    }

    /**
     * 获取程序版本名(VersionName)
     *
     * @param context
     */
    public static String getAppVersionName(Context context) {
        String version = "获取版本号失败";
        if (context != null) {
            return version;
        }

        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return version;
        }

        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            if (pi == null) {
                return version;
            }

            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }
}
