package com.shouchuanghui.customerviewmodule.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shouchuanghui.commonmodule.config.IntentConfig;
import com.shouchuanghui.commonmodule.util.DensityUtil;
import com.shouchuanghui.commonmodule.util.StringUtil;
import com.shouchuanghui.commonmodule.util.glide.GlideUtil;
import com.shouchuanghui.commonmodule.view.GlideImageView;
import com.shouchuanghui.customerviewmodule.BaseQuickAdapter;
import com.shouchuanghui.customerviewmodule.BaseViewHolder;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.customerviewmodule.activity.BeautyImageGalleryActivity;
import com.shouchuanghui.customerviewmodule.view.TagLayout;
import com.shouchuanghui.httpmodule.bean.common.MessageComment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* @description 评论详情内容adapter
* @author chenbin
* @date 2018/11/20 11:41
* @version v3.2.0
*/
public class CommentListContentAdapter extends BaseQuickAdapter<MessageComment, BaseViewHolder> {
    private String bbsId;

    public CommentListContentAdapter(@Nullable List<MessageComment> data, String bbsId) {
        super(R.layout.item_comment_content, data);
        this.bbsId = bbsId;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final MessageComment item, final int position) {
        MessageComment.ContentsBean contentsBean = item.getContents();
        TagLayout tl=viewHolder.getView(R.id.tl);
        TextView tvZan=viewHolder.getView(R.id.tvZan);
        TextView tvToComment=viewHolder.getView(R.id.tvToComment);
        GlideUtil.load((ImageView) viewHolder.getView(R.id.ivAvater),item.getAvatar());
        TextView tvName=viewHolder.getView(R.id.tvName);
        TextView tvQuote=viewHolder.getView(R.id.tvQuote);
        TextView tvComment=viewHolder.getView(R.id.tvComment);
        TextView tvCreateDate=viewHolder.getView(R.id.tvCreateDate);

        tvName.setText(item.getNickname());
        tvComment.setText(contentsBean.getNote());
        tvCreateDate.setText(item.getCreatetime());

        if(StringUtil.isEmpty(item.getQuote())){
            tvQuote.setVisibility(View.GONE);
        }else{
            tvQuote.setVisibility(View.VISIBLE);
            tvQuote.setText(item.getQuote());
        }

        if (contentsBean.getImgs() != null && contentsBean.getImgs().size() > 0) {
            tl.removeAllViews();
            tl.setVisibility(View.VISIBLE);
            final List<Uri> imageUrls = new ArrayList<>();
            for (MessageComment.ContentsBean.ImgsBean img : contentsBean.getImgs()) {
                imageUrls.add(Uri.parse(img.getM_url()));
            }
            int imageIndex = 0;
            for (MessageComment.ContentsBean.ImgsBean img : contentsBean.getImgs()){
                GlideImageView imageView = new GlideImageView(mContext);
                GlideUtil.load(imageView, img.getM_url());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext, 111),DensityUtil.dpToPx(mContext, 111));
                tl.addView(imageView, params);
                final int finalImageIndex = imageIndex;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt(BeautyImageGalleryActivity.PAGE_INDEX, finalImageIndex);
                        bundle.putSerializable(BeautyImageGalleryActivity.LIST_PATH_KEY, (Serializable) imageUrls);
                        intent.putExtras(bundle);
                        intent.setClass(mContext, BeautyImageGalleryActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                imageIndex++;
            }
        } else {
            tl.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(item.getGive_sum()) || "0".equals(item.getGive_sum())) {
            tvZan.setText("点赞");
        } else {
            tvZan.setText(item.getGive_sum());
        }
        tvToComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, ReplyActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(IntentConfig.BBS_ID, bbsId);
//                bundle.putString(IntentConfig.COMMENT_ID, item.getComment_id());
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
            }
        });
        tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (itemClickListener != null) {
//                    itemClickListener.onZanClick(item, position);
//                }
            }
        });
    }
}
