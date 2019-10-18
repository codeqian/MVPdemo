package com.shouchuanghui.commonmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.shouchuanghui.commonmodule.R;

/**
 * @author huscarter@163.com
 * @title 图片封装类
 * @description 项目里所有用到的ImageView都必须实用此类，方便第三方图片库更换
 * @date 11/19/17
 */

public class GlideImageView extends android.support.v7.widget.AppCompatImageView {
    private final static String TAG = GlideImageView.class.getSimpleName();

    public final static int RoundNone = 0;
    public final static int RoundTop = 1;
    public final static int RoundBottom = 2;
    public final static int RoundAll = 3;

    private int roundtype = 0;
    private float roundRadius = 0;

    public GlideImageView(Context context) {
        super(context);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.GlideImageView);
            roundtype = ta.getInt(R.styleable.GlideImageView_roundType, RoundNone);
            roundRadius = ta.getDimension(R.styleable.GlideImageView_roundRadius,0);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        JLog.d(TAG,"roundtype:"+roundtype+"    roundRadius:"+roundRadius);
        if (roundtype != RoundNone) {
            float[] rids;
            if (roundtype == RoundTop)
                rids = new float[]{roundRadius, roundRadius, roundRadius, roundRadius, 0.0f, 0.0f, 0.0f, 0.0f};
            else if (roundtype == RoundBottom)
                rids = new float[]{0.0f, 0.0f, 0.0f, 0.0f,roundRadius, roundRadius, roundRadius, roundRadius};
            else
                rids = new float[]{roundRadius, roundRadius, roundRadius, roundRadius,roundRadius, roundRadius, roundRadius, roundRadius};
            Path path = new Path();
            int w = this.getWidth();
            int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
            path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

    public int getRoundtype() {
        return roundtype;
    }

    public void setRoundtype(int roundtype) {
        this.roundtype = roundtype;
    }

    public float getRoundRadius() {
        return roundRadius;
    }

    public void setRoundRadius(float roundRadius) {
        this.roundRadius = roundRadius;
    }
}
