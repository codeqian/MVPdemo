//package com.shouchuanghui.customerviewmodule.adapter;
//
//import android.support.annotation.Nullable;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.shouchuanghui.commonmodule.bean.UploadImage;
//import com.shouchuanghui.customerviewmodule.R;
//
//import java.util.List;
//
///**
//* @description 图片选择adapter
//* @author chenbin
//* @date 2018/11/19 13:28
//* @version v3.2.0
//*/
//public class ImageSelectAdapter extends BaseDataBindingMultiItemQuickAdapter<UploadImage> {
//    private int size;
//    private ViewClickListener onViewClickListener;
//
//    public ImageSelectAdapter(@Nullable List data, int size, ViewClickListener onViewClickListener) {
//        super(data);
//        addItemType(0, R.layout.item_select_image);
//        addItemType(1, R.layout.item_add_image);
//        this.size = size;
//        this.onViewClickListener = onViewClickListener;
//    }
//
//    @Override
//    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, UploadImage item, final int position) {
//        if (item.getItemType() == 0) {//图片
//            ItemSelectImageBinding binding = (ItemSelectImageBinding) helper.getBinding();
//            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
//            layoutParams.width = size;
//            layoutParams.height = size;
//            GlideUtil.load(binding.ivImage, item.getUrl());
//            binding.rlImage.setLayoutParams(layoutParams);
//            binding.btnRemove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //删除图片
//                    if (onViewClickListener != null) {
//                        onViewClickListener.onDetele(getData().get(position));
//                    }
//                    getData().remove(position);
//                    if (getData().size() == 0 || getData().get(getData().size() - 1).getItemType() == 0) {
//                        UploadImage uploadImage = new UploadImage();
//                        uploadImage.setType(1);
//                        getData().add(uploadImage);
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//        } else if (item.getItemType() == 1){//添加图片按钮
//            ItemAddImageBinding binding = (ItemAddImageBinding) helper.getBinding();
//            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
//            layoutParams.width = size;
//            layoutParams.height = size;
//            binding.ivAddImage.setLayoutParams(layoutParams);
//            binding.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onViewClickListener != null) {
//                        onViewClickListener.onAddViewClick();
//                    }
//                }
//            });
//
//        }
//    }
//
//    public int getImageSize() {
//        if (getData().get(getData().size() - 1).getItemType() == 1)
//            return getData().size() - 1;
//        else
//            return getData().size();
//    }
//
//    public interface ViewClickListener {
//        void onAddViewClick();
//        void onDetele(UploadImage uploadImage);
//    }
//}
