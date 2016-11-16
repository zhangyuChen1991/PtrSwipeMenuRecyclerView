package com.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by zhangyu on 2016/11/8.
 */
public class SwipeMenuLayout extends FrameLayout {
    private static final String TAG = "SwipeMenuLayout";

    private View contentView;
    private LinearLayout menuView;
    private Context context;
    private Scroller mScroller;

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwipeMenuLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public SwipeMenuLayout(Context context, View contentView, LinearLayout menuView) {
        super(context);
        this.contentView = contentView;
        this.menuView = menuView;
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        mScroller = new Scroller(context);


        LayoutParams contentParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (null != contentView) {
            addView(contentView);
            contentView.setLayoutParams(contentParams);
        }
        if (null != menuView) {
            addView(menuView);
            menuView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        setLayoutParams(contentParams);


    }

    private float preTouchX, preTouchY;
    private float nowTouchX, nowTouchY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //点击停止滚动，或者开始下一轮滚动的数据设置
                if (!mScroller.computeScrollOffset()) { // 滚动已经结束

                    Log.i(TAG, "ACTION_DOWN..");
                    preTouchX = event.getX();
                    preTouchY = event.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.computeScrollOffset()) { // 滚动已结束
                    nowTouchX = event.getX();
                    nowTouchY = event.getY();

                    if (null == menuView)
                        return super.onTouchEvent(event);

                    //当从0点往左滑动时，currx为整数，滑得越远，数越大
                    int currX = mScroller.getCurrX();
                    float distanceX = nowTouchX - preTouchX;
                    if (currX < menuView.getWidth() && currX > 0) {//菜单已被拉出，往左右都可滑
                        return doScroll(distanceX);
                    }

                    //菜单已被完全拉出，往右可滑
                    if (currX >= menuView.getWidth() && distanceX > 0) {
                        return doScroll(distanceX);
                    }

                    //菜单未被拉出，往左可滑
                    if (currX <= 0 && distanceX < 0) {
                        return doScroll(distanceX);
                    }

                    preTouchX = nowTouchX;
                    preTouchY = nowTouchY;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP..");
                if (null != menuView)
                    startAutoScroll();
                break;
        }
        return super.onTouchEvent(event);
    }


    private void startAutoScroll() {
        int scrollX = getScrollX();
        Log.i(TAG, "startAutoScroll..scrollX = " + scrollX);
        if (scrollX >= menuView.getWidth() * 0.5) {
            smoothOpenMenu();
        } else {
            smoothCloseMenu();
        }
    }

    private void smoothOpenMenu() {
        smoothScrollTo(menuView.getWidth(), 0);
    }

    public void smoothCloseMenu() {
        smoothScrollTo(0, 0);
    }

    private boolean doScroll(float distanceX) {
        setScrollDistance(distanceX);
        preTouchX = nowTouchX;
        return true;
    }

    /**
     * 设置当次滚动的距离
     *
     * @param distanceX
     */
    private void setScrollDistance(float distanceX) {
        float X = -getScrollX();
        mScroller.setFinalX((-(int) (X + distanceX)));
        invalidate();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "contentView.getWidth() = " + contentView.getWidth() + "contentView.getHeight() = " + contentView.getHeight());
        super.onLayout(changed, left, top, right, bottom);

        int contentViewWidth = contentView.getWidth();
        int contentViewHeight = contentView.getHeight();
        if (contentView != null && contentViewWidth != 0 && contentViewHeight != 0) {
            contentView.layout(0, 0, contentView.getWidth(), contentView.getHeight());

            if (menuView != null) {
                int menuViewWidth = menuView.getWidth();
                menuView.layout(contentViewWidth, 0, contentViewWidth + menuViewWidth, contentViewHeight);
            }
        }
    }

    /**
     * 获取menuView
     *
     * @return
     */
    public LinearLayout getMenuView() {
        return menuView;
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
