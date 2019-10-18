package com.shouchuanghui.commonmodule.bean;

/**
 * @author huscarter@163.com
 * @title 图片上传
 * @description
 * @date 7/7/17
 */

public class Upload extends Base {
    /**
     * 'status_code': '1',
     * 'message': '上传成功',
     * 'data': {
     * 'image_id': 'b1a0962431a38a61b13fbba3915542a0',//图片ID
     * 'url': 'http://jushiyun-line.oss-cn-hangzhou.aliyuncs.com/public/images/dc/33/5c/c971557adb5afbe0a120b226ece703f521.jpg',//原图
     * 'l_url': 'http://jushiyun-line.oss-cn-hangzhou.aliyuncs.com/public/images/dc/33/5c/c971557adb5afbe0a120b226ece703f521.jpg?x-oss-process=image/resize,w_800',//大图
     * 'm_url': 'http://jushiyun-line.oss-cn-hangzhou.aliyuncs.com/public/images/dc/33/5c/c971557adb5afbe0a120b226ece703f521.jpg?x-oss-process=image/resize,w_500',//中图
     * 's_url': 'http://jushiyun-line.oss-cn-hangzhou.aliyuncs.com/public/images/dc/33/5c/c971557adb5afbe0a120b226ece703f521.jpg?x-oss-process=image/resize,w_200'//小图
     * },
     * 'count': null
     */
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String image_id;
        private String url;
        private String l_url;
        private String m_url;
        private String s_url;

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
    }

}
