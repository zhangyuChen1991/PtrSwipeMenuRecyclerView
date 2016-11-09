package com.library.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhangyu on 2016/11/7.
 */

public class PtrSwipeMenuRecyclerView extends RecyclerView {
    private static final String TAG = "PSMRecyclerView";
    boolean isVerticalScroll = true;
    public PtrSwipeMenuRecyclerView(Context context) {
        super(context);
    }

    public PtrSwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public PtrSwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {

        super.setAdapter(adapter);
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    boolean thisTouchHadDeal = false;
    private int startX, nowTouchX, startY, nowTouchY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent.. isVerticalScroll = " + isVerticalScroll);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isVerticalScroll = true;
                thisTouchHadDeal = false;
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                Log.w(TAG, "ACTION_DOWN..  p1X = " + startX + "  ,p1Y = " + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v(TAG, "ACTION_MOVE.. isVerticalScroll = " + isVerticalScroll);
                if (!thisTouchHadDeal) {
                    thisTouchHadDeal = true;
                    nowTouchX = (int) ev.getX();
                    nowTouchY = (int) ev.getY();
                    isVerticalScroll = !isHorizontalScroll(startX, startY, nowTouchX, nowTouchY);
                    Log.w(TAG, "ACTION_MOVE..判断后 isVerticalScroll = " + isVerticalScroll);
                }
                if (isVerticalScroll)   //如果是竖直方向滑动，沿袭父类做法
                    return super.onInterceptTouchEvent(ev);
                else {    //横向滑动，不做响应事件，让子View处理事件
                    return false;
                }
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "ACTION_UP.. isVerticalScroll = " + isVerticalScroll);
                isVerticalScroll = true;
                thisTouchHadDeal = false;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 判断是横向滑动还是纵向滑动
     *
     * @param startX
     * @param startY
     * @param secondX
     * @param secondY
     * @return
     */

    private boolean isHorizontalScroll(int startX, int startY, int secondX, int secondY) {
        boolean ret = false;
        int distanceX = Math.abs(startX - secondX);
        int distanceY = Math.abs(startY - secondY);

        Log.e(TAG, "p1X = " + startX + "  ,p1Y = " + startY + "  ,secondX = " + secondX + "  ,secondY = " + secondY + "  ,distanceX = " + distanceX + "  ,distanceY = " + distanceY);
        if (distanceX > distanceY)
            ret = true;
        return ret;
    }
}
