package com.library.widget.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by zhangyu on 2016/11/18.
 */

public abstract class FooterView extends RelativeLayout{
    public RelativeLayout rootContainer;
    private STATE nowState;
    public enum STATE{
        LOADING,
        READY,
        HIND
    }

    public FooterView(Context context) {
        super(context);
        init(context,null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }


    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, getLayoutView(),this);
        rootContainer = getRootContainer();
        initView();
    }

    /**
     * 指定自定义的布局
     * @return
     */
    public abstract int getLayoutView();

    /**
     * 初始化布局
     */
    public abstract void  initView();

    /**
     * 获取自定义布局的根布局容器
     * @return
     */
    public abstract RelativeLayout getRootContainer();

    /**
     * 设置正在加载时的view状态
     */
    public abstract void setLoadingViewState();

    /**
     * 设置上拉时的view状态
     */
    public abstract void setPullingViewState();

    public void setNowState(FooterView.STATE nowState) {
        this.nowState = nowState;
        refreshView();
    }

    private void refreshView() {
        switch (nowState){
            case LOADING:
                rootContainer.setVisibility(View.VISIBLE);
                setLoadingViewState();
                break;
            case READY:
                rootContainer.setVisibility(View.VISIBLE);
                setPullingViewState();
                break;
            case HIND:
                rootContainer.setVisibility(View.GONE);
                break;
        }
    }
}
