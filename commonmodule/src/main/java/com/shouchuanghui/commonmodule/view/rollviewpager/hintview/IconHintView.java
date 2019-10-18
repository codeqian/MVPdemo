package com.shouchuanghui.commonmodule.view.rollviewpager.hintview;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Mr.Jude on 2016/1/10.
 */
public class IconHintView extends ShapeHintView {
    private Drawable focusRes;
    private Drawable normalRes;


    public IconHintView(Context context, Drawable focusRes, Drawable normalRes) {
        super(context);
        this.focusRes = focusRes;
        this.normalRes = normalRes;
    }


    @Override
    public Drawable makeFocusDrawable() {
        Drawable drawable = focusRes;
        return drawable;
    }

    @Override
    public Drawable makeNormalDrawable() {
        Drawable drawable = normalRes;
        return drawable;
    }
}
