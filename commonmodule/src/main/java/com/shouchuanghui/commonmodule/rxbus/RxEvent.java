package com.shouchuanghui.commonmodule.rxbus;

/**
 * @author huscarter@163.com
 * @title RxBus的事件类
 * @description RxEvent为基本事件, 可以自定义其他特定的事件比如OrderEvent.
 * <p>
 * 每种事件都有一个标示type,每种事件可以定义事件行为,比如订单事件(OrderEvent)的改价行为(CHANGE_PRICE).
 * <p>
 * 每种的事件的标示(type)相差100, 意味着每种事件里可以有99种不同的行为,因此也可以通过行为推断出它属于哪种事件.
 * <p>
 * 举例:订单事件OrderEvent的type为100,订单的改价和付款行为标示为101和102,最大的行为标示为199.
 * @date 9/30/16
 */
public class RxEvent {
    /**
     * 事件的标示
     */
    public int type = 0;
    /**
     * 事件的发送者
     */
    public Object sender = null;

    public RxEvent() {
        //
    }

    public RxEvent(int type) {
        this.type = type;
    }

    public RxEvent(int type, Object sender) {
        this.type = type;
        this.sender = sender;
    }
    /**
     * 登录,注册事件(Login and register of user==LRU)
     */
    public static class LoginEvent extends RxEvent {
        public static final int VALUE = 100;
        /**
         * 登录成功
         */
        public static final int LOGIN_SUCCESS = VALUE + 1;
        public static final int RE_LOGIN = VALUE + 2;
        public static final int LOGOUT = VALUE + 3;
        public static final int LOGIN_WEXIN = VALUE +4;
        public static final int BIND_WEXIN = VALUE +5;

        public LoginEvent() {
            super(VALUE);
        }
    }

    public static class BbsEvent extends RxEvent {
        public static final int VALUE = 200;

        public static final int COMMENT_INSERT = VALUE + 1;     //添加评论
        public static final int BBS_INSERT = VALUE + 2;     //添加帖子
        public static final int INDEX_SWITCH = VALUE + 3;   //跳转信息中心
        public static final int BBS_DELETE = VALUE + 4;     //帖子删除
        public static final int BBS_BLACK = VALUE + 5;     //拉黑
        public static final int HEADLINE_COMMENT_INSERT = VALUE + 6;     //宜兴头条点赞
    }

    public static class PersonInfoEvent extends RxEvent {
        public static final int VALUE = 300;

        public static final int ADDRESS_INSERT = VALUE + 1;     //添加地址

        public static final int ADDRESS_SAVE = VALUE + 2;     //编辑地址

        public static final int COUPON_INSERT = VALUE + 3;     //添加优惠券

        public static final int COUPON_SAVE = VALUE + 4;     //编辑优惠券

        public static final int COUPON_DELTE = VALUE + 5;     //删除优惠券

        public static final int IDENTIFY_CHANGE = VALUE + 6;    //切换身份

        public static final int ADDRESS_DELETE = VALUE + 7;    //删除地址

        public static final int SYSTEM_NOTIFICATION_READED = VALUE + 8;    //系统消息已读

        public static final int ORDER_NOTIFICATION_READED = VALUE + 9;    //订单息已读
    }

    public static class OrderEvent extends RxEvent {
        public static final int VALUE = 400;

        public static final int SELECT_ADDRESS = VALUE + 1;

        public static final int SHOP_CAR_CONFIRM_ORDER = VALUE + 2;

        public static final int CONFIRM_ORDER = VALUE + 3;

        public static final int ORDER_CANCEL = VALUE + 4;//取消订单
        public static final int ORDER_SEND = VALUE + 5;//发货
        public static final int ORDER_PAY = VALUE + 6;//支付
        public static final int ORDER_OFFLINE_PAY = VALUE + 7;//到店付
        public static final int ORDER_CHECK_OFFLINE_PAY = VALUE + 8;//确认到店付
        public static final int ORDER_COMMENT = VALUE + 9;//评价
        public static final int ORDER_CHECK_RECEIVER = VALUE + 10;//确认收货

        public static final int ALIPAY_FINISH = VALUE + 11;       //支付宝支付完成
        public static final int WECHATPAY_FINISH = VALUE + 12;       //微信支付完成

        public static final int PAY_FINISH = VALUE + 13;       //支付完成

        public static final int SHOPCAR_DELETE = VALUE + 14;       //购物车删除
        public static final int SHOPCAR_ADD = VALUE + 15;       //购物车添加
    }

    public static class GoodsManageEvent extends RxEvent {
        public static final int VALUE = 500;

        public static final int REFRESH_GOODSMANAGE_NUMBER = VALUE + 1;
        public static final int REFRESH_GOODSMANAGE_AVAILABLE = VALUE + 2;
        public static final int REFRESH_GOODSMANAGE_UNAVAILABLE = VALUE + 3;
        public static final int REFRESH_GOODSMANAGE_OPERATION = VALUE + 4;
    }

    public static class CouponEvent extends RxEvent {
        public static final int VALUE = 600;

        public static final int COUPON_RECIEVE = VALUE + 1;
        public static final int REFRESH_MY_COUPON_TICKET_NUM = VALUE + 2;
        public static final int REFRESH_MY_COUPON = VALUE + 3;
    }

    public static class MapEvent extends RxEvent {
        public static final int VALUE = 700;

        public static final int RE_DIRECT = VALUE + 1;
        public static final int CHECK_OPEN = VALUE + 2;
        public static final int GOT_AREA = VALUE + 3;
        public static final int GOT_COMMUNITY = VALUE + 4;
        public static final int CHANGE_COMMUNITY = VALUE + 5;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

    /**
     * 重写toString方法:展示为type值.
     *
     * @return
     */
    @Override
    public String toString() {
        return "event type:" + type;
    }

}
