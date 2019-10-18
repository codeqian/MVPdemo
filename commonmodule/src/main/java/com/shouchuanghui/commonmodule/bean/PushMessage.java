package com.shouchuanghui.commonmodule.bean;

import java.io.Serializable;

public class PushMessage implements Serializable {
    private String title;                   //标题
    private String text;                    //内容
    private Payload payload;

    public static class Payload implements Serializable {
        private String source_type;             //点击跳转类型  all或者空=跳APP首页 order=跳订单详情  h5=跳网页 shop=跳商家店铺
        private String order_role;              //订单类型的跳转详情角色说明  0=默认值不需要任何操作   1=采购方跳转订单详情  2=供应商跳转订单详情。order_role 的值只针对于source_type值为order才使用
        private String source_jumps;            //跳转所需要的参数值，如商家ID、商品ID、订单号、H5跳转网址、帖子ID

        public String getSource_type() {
            return source_type;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }

        public String getOrder_role() {
            return order_role;
        }

        public void setOrder_role(String order_role) {
            this.order_role = order_role;
        }

        public String getSource_jumps() {
            return source_jumps;
        }

        public void setSource_jumps(String source_jumps) {
            this.source_jumps = source_jumps;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
