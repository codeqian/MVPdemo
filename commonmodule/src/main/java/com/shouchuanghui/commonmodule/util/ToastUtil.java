package com.shouchuanghui.commonmodule.util;

import android.widget.Toast;

import com.shouchuanghui.commonmodule.app.MyApp;

public class ToastUtil {
    private static Toast toast;
    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}
