package com.shouchuanghui.commonmodule.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具
 */

public class KeyBoardUtil {
    private static final String TAG = KeyBoardUtil.class.getSimpleName();
    /**
    * @description 打开软键盘
    * @author nmssdmf
    * @date 2018/6/5 0005 14:42
    * @version v3.2.0
    */
    public static void openKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
    * @description
    * @date 2018/6/5 0005 14:44
    * @version v3.2.0
    */
    public static void closeKeyWords(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive() && activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void closeKeyWords(EditText mEditText){
        if (isSoftInputShow((Activity) mEditText.getContext())) {
            InputMethodManager imm = (InputMethodManager) mEditText.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }
}
