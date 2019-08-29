package net.codepig.mvp.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 封装图片加载
 */
public class ImageUtil {
    private static Drawable drawable;
    private static Drawable errorDrawable;
    public static void imageConfig(Drawable _drawable) {
        drawable=_drawable;
    }

    public static void imageConfig(Drawable _drawable, Drawable _errorDrawable) {
        drawable=_drawable;
        errorDrawable=_errorDrawable;
    }

    public static void loadImageNoDefault(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .placeholder(drawable)
                .into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv, int w,int h) {
        Glide.with(context)
                .load(url)
                .placeholder(drawable)
                .override(w, h)
                .into(iv);
    }

    public static void clearImage(Context context,ImageView iv){
        Glide.with(context).clear(iv);
    }
}
