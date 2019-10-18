package com.shouchuanghui.commonmodule.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @author huscarter@163.com.
 * @title 权限处理类
 * @description 本类是针对android6.0以上的版本, 实施的权限处理.
 * 尤其针对华为android6.0的系统,因为他的文件读写权限不能获取.
 * @date 2015/9/7.
 */
public class PermissionCompat {
    private final static String TAG = PermissionCompat.class.getSimpleName();
    private static PermissionCompat instance;

    /**
     * 检测文件的权限
     */
    private static String[] PERMISSIONS_EXTERNAL_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    /**
     * 存储
     */
    public final static int REQUEST_EXTERNAL_STORAGE = 99;

    /**
     * 检测相机的
     */
    public static String[] PERMISSION_CAMERA = {
            Manifest.permission.CAMERA};

    /**
     * 相机请求
     */
    public final static int REQUEST_CAMERA = 98;//华为的requestCode不能大于128

    /**
     * 检测图片选择的
     */
    public static String[] PERMISSION_GALLERY = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public static String[] PERMISSION_FILE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    /**
     * 相册选择
     */
    public final static int REQUEST_GALLERY = 97;

    /**
     * 检测拨打电话权限
     */
    private static String[] PERMISSION_CALL_PHONE = {
            Manifest.permission.CALL_PHONE,
    };

    /**
     * 拨打电话请求
     */
    private final static int REQUEST_CALL_PHONE = 10096;

    /**
     * 设备状态
     */
    public final static int REQUEST_PHONE_STATE = 100;

    private final static String[] PERMISSIONS_PHONE_STATE = {Manifest.permission.READ_PHONE_STATE};

    /**
     * 获取手机通讯录
     */
    private final static int REQUEST_READ_CONTACTS = 101;

    private final static String[] PERMISSIONS_READ_CONTACTS = {Manifest.permission.READ_CONTACTS};

    /**
     * 定位权限
     */
    private static String[] PERMISSION_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION};

    /**
     * 分享相关权限
     */
    String[] ABOUT_SHARE = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private PermissionCompat() {
        //
    }

    public static PermissionCompat getInstance() {
        if (instance == null) {
            synchronized (PermissionCompat.class) {
                if (instance == null) {
                    instance = new PermissionCompat();
                }
            }
        }
        return instance;
    }

    public boolean checkCameraPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检测文件读写
     */
    public boolean checkFilePermission(Activity activity) {
        return checkPermission(activity, PERMISSION_FILE, REQUEST_EXTERNAL_STORAGE);
    }

    /**
     * 检测相册
     */
    public boolean checkGalleryPermission(Activity activity) {
        return checkPermission(activity, PERMISSION_GALLERY, REQUEST_GALLERY);
    }

    /**
     * 检测电话
     */
    public boolean checkCallPhonePermission(Activity activity) {
        return checkPermission(activity, PERMISSION_CALL_PHONE, REQUEST_CALL_PHONE);
    }

    /**
     * 检测文件读写
     */
    public boolean checkStoragePermission(Activity activity) {
        return checkPermission(activity, PERMISSIONS_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
    }

    /**
     * 检测手机状态权限
     * @param activity
     * @return
     */
    public boolean checkPhoneStatePermission(Activity activity) {
        return checkPermission(activity, PERMISSIONS_PHONE_STATE,REQUEST_PHONE_STATE);
    }

    /**
     * 检查手机通讯录权限
     * @param activity
     * @return
     */
    public boolean checkReadContactsPermission(Activity activity) {
        return checkPermission(activity, PERMISSIONS_READ_CONTACTS,REQUEST_READ_CONTACTS);
    }

    /**
     * 检测定位
     */
    public boolean checkLocationPermission(Activity activity) {
        return checkPermission(activity, PERMISSION_LOCATION, REQUEST_LOCATION);
    }

    /**
     * 定位权限
     */
    public final static int REQUEST_LOCATION = 10097;

    /**
     * 定位权限
     */
    public final static int REQUEST_SOCIAL_SHARE = 10097;

    /**
     * 分享相关权限
     * @param activity
     * @return
     */
    public boolean checkAboutSharePermission(Activity activity) {
        return checkPermission(activity, ABOUT_SHARE, REQUEST_SOCIAL_SHARE);
    }

    /**
     * 最底层的全新检测方法
     *
     * @param activity
     * @param permission   需要检测的权限数组
     * @param request_code 请求的request code 自定义
     * @return
     */
    public boolean checkPermission(Activity activity, String[] permission, int request_code) {
        for (String str : permission) {
            int check = ContextCompat.checkSelfPermission(activity, str);
            boolean is_granted = (check == PackageManager.PERMISSION_GRANTED);
            if (!is_granted) { //
                ActivityCompat.requestPermissions(
                        activity,
                        permission,
                        request_code
                );
                return false;
            }
        }
        return true;
    }

}
