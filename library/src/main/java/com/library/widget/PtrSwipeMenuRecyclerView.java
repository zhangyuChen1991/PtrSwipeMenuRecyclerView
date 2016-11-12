package com.library.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.library.widget.adapter.SwipeMenuAdapter;
import com.library.widget.interfaces.OnMenuClickListener;

import java.io.InvalidClassException;

/**
 * Created by zhangyu on 2016/11/7.
 */
public class PtrSwipeMenuRecyclerView extends RecyclerView {
    private static final String TAG = "PSMRecyclerView";
    private boolean isVerticalScroll = true;
    private Context context;
    private Scroller mScroller;
    private SwipeMenuAdapter adapter;
    private HeaderView headerView;

    private static OnMenuClickListener onMenuClickListener;

    public PtrSwipeMenuRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public PtrSwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PtrSwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }


    private void init(Context context) {
        mScroller = new Scroller(context);
        this.context = context;
        if (getAdapter() instanceof SwipeMenuAdapter)
            adapter = (SwipeMenuAdapter) getAdapter();
        else {
            try {
                throw new InvalidClassException("所使用Adapter并非SwipeMenuAdapter的子类");
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * 重写ViewHolder
     * 使用PtrSwipeMenuRecyclerView，必须配合PtrSwipeMenuRecyclerView.ViewHolder，否则将会出错
     */
    public static abstract class ViewHolder extends RecyclerView.ViewHolder {
        SwipeMenuLayout swipeMenuLayout;
        LinearLayout menuView;

        public ViewHolder(View itemView) {
            super(itemView);
            swipeMenuLayout = (SwipeMenuLayout) itemView;
            menuView = swipeMenuLayout.getMenuView();
            setOnMenuClickListener();
        }

        private void setOnMenuClickListener() {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                menuView.getChildAt(i).setOnClickListener(onClickListener);
            }

        }

        private View.OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    if (view.getId() == menuView.getChildAt(i).getId()) {
                        if (null != onMenuClickListener) {
                            onMenuClickListener.onMenuClick(view, getAdapterPosition());//触发菜单点击事件
                        }
                    }
                }
            }
        };
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
     * 设置菜单点击监听
     *
     * @param onMenuClickListener
     */
    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
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
