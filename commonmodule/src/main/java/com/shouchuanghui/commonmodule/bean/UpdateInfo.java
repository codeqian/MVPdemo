package com.shouchuanghui.commonmodule.bean;

import java.io.Serializable;

/**
 * 版本更新信息
 * Create by chenbin on 2018/12/22
 * <p>
 * <p>
 */
public class UpdateInfo implements Serializable {

    private String is_mandatory;            //版本号
    private String version;                 //APP大小
    private String filesize;                //是否强制更新  0=否 1=是
    private String update_content;          //内容
    private String is_notified;             //是否开启提醒
    private String down_url;                //下载URL
    private String is_online;               //0-未上线1-已上线

    public String getIs_mandatory() {
        return is_mandatory;
    }

    public void setIs_mandatory(String is_mandatory) {
        this.is_mandatory = is_mandatory;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getUpdate_content() {
        return update_content;
    }

    public void setUpdate_content(String update_content) {
        this.update_content = update_content;
    }

    public String getIs_notified() {
        return is_notified;
    }

    public void setIs_notified(String is_notified) {
        this.is_notified = is_notified;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }
}
