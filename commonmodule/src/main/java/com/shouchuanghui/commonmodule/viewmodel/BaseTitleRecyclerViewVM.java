package com.shouchuanghui.commonmodule.viewmodel;

import com.nmssdmf.commonlib.callback.BaseTitleRecyclerViewCB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/10/19 0019.
 */

public abstract class BaseTitleRecyclerViewVM extends BaseVM {

    protected List list = new ArrayList<>();
    protected BaseTitleRecyclerViewCB baseTitleRecyclerViewCB;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public BaseTitleRecyclerViewVM(BaseTitleRecyclerViewCB callBack) {
        super(callBack);
        baseTitleRecyclerViewCB = callBack;
    }

    public abstract void initData(boolean isRefresh);

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
