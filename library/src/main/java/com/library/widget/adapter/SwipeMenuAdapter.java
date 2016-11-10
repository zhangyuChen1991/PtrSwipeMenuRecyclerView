package com.library.widget.adapter;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.library.widget.SwipeMenuLayout;

/**
 * Created by zhangyu on 2016/11/9.
 */
public abstract class SwipeMenuAdapter extends RecyclerView.Adapter {
    private static final String TAG = "SwipeMenuAdapter";
    private LinearLayout menuView;
    private View contentView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder..");
        menuView = createMenuView(parent, viewType);
        contentView = createContentView(parent, viewType);
        SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(parent.getContext(), contentView, menuView);
        return onCreateThisViewHolder(swipeMenuLayout, viewType);
    }

    /**
     * 创建item内容的view布局
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract View createContentView(ViewGroup parent, int viewType);

    /**
     * 创建菜单view的布局
     * @return
     */
    protected abstract LinearLayout createMenuView(ViewGroup parent, int viewType);

    /**
     * 创建ViewHolder
     * @param contentView 已经在createContentView()中创建好，然后经过再次包裹了的itemview
     * @param viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder onCreateThisViewHolder(ViewGroup contentView, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();

}
