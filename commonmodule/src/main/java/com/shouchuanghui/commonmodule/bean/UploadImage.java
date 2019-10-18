package com.shouchuanghui.commonmodule.bean;
import com.shouchuanghui.commonmodule.entity.MultiItemEntity;
/**
 * Create by huscarter@163.com on 7/7/17
 * <p>
 * 图片上传
 */

public class UploadImage extends Base implements MultiItemEntity {
    private String image_id;
    private String url;
    private String l_url;
    private String m_url;
    private String s_url;
    private int type;
    private String videoPath;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getL_url() {
        return l_url;
    }

    public void setL_url(String l_url) {
        this.l_url = l_url;
    }

    public String getM_url() {
        return m_url;
    }

    public void setM_url(String m_url) {
        this.m_url = m_url;
    }

    public String getS_url() {
        return s_url;
    }

    public void setS_url(String s_url) {
        this.s_url = s_url;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
