package com.library.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

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
    private SwipeMenuAdapter<ViewHolder> adapter;
    //最新的touch Y坐标点
    private float touchCurrY;
    //是否正在刷新、是否正在加载更多
    private boolean refreshing = false, loading = false;
    //触发刷新的临界值
    private final float refreshHeight = 150f;
    //HeaderView最新的高度
    private int currHeaderHeight;
    //是否允许上拉加载更多
    private boolean pullLoadMoreEnable = true;
    //是否允许下拉刷新
    private boolean pullToRefreshEnable = true;
    private OnPullListener onPullListener;

    private static OnMenuClickListener onMenuClickListener;
    private ViewConfiguration mViewConfig;

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
        if (adapter instanceof SwipeMenuAdapter) {
            this.adapter = (SwipeMenuAdapter<ViewHolder>) adapter;
        } else {
            try {
                throw new InvalidClassException("所使用Adapter并非SwipeMenuAdapter的子类");
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }


    private void init(Context context) {
        mViewConfig = ViewConfiguration.get(context);
        this.context = context;

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
                            onMenuClickListener.onMenuClick(view, getAdapterPosition() - 1);//触发菜单点击事件，减去header的位置
                        }
                    }
                }
            }
        };
    }

    boolean thisTouchHadDeal = false;
    private int startX, nowTouchX, startY, nowTouchY;
    private SwipeMenuLayout newSwipeMenuLayout, oldSwipeMenuLayout;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isVerticalScroll = true;
                thisTouchHadDeal = false;
                startX = (int) ev.getX();
                startY = (int) ev.getY();

                int touchingPosition = getChildAdapterPosition(findChildViewUnder(startX, startY));
                if (touchingPosition != adapter.getItemCount() - 1 && touchingPosition != 0) {
                    ViewHolder viewHolder = (ViewHolder) findViewHolderForAdapterPosition(touchingPosition);

                    newSwipeMenuLayout = (SwipeMenuLayout) viewHolder.itemView;
                    if (oldSwipeMenuLayout != null && oldSwipeMenuLayout.getScrollX() != 0)//上一次被弹出的菜单回滚回原位
                        oldSwipeMenuLayout.smoothCloseMenu();
                    oldSwipeMenuLayout = newSwipeMenuLayout;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!thisTouchHadDeal) {
                    thisTouchHadDeal = true;
                    nowTouchX = (int) ev.getX();
                    nowTouchY = (int) ev.getY();
                    isVerticalScroll = !isHorizontalScroll(startX, startY, nowTouchX, nowTouchY);
                }
                if (isVerticalScroll) {   //如果是竖直方向滑动，拦截事件自己处理
                    return true;
                } else {    //横向滑动，不做响应事件，让子View处理事件
                    return false;
                }
            case MotionEvent.ACTION_UP:
                isVerticalScroll = true;
                thisTouchHadDeal = false;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                //TODO 理论上应该computeVerticalScrollOffset == 0 但是可能因为隐藏header的缘故，死活是150，改变header的初始高度也不影响这个值，why??
                if (computeVerticalScrollOffset() <= 150) {//recyclerviwe已滑动到顶部
                    if (refreshing || !pullToRefreshEnable)
                        return super.onTouchEvent(e);
                    touchCurrY = e.getY();

                    float distanceY = touchCurrY - startY;
                    if (distanceY > 0) {
                        dealPulling(distanceY);
                        return true;
                    }
                }

                if (!canScrollVertically(1)) {//已经滑到底部

                    if (loading || !pullLoadMoreEnable)
                        return super.onTouchEvent(e);

                    float distanceY = touchCurrY - startY;
                    if (distanceY < 0) {
                        startLoadMore();
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!refreshing) {
                    currHeaderHeight = adapter.getHeaderView().getHeight();
                    dealPullUp();
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 开始加载更多
     */
    private void startLoadMore() {
        loading = true;
        if (null != onPullListener)
            onPullListener.onLoadMore();

        adapter.getFooterView().setNowState(FooterView.STATE.LOADING);
    }

    public void onLoadMoreComplete() {
        loading = false;
        adapter.getFooterView().setNowState(FooterView.STATE.READY);
    }

    private void dealPullUp() {
        HeaderView headerView = adapter.getHeaderView();
        int height = headerView.getHeight();
        if (height < refreshHeight) {
            startScrollBackAnim();
        } else {
            scrollToReadyAndRefresh();
        }
    }

    /**
     * 回滚到刷新状态的位置并开始刷新
     */
    private void scrollToReadyAndRefresh() {
        if (null != onPullListener)
            onPullListener.onRefresh();
        refreshing = true;
        ValueAnimator animator = ObjectAnimator.ofFloat(1, refreshHeight / currHeaderHeight);
        animator.setDuration(200);
        animator.addUpdateListener(scrollToReadyUpdateListener);
        animator.addListener(scrollToReadyAnimatorListener);
        animator.start();
    }

    private ValueAnimator.AnimatorUpdateListener scrollToReadyUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float value = (float) valueAnimator.getAnimatedValue();
            adapter.getHeaderView().setViewHeight(currHeaderHeight * value);
        }
    };

    private Animator.AnimatorListener scrollToReadyAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            adapter.getHeaderView().setNowState(HeaderView.STATE.REFRESHING);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };


    /**
     * 开启回滚回原位的动画
     */
    private void startScrollBackAnim() {
        ValueAnimator animator = ObjectAnimator.ofFloat(1, 0);
        animator.setDuration(300);
        animator.addUpdateListener(scrollBackAnimatUpdateListener);
        animator.start();
    }

    private ValueAnimator.AnimatorUpdateListener scrollBackAnimatUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float value = (float) valueAnimator.getAnimatedValue();
            adapter.getHeaderView().setViewHeight(currHeaderHeight * value);
            if (value == 0) {
                refreshing = false;
            }
        }
    };

    /**
     * 处理下拉过程
     *
     * @param distanceY 手指下拉的距离
     */
    private void dealPulling(float distanceY) {

        float moveDistance = distanceY * 0.8f;
        adapter.getHeaderView().setViewHeight(moveDistance);
        if (moveDistance < refreshHeight) {
            adapter.getHeaderView().setNowState(HeaderView.STATE.PULLING);
        } else {
            adapter.getHeaderView().setNowState(HeaderView.STATE.READY);
        }
    }

    /**
     * 结束下拉刷新
     */
    public void onRefreshComplete() {
        currHeaderHeight = (int) refreshHeight;
        startScrollBackAnim();
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

        if (distanceX * Math.tan(2 * Math.PI / 360 * 30) > distanceY)//水平30度角度内算作水平滑动
            ret = true;
        return ret;
    }

    /**
     * 设置下拉刷新和上拉加载的事件监听
     *
     * @param onPullListener
     */
    public void setOnPullListener(OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
    }

    /**
     * 设置是否允许上拉加载更多
     *
     * @param enable true 允许上拉加载更多;false 不允许上拉加载更多
     */
    public void setPullLoadMoreEnable(boolean enable) {
        this.pullLoadMoreEnable = enable;

        if(null != adapter && !enable)
            adapter.setFooterViewEnable(false);
    }

    /**
     * 设置是否允许下拉刷新
     *
     * @param enable true 允许下拉刷新;false 不允许下拉刷新
     */
    public void setPullToRefreshEnable(boolean enable) {
        this.pullToRefreshEnable = enable;
    }

    public interface OnPullListener {
        /**
         * 下拉刷新触发执行方法
         */
        void onRefresh();

        /**
         * 加载更多触发执行方法
         */
        void onLoadMore();
    }

}
