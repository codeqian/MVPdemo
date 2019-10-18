package com.shouchuanghui.commonmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.callback.WebViewCB;
import com.nmssdmf.commonlib.config.IntentConfig;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public class WebViewVM extends BaseVM{
    private WebViewCB cb;
    private String link;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public WebViewVM(WebViewCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getIntentData() {
        Bundle bundle = cb.getIntentData();
        if (bundle == null) return;
        link = bundle.getString(IntentConfig.LINK);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
