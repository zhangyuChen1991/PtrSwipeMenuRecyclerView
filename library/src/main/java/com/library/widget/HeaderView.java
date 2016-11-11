package com.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 下拉刷新HeaderView
 * Created by zhangyu on 2016/11/2.
 */
public class HeaderView extends RelativeLayout {
    private static final String TAG = "HeaderView";
    private Context context;
    public TextView hintText;
    private ProgressBar progressBar;

    private STATE nowState = STATE.HIND;
    public enum STATE{
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
        init(context,attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        View.inflate(context, R.layout.pull_to_refresh_header,this);
        hintText = (TextView) findViewById(R.id.ptrh_tv);
        progressBar = (ProgressBar) findViewById(R.id.ptrh_progress_bar);
    }


    public void setNowState(STATE nowState) {
        this.nowState = nowState;
        refreshView();
    }

    private void refreshView() {
        switch (nowState){
            case PULLING:
                progressBar.setVisibility(INVISIBLE);
                hintText.setText("下拉刷新");
                break;
            case READY:
                progressBar.setVisibility(INVISIBLE);
                hintText.setText("放开刷新");
                break;
            case REFRESHING:
                progressBar.setVisibility(VISIBLE);
                hintText.setText("正在刷新..");
                break;
            case HIND:
                break;
        }
    }

}
