package com.cc.library.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.library.view.R;
import com.library.widget.baseview.FooterView;

/**
 * Created by zhangyu on 2016/11/18.
 */

public class MFooterView extends FooterView {

    private TextView tv;
    private ProgressBar progressBar;

    public MFooterView(Context context) {
        super(context);
    }

    public MFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutView() {
        return R.layout.m_footer_view;
    }

    @Override
    public void initView() {
        tv = (TextView) findViewById(R.id.mfv_tv);
        progressBar = (ProgressBar) findViewById(R.id.mfv_progress_bar);
    }

    @Override
    public RelativeLayout getRootContainer() {

        return (RelativeLayout) findViewById(R.id.mfv_container);
    }

    @Override
    public void setLoadingViewState() {
        progressBar.setVisibility(VISIBLE);
        tv.setText("正在加载..");
    }

    @Override
    public void setPullingViewState() {
        progressBar.setVisibility(INVISIBLE);
        tv.setText("上拉加载更多");
    }
}
