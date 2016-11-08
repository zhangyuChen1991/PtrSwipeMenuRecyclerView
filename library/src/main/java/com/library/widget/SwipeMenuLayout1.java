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
 * Created by Administrator on 2016/11/8.
 */
public class SwipeMenuLayout1 extends FrameLayout {
    private static final String TAG = "SwipeMenuLayout1";

    private View contentView;
    private LinearLayout menuView;
    private Context context;
    private Scroller mScroller;
    public SwipeMenuLayout1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwipeMenuLayout1(Context context) {
        super(context);
        init(context, null);
    }

    public SwipeMenuLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        mScroller = new Scroller(context);
    }

    public SwipeMenuLayout1(Context context,View contentView,LinearLayout menuView){
        super(context);
        init(context,null);
        this.contentView = contentView;
        this.menuView = menuView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
        addView(contentView);
    }

    public void setMenuView(LinearLayout menuView) {
        this.menuView = menuView;
        addView(menuView);
    }

    private int preTouchX;
    private int nowTouchX;
    private int startX;
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
                break;
        }
        return true;
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


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(contentView != null){
            contentView.layout(0,0,contentView.getWidth(),contentView.getHeight());

            if(menuView != null){
                menuView.layout(contentView.getWidth(),0,contentView.getWidth()+menuView.getWidth(),contentView.getHeight());
            }
        }
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
