package com.shouchuanghui.commonmodule.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.LinearLayout;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class CommonUtils {

    /**
     * 判断list里都有不为空的值
     *
     * @param args
     * @param index 事先已知数组里有几个数值
     * @return
     */
    public static boolean isFull(List<String> args, int index) {
        for (int i = 0; i < index; i++) {
            if (isEmpty(args.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否为null
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
//        JLog.i(TAG, "isEmpty obj:" + obj);
        String str = obj + "";
        if (str.equals("") || str.equals("null")) {
            return true;
        }
        return false;
    }

    /*校验定位服务是否开启*/
    public static boolean CheckAPSService(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 拨打电话
     *
     * @param phone
     * @return
     * @description AlertDialog如果不引用support包，则是居中显示底部按钮
     */
    public static void callPhone(final Activity activity, final String phone) {

        if (!PermissionCompat.getInstance().checkCallPhonePermission(activity)) {
            ToastUtil.showToast( "没有获取到拨打电话权限");
            return;
        }
        if (isEmpty(phone)) {
            ToastUtil.showToast( "用户尚未填写联系方式");
        } else {
            Dialog dialog = new AlertDialog.Builder(activity).setMessage(phone).setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    activity.startActivity(intent);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

            dialog.getWindow().setLayout(DensityUtil.dpToPx(activity, 340), LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    public static String formatSold(String sold) {
        BigDecimal soldBigDecimal = new BigDecimal(sold);
        if (soldBigDecimal.compareTo(new BigDecimal(10000)) == 1 ) {
            soldBigDecimal = soldBigDecimal.divide(new BigDecimal(10000)).setScale(2);
        }
        return soldBigDecimal.toString();
    }

    /**
     * 获取app版本信息
     *
     * @return
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
