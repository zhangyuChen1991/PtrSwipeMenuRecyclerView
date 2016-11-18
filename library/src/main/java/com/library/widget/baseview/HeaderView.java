package com.library.widget.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by zhangyu on 2016/11/18.
 */

public abstract class HeaderView extends RelativeLayout {

    public RelativeLayout rootContainer;
    protected STATE nowState;
    public enum STATE {
        PULLING,
        READY,
        REFRESHING,
        HIND
    }

    public HeaderView(Context context) {
        super(context);
        init(context,null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, getLayoutView(), this);
        rootContainer = getRootContainer();
        initView();
        setNowState(HeaderView.STATE.HIND);
    }


    /**
     * 设置HeaderView的高度
     * @param height
     */
    public void setViewHeight(float height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rootContainer.getLayoutParams();
        params.height = (int) height;
        rootContainer.setLayoutParams(params);
        invalidate();
    }

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
     * 指定自定义的布局
     *
     * @return
     */
    public abstract int getLayoutView();

    /**
     * 设置正在加载时的view状态
     */
    public abstract void setRefreshingViewState();

    /**
     * 设置下拉时的view状态
     */
    public abstract void setPullingViewState();

    /**
     * 设置准备好刷新时状态
     */
    public abstract void setReadyViewState();

    public void setNowState(HeaderView.STATE nowState) {
        this.nowState = nowState;
        refreshView();
    }

    private void refreshView() {

        switch (nowState) {
            case PULLING:
                rootContainer.setVisibility(View.VISIBLE);
                setPullingViewState();
                break;
            case READY:
                rootContainer.setVisibility(View.VISIBLE);
                setReadyViewState();
                break;
            case REFRESHING:
                rootContainer.setVisibility(View.VISIBLE);
                setRefreshingViewState();
                break;
            case HIND:
                rootContainer.setVisibility(View.GONE);
                break;
        }
    }

}
