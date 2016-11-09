package com.library.widget.interfaces;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zhangyu on 2016/11/9.
 */
public interface PtrListView {
    /**
     * 设置菜单view
     * @param menuView 菜单布局
     * @return true 设置成功; false 设置失败
     */
    boolean setMenuView(LinearLayout menuView);

    /**
     * 设置内容view
     * @param ContentView 内容布局
     * @return 设置成功; false 设置失败
     */
    boolean setContentView(View ContentView);

    /**
     * 设置菜单点击监听
     * @param menuClickListener 点击监听回调
     */
    void setOnMenuClickListener(OnMenuClickListener menuClickListener);

    /**
     * 下拉刷新触发执行方法
     */
    void onRefresh();

    /**
     * 加载更多触发执行方法
     */
    void onLoadMore();
}
