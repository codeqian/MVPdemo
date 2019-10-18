package com.shouchuanghui.customerviewmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shouchuanghui.commonmodule.bean.UploadImage;
import com.shouchuanghui.commonmodule.config.IntegerConfig;
import com.shouchuanghui.commonmodule.view.ImageSelectView;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.customerviewmodule.databinding.ActivityReplayBinding;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @author chenbin
 * @version v3.2.0
 * @description 回复
 * @date 2018/11/21 17:34
 */
public class ReplyActivity extends BaseTitleActivity {
    private final String TAG = ReplyActivity.class.getSimpleName();
    private ReplyVM vm;
    private ActivityReplayBinding binding;

    @Override
    public String setTitle() {
        return "写评论";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityReplayBinding) baseViewBinding;
        baseTitleBinding.tTitle.inflateMenu(R.menu.post);
        binding.isv.setOnUploadlistener(new ImageSelectView.OnImageUpLoadCompleteListener() {
            @Override
            public void onUpLoadComplete(String[] ids) {
                getContent(ids);
            }

            @Override
            public void onUpLoadFailed(Throwable e) {
            }

            @Override
            public void onAddImageClick(ImageSelectView imageSelectView) {

            }

            @Override
            public void deleteImage(UploadImage uploadImage) {

            }
        });
        setListener();
    }

    private void setListener() {
        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.post) {
                    if (!FastClickUtils.isFastClick()){
                        showLoaddingDialog();
                        uploadImg();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_replay;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ReplyVM(this);
        return vm;
    }

    /**
     * 图片上传
     */
    public void uploadImg() {
        if (binding.isv.getImgSize() > 0) {
            binding.isv.uploadImage();
        } else {
            //图片个数为0，直接获取文本内容,否则等待图片上传完毕后，再获取文本+图片内容
            getContent(null);
        }
    }

    public void getContent(String[] ids) {
        List<PostContent> postContents = new ArrayList<>();
        PostContent postContent = new PostContent();
        postContent.setNote(binding.etContent.getText().toString());
        if (ids != null && ids.length > 0)
            postContent.setImgs(ids);
        postContents.add(postContent);
        vm.comment(postContents);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntegerConfig.REQUEST_CODE_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {
                    binding.isv.addCameraImage();
                }
                break;
            case ImageGalleryActivity.IMAGE_SELECT_REQUEST:
                if (resultCode == RESULT_OK) {
                    binding.isv.addAlbumImage(data);
                }
                break;
            default:
                break;
        }
    }
}
