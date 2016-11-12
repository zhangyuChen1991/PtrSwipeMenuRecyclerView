package com.cc.library.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cc.library.view.R;
import com.library.widget.PtrSwipeMenuRecyclerView;
import com.library.widget.adapter.SwipeMenuAdapter;

/**
 * Created by zhangyu on 2016/11/9.
 */
public class SwipeAdapter extends SwipeMenuAdapter {

    public class ViewHolder extends PtrSwipeMenuRecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    protected View createContentView(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view_for_test, parent, false);
        return contentView;
    }

    @Override
    protected LinearLayout createMenuView(ViewGroup parent, int viewType) {
        LinearLayout menuView = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_for_test, parent, false);
        return menuView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateThisViewHolder(ViewGroup contentView, int viewType) {
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getThisItemCount() {
        return 17;
    }

}
