package com.shouchuanghui.commonmodule.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shouchuanghui.commonmodule.R;
import com.shouchuanghui.commonmodule.base.BaseActivity;

/**
 * Create by huscarter@163.com on 7/11/17
 * <p>
 * Actvity 具有标题栏的基类
 * <p>
 * 此类在BaseActivity的基础上增加了toolbar的设置
 */
public abstract class BaseTitleActivity extends BaseActivity {
    protected ActivityBaseTitleBinding baseTitleBinding;
    protected ViewDataBinding baseViewBinding;
    @Override
    protected void initAll(Bundle savedInstanceState) {
        baseTitleBinding = (ActivityBaseTitleBinding) baseBinding;
        baseViewBinding = DataBindingUtil.inflate(LayoutInflater.from(this), getContentViewId(), null, false);
        baseTitleBinding.llRootView.addView(baseViewBinding.getRoot(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initTitleView();

        initContent(savedInstanceState);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_base_title;
    }

    /**
    * @description 初始化视图内容
    * @author nmssdmf
    * @date 2018/10/16 0016 9:55
    * @params
    * @return
    */
    private void initTitleView(){
        setTitle(setTitle());
        baseTitleBinding.tTitle.setNavigationIcon(getDefaultNavigationIcon());
        setNavigationClickListener();
    }

    public void setTitle(String title) {
        baseTitleBinding.tvTitle.setText(title);
    }

    /**
    * @description 默认的NavigationIcon
    * @author nmssdmf
    * @date 2018/10/15 0015 17:59
    * @params
    * @return
    */
    private int getDefaultNavigationIcon(){
        return R.drawable.ic_arrow_back;
    }

    /**
    * @description 去除Navigation
    * @author nmssdmf
    * @date 2018/10/15 0015 17:59
    * @params
    * @return
    */
    protected void hideNavigation(){
        baseTitleBinding.tTitle.setNavigationIcon(null);
    }

    protected void setISlenderLineGone(){
        baseTitleBinding.iSlenderLine.setVisibility(View.GONE);
    }

    /**
    * @description 设置Navigation点击事件
    * @author nmssdmf
    * @date 2018/10/15 0015 18:00
    * @version v3.2.1
    * @params 
    * @return 
    */
    private void setNavigationClickListener() {
        baseTitleBinding.tTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
    * @description title设置
    * @author nmssdmf
    * @date 2018/10/16 0016 9:44
    * @params
    * @return
    */
    public abstract String setTitle();

    /**
     * 除了title之外的view初始化
     */
    public abstract void initContent(Bundle savedInstanceState);

    /**
    * @description 设置子类视图view
    * @author nmssdmf
    * @date 2018/10/16 0016 9:50
    */
    public abstract int getContentViewId();
}
