package com.library.widget.defaultimple;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.widget.R;
import com.library.widget.baseview.HeaderView;

/**
 * Created by zhangyu on 2016/11/18.
 */

public class DefaultHeaderView extends HeaderView {
    public TextView hintText;
    private ProgressBar progressBar;
    public DefaultHeaderView(Context context) {
        super(context);
    }

    public DefaultHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView() {
        hintText = (TextView) findViewById(R.id.ptrh_tv);
        progressBar = (ProgressBar) findViewById(R.id.ptrh_progress_bar);
    }

    @Override
    public RelativeLayout getRootContainer() {
        return (RelativeLayout) findViewById(R.id.header_container);
    }

    @Override
    public int getLayoutView() {
        return R.layout.pull_to_refresh_header;
    }

    @Override
    public void setRefreshingViewState() {
        progressBar.setVisibility(VISIBLE);
        hintText.setText("正在刷新..");
    }

    @Override
    public void setPullingViewState() {
        progressBar.setVisibility(INVISIBLE);
        hintText.setText("下拉刷新");
    }

    @Override
    public void setReadyViewState() {
        progressBar.setVisibility(INVISIBLE);
        hintText.setText("放开刷新");
    }

}
