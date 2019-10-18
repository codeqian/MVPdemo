package com.shouchuanghui.commonmodule.viewmodel;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewFragmentVM extends BaseVM {

    protected List list = new ArrayList<>();
    protected BaseRecyclerViewFragmentCB baseCB;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public BaseRecyclerViewFragmentVM(BaseRecyclerViewFragmentCB callBack) {
        super(callBack);
        baseCB = callBack;
    }

    public abstract void initData(boolean isRefresh);

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
