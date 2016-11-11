package com.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * FooterView
 * Created by zhangyu on 2016/11/2.
 */
public class FooterView extends RelativeLayout {
    private static final String TAG = "FooterView";
    private Context context;
    public TextView hintText;
    private ProgressBar progressBar;

    private STATE nowState = STATE.HIND;
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
        this.context = context;
        View.inflate(context, R.layout.pull_to_refresh_footer,this);
        hintText = (TextView) findViewById(R.id.ptrf_tv);
        progressBar = (ProgressBar) findViewById(R.id.ptrf_progress_bar);
    }


    public void setNowState(STATE nowState) {
        this.nowState = nowState;
        refreshView();
    }

    private void refreshView() {
        switch (nowState){
            case LOADING:
                setVisibility(View.VISIBLE);
                progressBar.setVisibility(INVISIBLE);
                hintText.setText("正在加载..");
                break;
            case READY:
                setVisibility(View.VISIBLE);
                progressBar.setVisibility(INVISIBLE);
                hintText.setText("上拉加载更多");
                break;
            case HIND:
                setVisibility(View.GONE);
                break;
        }
    }

}
