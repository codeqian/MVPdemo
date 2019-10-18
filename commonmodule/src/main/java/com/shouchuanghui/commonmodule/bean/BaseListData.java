package com.shouchuanghui.commonmodule.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by huscarter@163.com on 11/14/17
 * <p>
 * 基础的数据实体
 */
public final class BaseListData<T> extends Base {
    private String count;

    private List<T> data;

    public BaseListData() {
    }

    public BaseListData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data == null ? new ArrayList<T>() : data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
