package com.shouchuanghui.commonmodule.util.glide;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.shouchuanghui.commonmodule.R;

/**
 * Create by huscarter@163.com on 12/5/17
 * <p>
 * glide工具类
 * <p>
 */

public class GlideUtil {
    public final static int centerCrop = 101;
    public final static int fitCenter = 102;
    public final static int centerInside = 103;

    public static void load(ImageView view, String url) {
        load(view, url, null, null, false, 0, centerCrop);
    }

    public static void load(ImageView view, String url, int centerMode) {
        load(view, url, null, null, false, 0, centerMode);
    }

    public static void load(ImageView view, String url, float radius) {
        load(view, url, null, null, false, radius, centerCrop);
    }

    public static void load(ImageView view, String url, Drawable holderDrawable) {
        load(view, url, holderDrawable, null, false, 0, centerCrop);
    }

    public static void load(ImageView view, String url, Drawable holderDrawable, float radius) {
        load(view, url, holderDrawable, null, false, radius, centerCrop);
    }

    public static void load(ImageView view, String url, Drawable holderDrawable, Drawable errorDrawable) {
        load(view, url, holderDrawable, errorDrawable, false, 0, centerCrop);
    }

    public static void load(ImageView view, String url, Drawable holderDrawable, Drawable errorDrawable, boolean roundAsCircle) {
        load(view, url, holderDrawable, errorDrawable, roundAsCircle, 0, centerCrop);
    }

    public static void load(ImageView view, String url, Drawable holderDrawable, Drawable errorDrawable, float radius) {
        load(view, url, holderDrawable, errorDrawable, false, radius, centerCrop);
    }

    public static void load(ImageView view, String url, Drawable holderDrawable, Drawable errorDrawable, boolean roundAsCircle, float radius, int centerMode) {
        try {
            RequestBuilder requestBuilder = Glide.with(view.getContext()).asDrawable().load(url);
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH);
            if (centerMode == centerInside) {
                requestOptions.centerInside();
            } else if (centerMode == centerCrop) {
                requestOptions.centerCrop();
            } else if (centerMode == fitCenter) {
                requestOptions.fitCenter();
            }

            errorDrawable = errorDrawable == null ? view.getContext().getResources().getDrawable(R.drawable.no_pic) : errorDrawable;
//        holderDrawable = holderDrawable == null ? view.getContext().getResources().getDrawable(R.drawable.no_pic_big) : holderDrawable;

            if (roundAsCircle) {
                // To set the src circle
                requestOptions = requestOptions.circleCrop();
                // To set the placeHolderImage circle
                if (holderDrawable != null) {
                    RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), ((BitmapDrawable) holderDrawable).getBitmap());
                    bitmapDrawable.setCircular(true);
                    requestOptions = requestOptions.placeholder(bitmapDrawable);
                }
                // To set the errorImage circle
                if (errorDrawable != null) {
                    RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), ((BitmapDrawable) errorDrawable).getBitmap());
                    bitmapDrawable.setCircular(true);
                    requestOptions = requestOptions.error(bitmapDrawable);
                }
            } else {
                if (radius > 0) {
                    // To set the src round
                    requestOptions = requestOptions.transform(new RoundTransform(view.getContext(), radius));
                    // To set the placeHolderImage round
                    if (holderDrawable != null) {
                        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), ((BitmapDrawable) holderDrawable).getBitmap());
                        bitmapDrawable.setCornerRadius(radius);
                        requestOptions = requestOptions.placeholder(bitmapDrawable);
                    }
                    // To set the errorImage round
                    if (errorDrawable != null) {
                        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), ((BitmapDrawable) errorDrawable).getBitmap());
                        bitmapDrawable.setCornerRadius(radius);
                        requestOptions = requestOptions.error(bitmapDrawable);
                    }
                } else {
                    if (holderDrawable != null) {
                        requestOptions = requestOptions.placeholder(holderDrawable);
                    }
                    if (errorDrawable != null) {
                        requestOptions = requestOptions.error(errorDrawable);
                    }
                }
            }

            requestBuilder.apply(requestOptions).into(view);
        } catch (Exception e) {

        }
    }
}
