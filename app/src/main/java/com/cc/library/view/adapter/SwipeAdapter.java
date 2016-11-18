package com.cc.library.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.library.view.R;
import com.library.widget.baseview.PtrSwipeMenuRecyclerView;
import com.library.widget.adapter.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by zhangyu on 2016/11/9.
 */
public class SwipeAdapter extends SwipeMenuAdapter<SwipeAdapter.ViewHolder> {
    public List<Integer> datas;
    public final int ONE_MENU = 0X1132, NO_MENU = 0X1133, TWO_MENU = 0X1134;

    public class ViewHolder extends PtrSwipeMenuRecyclerView.ViewHolder {
        public TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.content_tv);
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
        LinearLayout menuView1 = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.menu1_for_test, parent, false);

        if (viewType == ONE_MENU)
            return menuView1;
        else if (viewType == TWO_MENU)
            return menuView;
        else
            return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateThisViewHolder(ViewGroup contentView, int viewType) {
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindThisViewHolder(ViewHolder holder, int position) {
        if (null != datas && position < datas.size()){
            if (position % 3 == 0) {
                holder.tv.setText("我没有菜单 position:" + datas.get(position));
            } else if (position % 3 == 1) {
                holder.tv.setText("我有一个菜单 position:" + datas.get(position));
            } else
                holder.tv.setText("我有两个菜单 position:" + datas.get(position));
        }

    }

    @Override
    public int getThisItemViewType(int position) {
        if (position % 3 == 0) {
            return NO_MENU;
        } else if (position % 3 == 1) {
            return ONE_MENU;
        } else
            return TWO_MENU;
    }


    @Override
    public int getThisItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
    }
}
