package com.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by zhangyu on 2016/11/7.
 */

public class SwipeMenuLayout extends LinearLayout {
    private static final String TAG = "SwipeMenuLayout";
    private LinearLayout contentContainer, menuContainer;
    private Scroller mScroller;
    private Context context;
    private int preTouchX;
    private int nowTouchX;
    private int startX;
    private View menuView;


    public SwipeMenuLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        mScroller = new Scroller(context);
        contentContainer = (LinearLayout) findViewById(R.id.content_container);
        menuContainer = (LinearLayout) findViewById(R.id.menu_container);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //点击停止滚动，或者开始下一轮滚动的数据设置
                if (mScroller.computeScrollOffset()) { // 滚动还未结束
                    mScroller.abortAnimation();        //停止滚动
                } else {
                    Log.i(TAG, "ACTION_DOWN..");
                    startX = (int) event.getX();
                    preTouchX = startX;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.computeScrollOffset()) { // 滚动已结束
                    nowTouchX = (int) event.getX();

                    int distanceX = nowTouchX - preTouchX;
                    setScrollDistance(distanceX);
                    preTouchX = nowTouchX;
                }
                break;
            case MotionEvent.ACTION_UP:
                startAutoScroll();
                break;
        }
        return true;
    }

    /**
     * 自动滚动
     */
    private void startAutoScroll() {
        if (menuView == null)
            return;
        int mWidth = menuView.getWidth();
        int currX = mScroller.getCurrX();

        if (Math.abs(currX) > mWidth * 0.5f) {
            smoothShowMenu();
        }else{
            scrollBack();
        }
    }

    private void smoothShowMenu() {
        //TODO 滚动到显示菜单的位置
    }

    /**
     * 添加菜单
     *
     * @param menuView 需要添加的菜单view
     * @return true:添加成功  false：添加失败
     */
    public boolean addMenu(View menuView) {
        if (this.menuView == null) {
            this.menuView = menuView;
            menuContainer.addView(menuView);
            return true;
        }
        return false;
    }


    /**
     * 回滚到起始状态
     */
    public void scrollBack() {
        smoothScrollTo(0, 0);
    }

    /**
     * 设置当次滚动的距离
     *
     * @param distanceX
     */
    private void setScrollDistance(int distanceX) {
        float X = -getScrollX();
        mScroller.setFinalX((-(int) (X + distanceX)));
        invalidate();
    }


    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }
}
