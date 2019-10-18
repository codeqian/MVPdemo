package com.shouchuanghui.customerviewmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shouchuanghui.commonmodule.config.IntentConfig;
import com.shouchuanghui.commonmodule.util.DensityUtil;
import com.shouchuanghui.commonmodule.util.glide.GlideUtil;
import com.shouchuanghui.customerviewmodule.BaseQuickAdapter;
import com.shouchuanghui.customerviewmodule.BaseViewHolder;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.customerviewmodule.activity.ArticleDetailActivity;
import com.shouchuanghui.httpmodule.bean.bbs.BbsInfoListItem;

import java.util.List;

/**
 * 帖子item
 */
public class DailyHotNewsAdapter extends BaseQuickAdapter<BbsInfoListItem, BaseViewHolder> {
    private int currentPageIndex;

    public DailyHotNewsAdapter(@Nullable List<BbsInfoListItem> data) {
        super(R.layout.item_daily_hot_news, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final BbsInfoListItem item, final int position) {
        LinearLayout llImgs=viewHolder.getView(R.id.llImgs);
        ImageView ivPic= viewHolder.getView(R.id.ivPic);
        ImageView ivHead= viewHolder.getView(R.id.ivHead);
        GlideUtil.load(ivHead,item.getAvatar());
        viewHolder.setText(R.id.nickName,item.getNickname())
                .setText(R.id.tvTitle,item.getTitle())
                .setText(R.id.updateTime,item.getCreatetime())
                .setText(R.id.cateName,"#"+item.getCate_name()+"#")
                .setText(R.id.areaName,item.getCommunity())
                .setText(R.id.viewCount,item.getRead_sum())
                .setText(R.id.commitCount,item.getComment_sum());
        //不同数量的图片布局不同
        if (item.getImgs()== null || item.getImgs().size() == 0) {
            llImgs.removeAllViews();
            ivPic.setVisibility(View.GONE);
        } else if (item.getImgs().size() == 1) {
            llImgs.removeAllViews();
            ivPic.setVisibility(View.VISIBLE);
            GlideUtil.load(ivPic,item.getImgs().get(0));
        } else {
            ivPic.setVisibility(View.GONE);
            llImgs.removeAllViews();
            int index = 0;
            for (String img:item.getImgs()) {
                if (index > 2)
                    break;
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext,111),DensityUtil.dpToPx(mContext,75));
                if (index != 2)
                    layoutParams.rightMargin = DensityUtil.dpToPx(mContext,5);
                imageView.setLayoutParams(layoutParams);
                GlideUtil.load(imageView,img);
                llImgs.addView(imageView);
                index++;
            }
        }
        viewHolder.getView(R.id.rootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(mContext, ArticleDetailActivity.class);
                bundle.putString(IntentConfig.SOURCE, "1");
                bundle.putString(IntentConfig.ID, item.getBbs_id());
                bundle.putInt(IntentConfig.POSITION, position);
                bundle.putInt(IntentConfig.PAGEVIEW_CURRENT_INDEX, currentPageIndex);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }
}
