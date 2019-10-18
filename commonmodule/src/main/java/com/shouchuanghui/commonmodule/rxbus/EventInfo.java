package com.shouchuanghui.commonmodule.rxbus;

/**
 * @author:huscarter
 * @description: 事件实体类包括2个属性, 一个是位置信息(index), 一个内容改变的内容(content)
 * @date:2016/09/27
 */
public class EventInfo {
    private static final String TAG = EventInfo.class.getSimpleName();

    /**
     * 列表position位置
     */
    private int index = -99;

    /**
     * 消息携带的信息,比如改价的价格或者整个订单
     */
    private Object content = null;

    public EventInfo() {
        //
    }

    public EventInfo(int index) {
        this.index = index;
    }

    /**
     * @param content
     */
    public EventInfo(Object content) {
        this.content = content;
    }

    public EventInfo(int index, Object content) {
        this.index = index;
        this.content = content;
    }

    public static String getTAG() {
        return TAG;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("{")
                .append("position:").append(index)
                .append(",content:").append(content)
                .append("}").toString();
    }

}
