package com.fivefivelike.mybaselibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 设备工具类
 */

public class DeviceUtils {

    /**
     * 得到屏幕的高
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    private static Context getContext() {
        return GlobleContext.getInstance().getApplicationContext();
    }

    /**
     * 得到屏幕的宽
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidths() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeights() {
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity() {
        return getContext().getResources().getDisplayMetrics().density;
    }


    /**
     * 获取手机唯一标识序列号
     *
     * @return
     */
    public static String getUniqueSerialNumber() {
        String phoneName = Build.MODEL;// Galaxy nexus 品牌类型
        String manuFacturer = Build.MANUFACTURER;//samsung 品牌
        Log.d("详细序列号", manuFacturer + "-" + phoneName + "-" + getSerialNumber());
        return manuFacturer + "-" + phoneName + "-" + getSerialNumber();
    }

    /**
     * IMEI （唯一标识序列号）
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI
     */
    public static String getIMEI() {
        String deviceId;
        if (isPhone()) {
            deviceId = getDeviceIdIMEI();
        } else {
            deviceId = getAndroidId();
        }
        return deviceId;
    }


    /**
     * 获取设备的IMEI
     *
     * @return
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getDeviceIdIMEI() {
        String id;
        //android.telephony.TelephonyManager
        TelephonyManager mTelephony = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            id = mTelephony.getDeviceId();
        } else {
            //android.provider.Settings;
            id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return id;
    }

    /**
     * 获取设备的软件版本号
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceSoftwareVersion() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getDeviceSoftwareVersion();
    }

    /**
     * 获取手机号
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getLine1Number() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 获取ISO标准的国家码，即国际长途区号
     *
     * @return
     */
    public static String getNetworkCountryIso() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getNetworkCountryIso();
    }

    /**
     * 获取设备的 MCC + MNC
     *
     * @return
     */
    public static String getNetworkOperator() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getNetworkOperator();
    }

    /**
     * 获取(当前已注册的用户)的名字
     *
     * @return
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName();
    }

    /**
     * 获取当前使用的网络类型
     *
     * @return
     */
    public static int getNetworkType() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getNetworkType();
    }

    /**
     * 获取手机类型
     *
     * @return
     */
    public static int getPhoneType() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getPhoneType();
    }

    /**
     * 获取SIM卡的国家码
     *
     * @return
     */
    public static String getSimCountryIso() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getSimCountryIso();
    }

    /**
     * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字
     *
     * @return
     */
    public static String getSimOperator() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getSimOperator();
    }

    /**
     * 获取服务商名称
     *
     * @return
     */
    public static String getSimOperatorName() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getSimOperatorName();
    }

    /**
     * 获取SIM卡的序列号
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getSimSerialNumber() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 获取SIM的状态信息
     *
     * @return
     */
    public static int getSimState() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getSimState();
    }

    /**
     * 获取唯一的用户ID
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getSubscriberId() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 获取语音邮件号码
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getVoiceMailNumber() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getVoiceMailNumber();
    }

    /**
     * 获取ANDROID ID
     *
     * @return
     */
    public static String getAndroidId() {
        return Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备型号，如MI2SC
     *
     * @return 设备型号
     */
    public static String getBuildBrandModel() {
        return Build.MODEL;// Galaxy nexus 品牌类型
    }

    public static String getBuildBrand() {
        return Build.BRAND;//google
    }

    /**
     * 获取设备厂商，如Xiaomi
     *
     * @return 设备厂商
     */
    public static String getBuildMANUFACTURER() {
        return Build.MANUFACTURER;// samsung 品牌
    }

    /**
     * 序列号
     *
     * @return
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * 获取App版本名称
     *
     * @return
     */
    public static String getAppVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 获取App版本号
     *
     * @return
     */
    public static int getAppVersionNo() {
        // 获取packagemanager的实例
        PackageManager packageManager = getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int version = packInfo.versionCode;
        return version;
    }

    /**
     * 检查权限
     *
     * @param permission 例如 Manifest.permission.READ_PHONE_STATE
     * @return
     */
    public static boolean checkPermission(String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(getContext(), permission);
                result = rest == PackageManager.PERMISSION_GRANTED;
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = getContext().getPackageManager();
            if (pm.checkPermission(permission, getContext().getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }



    /**
     * 判断设备是否是手机
     * 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }





    /**
     * 获取手机联系人
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_CONTACTS"/>}</p>
     * 上下文;
     *
     * @return 联系人链表
     */
    public static List<HashMap<String, String>> getAllContactInfo() {
        SystemClock.sleep(3000);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = getContext().getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"},
                null, null, null);
        // 5.解析cursor
        while (cursor.moveToNext()) {
            // 6.获取查询的数据
            String contact_id = cursor.getString(0);
            // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
            // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
            // 判断contact_id是否为空
            if (!TextUtils.isEmpty(contact_id)) {//null   ""
                // 7.根据contact_id查询view_data表中的数据
                // selection : 查询条件
                // selectionArgs :查询条件的参数
                // sortOrder : 排序
                // 空指针: 1.null.方法 2.参数为null
                Cursor c = resolver.query(date_uri, new String[]{"data1",
                                "mimetype"}, "raw_contact_id=?",
                        new String[]{contact_id}, null);
                HashMap<String, String> map = new HashMap<String, String>();
                // 8.解析c
                while (c.moveToNext()) {
                    // 9.获取数据
                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    // 10.根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        // 电话
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                        // 姓名
                        map.put("name", data1);
                    }
                }
                // 11.添加到集合中数据
                list.add(map);
                // 12.关闭cursor
                c.close();
            }
        }
        // 12.关闭cursor
        cursor.close();
        return list;
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


    /**
     * 获取手机短信并保存到xml中
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_SMS"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     * <p>
     * 上下文
     */
    public static void getAllSMS() {
        // 1.获取短信
        // 1.1获取内容解析者
        ContentResolver resolver = getContext().getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        // 2.备份短信
        // 2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2设置xml文件保存的路径
            // os : 保存的位置
            // encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            // 2.3设置头信息
            // standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                // 2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            // 2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
     * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
     *
     * @param activity activity
     */
    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     * 上下文
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape() {
        return getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     * <p>
     * 上下文
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait() {
        return getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * <p>需要用到上面获取状态栏高度getStatusBarHeight的方法</p>
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 获取DisplayMetrics对象
     * <p>
     * 应用程序上下文
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 判断是否锁屏
     * 上下文
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) getContext()
                .getSystemService(getContext().KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }


    /**
     * 设置安全窗口，禁用系统截屏。防止 App 中的一些界面被截屏，并显示在其他设备中造成信息泄漏。
     * （常见手机设备系统截屏操作方式为：同时按下电源键和音量键。）
     *
     * @param activity
     */
    public static void noScreenshots(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }


    /**
     * 是否为调试模式.
     * 环境
     */
    public static boolean isApkDebugging() {
        try {
            ApplicationInfo info = getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 是否在前台运行
     *
     * @return
     */
    @SuppressWarnings({"deprecation", "unused"})
    public static boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) getContext()
                .getSystemService(getContext().ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName)
                && currentPackageName.equals(getContext().getPackageName())) {
            return true;
        }

        return false;
    }


    /**
     * 拨打电话
     *
     * @param context
     * @param tel
     */

    public static void goTel(Activity context, String tel) {
        goTel(context, tel, false);
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param tel
     * @param isDirectly : 是否直接拨号
     */
    @SuppressLint("MissingPermission")
    public static void goTel(Activity context, String tel, boolean isDirectly) {
        if (StringUtil.isBlank(tel)) {
            ToastUtil.show("号码格式不对");
            return;
        }
        Intent intent = new Intent(!isDirectly ? Intent.ACTION_DIAL
                : Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 调用系统短信页面
     *
     * @param context
     * @param tel
     * @param message
     */
    public static void sendMsg(Activity context, String tel, String message) {
        if (StringUtil.isBlank(tel)) {
            ToastUtil.show("号码格式不对");
            return;
        }
        Uri uri = Uri.parse("smsto:" + tel);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        sendIntent.putExtra("sms_body", message);
        context.startActivity(sendIntent);
    }

    /**
     * 调用系统群发短信页面
     *
     * @param context
     * @param tel     (电话号码用;隔开)
     * @param message
     */
    public static void sendMsgGroup(final Activity context, String tel,
                                    String message) {
        if (StringUtil.isBlank(tel)) {
            ToastUtil.show("号码格式不对");
            return;
        }
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.putExtra("sms_body", message);
        sendIntent.putExtra("address", tel);
        context.startActivity(sendIntent);
    }

    /**
     * 获取签名
     *
     * @return
     */
    public static String getAppSign() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(sign.toByteArray()));
            String pubKey = cert.getPublicKey().toString();

            String signNumber = cert.getSerialNumber().toString(16);
            System.out.println("signName:" + cert.getSigAlgName());
            System.out.println("pubKey:" + pubKey);
            System.out.println("signNumber:" + signNumber);
            System.out.println("subjectDN:" + cert.getSubjectDN().toString());
            return signNumber;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 开启关闭震动
     *
     * @param isOpen
     */
    public static void openShake(boolean isOpen) {
        Vibrator vibrator = (Vibrator) getContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
        if (isOpen) {
            vibrator.vibrate(1 * 1000);
        } else {
            vibrator.cancel();
        }
    }

    /**
     * 开启关闭声音
     *
     * @param isOpen
     */
    public static void openVoice(boolean isOpen) {
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(getContext(),
                RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getContext(), uri);// 获取名字
        if (isOpen) {
            ringtone.play();
        } else {
            ringtone.stop();
        }

    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void copyChar(Activity context, String content) {
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(context.CLIPBOARD_SERVICE);
        cmb.setText(content);
        ToastUtil.show("复制成功");
    }


}
