package com.library.widget.defaultimple;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.widget.R;
import com.library.widget.baseview.FooterView;

/**
 * Created by zhangyu on 2016/11/18.
 */

public class DefaultFooterView extends FooterView {

    public TextView hintText;
    private ProgressBar progressBar;

    public DefaultFooterView(Context context) {
        super(context);
    }

    public DefaultFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutView() {
        return R.layout.pull_to_refresh_footer;
    }

    @Override
    public void initView() {
        hintText = (TextView) findViewById(R.id.ptrf_tv);
        progressBar = (ProgressBar) findViewById(R.id.ptrf_progress_bar);
    }

    @Override
    public RelativeLayout getRootContainer() {
        return (RelativeLayout) findViewById(R.id.footer_container);
    }

    @Override
    public void setLoadingViewState() {
        progressBar.setVisibility(VISIBLE);
        hintText.setText("正在加载..");
    }

    @Override
    public void setPullingViewState() {
        progressBar.setVisibility(INVISIBLE);
        hintText.setText("上拉加载更多");
    }
}
