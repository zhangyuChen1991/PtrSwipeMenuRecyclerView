package com.cc.library.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.library.view.R;
import com.library.widget.baseview.HeaderView;

/**
 * Created by zhangyu on 2016/11/18.
 */

public class MHeaderView extends HeaderView {
    private TextView tv;
    private ProgressBar progressBar;
    public MHeaderView(Context context) {
        super(context);
    }

    public MHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView() {
        tv = (TextView) findViewById(R.id.mhv_tv);
        progressBar = (ProgressBar) findViewById(R.id.mhv_progress_bar);
    }

    @Override
    public RelativeLayout getRootContainer() {

        return (RelativeLayout) findViewById(R.id.mhv_container);
    }

    @Override
    public int getLayoutView() {
        return R.layout.m_header_view;
    }

    @Override
    public void setRefreshingViewState() {
        progressBar.setVisibility(VISIBLE);
        tv.setText("正在刷新..");
    }

    @Override
    public void setPullingViewState() {
        progressBar.setVisibility(INVISIBLE);
        tv.setText("下拉刷新");
    }

    @Override
    public void setReadyViewState() {
        progressBar.setVisibility(INVISIBLE);
        tv.setText("放开刷新");
    }
}
