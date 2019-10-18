package com.shouchuanghui.commonmodule.view.LoadingDialog;

import android.content.Context;
import android.view.View;
import com.shouchuanghui.commonmodule.util.ContextUtil;


/**
 * @author chenxianfu_it@163.com
 * @title 这个一个加载控件
 * @description 这个类具备以下功能:
 * 1.点击back是否可取消
 * 2.设置背景颜色
 * 3.设置提示和说明
 * 4.设置弹出的自定义view
 * 5.设置透明度
 * 6.设置弹出时候的背景其余地方的光亮调节度0-1
 * 7.设置转圈的速率
 * 8.有4中样式
 * 8.1）SPIN_INDETERMINATE 这是一个圆的不断循环 不需要进行进度计算
 * 8.2）PIE_DETERMINATE 这是一个扇形扫描的加载 需要进行进度计算
 * 8.3）BAR_DETERMINATE 这是一个加载进度条的不断加载 需要进行进度计算
 * 8.4）ANNULAR_DETERMINATE 这是一个圆它的边渐渐填充加载  需要进行进度计算
 * @date 2016/8/11
 */
public class LoadingDialog {
    /**
     * 这是一个圆的不断循环 不需要进行进度计算
     */
    public static final int SPIN_INDETERMINATE = 0;
    /**
     * 这是一个扇形扫描的加载 需要进行进度计算
     */
    public static final int PIE_DETERMINATE = 1;
    /**
     * 这是一个加载进度条的不断加载 需要进行进度计算
     */
    public static final int BAR_DETERMINATE = 2;
    /**
     * 这是一个圆它的边渐渐填充加载  需要进行进度计算
     */
    public static final int ANNULAR_DETERMINATE = 3;

    public static KProgressHUD progress = null;

    private LoadingDialog() {
        //
    }

    /**
     * 关闭dialog
     */
    public static void dismiss() {
        if (progress != null && progress.isShowing() && ContextUtil.isContextExisted(progress.getmContext())){
            progress.dismiss();
            progress = null;
        }
    }

    /**
     * 用于常规的启动
     */
    public static void show(Context ctx, int resid) {
        show(ctx, SPIN_INDETERMINATE, ctx.getString(resid));
    }

    /**
     * 用于常规的启动
     */
    public static void show(Context ctx, String label) {
        show(ctx, SPIN_INDETERMINATE, label);
    }

    /**
     * 这是构造的一个实体类,他需要传入如下三个参数
     *
     * @param ctx    所在activity
     * @param status 希望的dialog类型
     * @param label  这个是加载时候提示语
     */
    public static void show(Context ctx, int status, String label) {
        show(ctx, status, label, null);
    }

    /**
     * @param ctx
     * @param status
     * @param label
     * @param detailsLabel 详细描述
     */
    public static void show(Context ctx, int status, String label, String detailsLabel) {
        if (progress != null) {
            return;
        }
        progress = KProgressHUD.create(ctx);
        progress.setLabel(label);
        if (detailsLabel != null && !detailsLabel.equals("")) {
            progress.setDetailsLabel(detailsLabel);
        }
        if (status == SPIN_INDETERMINATE) {
            progress.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        } else if (status == PIE_DETERMINATE) {
            progress.setStyle(KProgressHUD.Style.PIE_DETERMINATE);
        } else if (status == BAR_DETERMINATE) {
            progress.setStyle(KProgressHUD.Style.BAR_DETERMINATE);
        } else if (status == ANNULAR_DETERMINATE) {
            progress.setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE);
        }
        progress.show();
    }

    /**
     * 这是构造的一个实体类,他需要传入如下三个参数
     *
     * @param ctx   所在activity
     * @param view  自定义的view
     * @param label 这个是加载时候提示语
     */
    public static void show(Context ctx, View view, String label) {
        progress = KProgressHUD.create(ctx);
        progress.setLabel(label);
        progress.setCustomView(view);
        progress.show();
    }

    /**
     * 根据进度来加载dialog,当为100时加载完成
     *
     * @param totle 下载的总量
     * @param down  当前的量
     */
    public static void simulateProgressUpdate(int totle, int down) {
        int p = (down / totle) * 100;
        if (p == 100) {
            progress.dismiss();
        } else {
            progress.setProgress(p);
        }
    }


}
