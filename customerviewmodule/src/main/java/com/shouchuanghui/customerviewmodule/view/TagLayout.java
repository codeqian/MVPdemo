package com.shouchuanghui.customerviewmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.shouchuanghui.customerviewmodule.R;

import java.util.Hashtable;

/**
 * 标签group
 */

public class TagLayout extends LinearLayout {
    private final String TAG = TagLayout.class.getSimpleName();

    /**
     * 描绘时候的坐标
     */
    private int left, right, top, bottom;

    /**
     * 限制显示的行数
     */
    private int lineCount = 0;
    /**
     * childView之间的横向距离
     */
    private int horizontalSpace = 0;
    /**
     * childView之间的纵向距离
     */
    private int verticalSpace = 0;
    /**
     * 一行几个
     * 如果为0,表示为不规则排列
     * 如果大于0,则表示规则排列
     */
    private int horizontalCount = 0;

    /**
     * view为key,坐标为value
     */
    Hashtable<View, RectF> map = new Hashtable<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagLayout);
        horizontalCount = typedArray.getInteger(R.styleable.TagLayout_horizontal_count, 0);
        verticalSpace = (int) typedArray.getDimension(R.styleable.TagLayout_vertical_space, 0);
        horizontalSpace = (int) typedArray.getDimension(R.styleable.TagLayout_horizontal_space, 0);
        lineCount = typedArray.getInteger(R.styleable.TagLayout_line_count, 0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int superW = MeasureSpec.getSize(widthMeasureSpec);//layout的宽度
        final int superWithMode = MeasureSpec.getMode(widthMeasureSpec);//layout的宽度类型
        final int superH = MeasureSpec.getSize(heightMeasureSpec);//layout高度
        final int superHeightMode = MeasureSpec.getMode(heightMeasureSpec);//layout的高度类型

        final int childCount = getChildCount();//含有view的数量

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int maxRight = 0;

        //初始化描绘的左上角坐标
        left = paddingLeft;
        top = paddingTop;

        int line_count = 1;//表示行数
        int horizontal_count = 0;//表示一行个数
        boolean isFirstOne = true;//表示第一个view
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }
            /*
                (1) UPSPECIFIED :父容器对于子容器没有任何限制,子容器想要多大就多大.
                (2) EXACTLY父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.
                (3) AT_MOST子容器可以是声明大小内的任意大小.
             */
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

            final RectF rectF = new RectF();

            final int childW;
            final int childH = child.getMeasuredHeight();

            if (horizontalCount > 0) {//规则布局
                if (superWithMode == MeasureSpec.AT_MOST) {
                    childW = child.getMeasuredWidth();
                } else {
                    childW = (superW - paddingLeft - paddingRight - (horizontalCount - 1) * horizontalSpace) / horizontalCount;
                }
            } else {//不规则布局
                //测量childview的宽高
                childW = child.getMeasuredWidth();
            }
            if (isFirstOne) {
                isFirstOne = false;
                horizontal_count = 1;
            } else {
                right = left + childW;//如果不是第一个,则需要加上间距
                if (right + paddingRight > superW) {//超过容器宽度
                    if ((this.lineCount > 0 && line_count + 1 > this.lineCount)
                        /* || top + childH + verticalSpace + childH + paddingBottom > superH*/) {//注释掉的代码为高度最大为superH
                        //超过限定行数或者大于group的高度
                        break;
                    } else {//换行添加
                        line_count += 1;
                        left = paddingLeft;
                        top += childH + verticalSpace;
                        horizontal_count = 1;
                    }
                } else {//不换行添加
                    if (horizontalCount > 0 && horizontal_count == horizontalCount) {//超过规定个数,则换行
                        line_count += 1;
                        left = paddingLeft;
                        top += childH + verticalSpace;
                        horizontal_count = 1;
                    }
                    horizontal_count += 1;
                }
            }
            bottom = top + childH;
            right = left + childW;
            if (right > maxRight) {
                maxRight = right;
            }
            Log.d(TAG, "left = " + left + "  top = " + top + "  right= " + right + "  bottom = " + bottom);
            getRectF(rectF, left, top, right, bottom);
            map.put(child, rectF);
            left = right + horizontalSpace;
        }
        boolean isHeightWrapContent = superHeightMode == MeasureSpec.AT_MOST || superHeightMode == MeasureSpec.UNSPECIFIED;
        boolean isWidthtWrapContent = superHeightMode == MeasureSpec.AT_MOST || superHeightMode == MeasureSpec.UNSPECIFIED;
        if (isHeightWrapContent && isWidthtWrapContent) {//当宽度/高度设置为wrap_content时候
            setMeasuredDimension(maxRight + paddingRight, bottom + paddingBottom);
        } else if (isHeightWrapContent) {//当高度设置为wrap_content时候
            setMeasuredDimension(superW, bottom + paddingBottom);
        } else if (isWidthtWrapContent) {
            setMeasuredDimension(maxRight + paddingRight, superH);
        } else {
            setMeasuredDimension(superW, superH);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final RectF rectF = map.get(child);
            if (rectF != null) {
                child.layout((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            }
        }
    }

    private void getRectF(RectF rectF, int left, int top, int right, int bottom) {
        rectF.left = left;
        rectF.top = top;
        rectF.right = right;
        rectF.bottom = bottom;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getHorizontalSpace() {
        return horizontalSpace;
    }

    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    public int getVerticalSpace() {
        return verticalSpace;
    }

    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    public int getHorizontalCount() {
        return horizontalCount;
    }

    public void setHorizontalCount(int horizontalCount) {
        this.horizontalCount = horizontalCount;
    }
}

