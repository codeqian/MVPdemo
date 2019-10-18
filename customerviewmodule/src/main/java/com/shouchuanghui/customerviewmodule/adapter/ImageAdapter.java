package com.shouchuanghui.customerviewmodule.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.customerviewmodule.activity.BeautyImageSelectActivity;
import com.shouchuanghui.customerviewmodule.activity.ImageGalleryActivity;
import com.shouchuanghui.httpmodule.bean.common.ImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * product case adapter
 *
 * @author
 * @date 2015/7/9.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageVH> {
    private static final String TAG = "ImageAdapter";
    private List<ImageData> list = new ArrayList<>();
    private Activity context;
//    private ArrayList<String> selects = new ArrayList<>();
    private List<ImageData> selects = new ArrayList<>();
    private boolean show_select = false;
    private int count = 4;
    private RelativeLayout.LayoutParams layout_params;
    private int type = ImageGalleryActivity.TYPE_IMAGE;
    /**
     * 最后选择的位置
     */
    private int last_checked_position = -1;

    public ImageAdapter(Activity context, List<ImageData> list) {
        this.context = context;
        this.list = list;
    }

    public ImageAdapter(Activity context, List<ImageData> list, boolean show_select) {
        this.context = context;
        this.list = list;
        this.show_select = show_select;
    }

    public RelativeLayout.LayoutParams getLayout_params() {
        return this.layout_params;
    }

    public void setLayout_params(RelativeLayout.LayoutParams layout_params) {
        this.layout_params = layout_params;
    }

    @Override
    public ImageVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_gallery, viewGroup, false);
        ImageVH vh = new ImageVH(v);
        vh.sdv = (ImageView) v.findViewById(R.id.sdv);
        vh.iv = (ImageView) v.findViewById(R.id.iv);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ImageVH vh, final int i) {
        final ImageData obj = list.get(i);

        vh.sdv.setLayoutParams(layout_params);
        Uri uri = Uri.parse("file://" + obj.getPath());

        Glide.with(context)
                .load(uri)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.no_pic))
                .into(vh.sdv);

        if (show_select) {
            vh.iv.setVisibility(View.VISIBLE);
            if (obj.isSelected()) {
                vh.iv.setBackgroundResource(R.mipmap.agree);
            } else {
                vh.iv.setBackgroundResource(R.mipmap.un_agree);
            }
            vh.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSelects(i);
                }
            });
            vh.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startBeautyGallery(i);
                }
            });
        } else {
            vh.iv.setVisibility(View.GONE);
        }
    }

    private void startBeautyGallery(final int position) {
        Intent intent = new Intent(context, BeautyImageSelectActivity.class);
        Bundle bundle = new Bundle();
        Uri uri = Uri.parse("file://" + (type == ImageGalleryActivity.TYPE_IMAGE ? list.get(position).getPath() :list.get(position).getVideoPath() ));
        bundle.putParcelable(BeautyImageSelectActivity.PATH_KEY, uri);
        bundle.putInt(BeautyImageSelectActivity.CHECK_COUNT, getSelects().size());
        bundle.putInt(BeautyImageSelectActivity.POSITION, position);
        bundle.putInt(BeautyImageSelectActivity.MAX_COUNT, count);
        bundle.putBoolean(BeautyImageSelectActivity.CHECKED, list.get(position).isSelected());
        bundle.putInt("type", type);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, BeautyImageSelectActivity.BEAUTY_IMAGE_SELECT_REQUEST);
    }

    /**
     * 选择图片，count是总数，用于限制最多选几张。
     * 如果count为1时，需要自动替换之前选择的图片，不需要用户先取消之前的选择。
     *
     * @param position
     */
    private void changeSelects(int position) {
        if (!list.get(position).isSelected()) { // 当前组件没有选中
            if (count == 1) {
                if (selects.size() == 1) {
                    list.get(last_checked_position).setSelected(false);
                    selects.clear();
                    notifyItemChanged(last_checked_position);
                }
                selects.add(list.get(position));
                list.get(position).setSelected(true);
                notifyItemChanged(position);
                last_checked_position = position;
            } else {
                if (selects.size() < count) {
                    selects.add(list.get(position));
                    list.get(position).setSelected(true);
                    notifyItemChanged(position);
                    last_checked_position = position;
                } else {
                    Toast.makeText(context, "超过图片选择上限", Toast.LENGTH_SHORT).show();
                }
            }
        } else { // 当前组件选中了
            selects.remove(list.get(position).getPath());
            list.get(position).setSelected(false);
            notifyItemChanged(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<ImageData> getSelects() {
        return this.selects;
    }

    public void setSelect(int position, boolean b) {
        list.get(position).setSelected(b);

        if (b) {
            if (selects.size() < count) {
                selects.add(list.get(position));
                list.get(position).setSelected(true);
                last_checked_position = position;
            } else {
                Toast.makeText(context, "超过图片选择上限", Toast.LENGTH_SHORT).show();
            }
        } else {
            selects.remove(list.get(position).getPath());
            list.get(position).setSelected(false);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if (type == ImageGalleryActivity.TYPE_VIDEO) {
            count = 1;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public class ImageVH extends RecyclerView.ViewHolder {
        public ImageView sdv;
        public ImageView iv;
        public View root;

        public ImageVH(View itemView) {
            super(itemView);
            this.root = itemView;
        }
    }
}

