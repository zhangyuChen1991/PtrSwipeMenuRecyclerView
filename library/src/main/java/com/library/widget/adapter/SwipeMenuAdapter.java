package com.library.widget.adapter;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.library.widget.FooterView;
import com.library.widget.HeaderView;
import com.library.widget.SwipeMenuLayout;

/**
 * Created by zhangyu on 2016/11/9.
 */
public abstract class SwipeMenuAdapter extends RecyclerView.Adapter {
    private static final String TAG = "SwipeMenuAdapter";
    private LinearLayout menuView;
    private View contentView;
    public static final int HeaderType = 0x1099, FooterType = 0x1101;

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderView headerView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerView = (HeaderView) itemView;
        }
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterView footerView;
        public FooterViewHolder(View itemView) {
            super(itemView);
            footerView = (FooterView) itemView;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder..");
        if(viewType == HeaderType)
            return new HeaderViewHolder(new HeaderView(parent.getContext()));
        if(viewType == FooterType)
            return new FooterViewHolder(new FooterView(parent.getContext()));

        menuView = createMenuView(parent, viewType);
        contentView = createContentView(parent, viewType);
        SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(parent.getContext(), contentView, menuView);
        return onCreateThisViewHolder(swipeMenuLayout, viewType);
    }

    /**
     * 创建item内容的view布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract View createContentView(ViewGroup parent, int viewType);

    /**
     * 创建菜单view的布局
     *
     * @return
     */
    protected abstract LinearLayout createMenuView(ViewGroup parent, int viewType);

    /**
     * 创建ViewHolder
     *
     * @param contentView 已经在createContentView()中创建好，然后经过再次包裹了侧滑菜单布局的itemview
     * @param viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder onCreateThisViewHolder(ViewGroup contentView, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount(){
        //添加Header和Footer的数目
        return getThisItemCount() + 2;
    }

    /**
     * 此方法执行RecyclerView.Adapter中getItemCount()的逻辑
     * @return
     */
    public abstract int getThisItemCount();

    /**
     * 重写此方法时请注意保留父类的方法，否则导致header计数混乱，下拉刷新出错
     * 使用position时注意减1(减去header的位置)
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HeaderType;
        if (position == getThisItemCount() + 1)
            return FooterType;
        return super.getItemViewType(position - 1);//减1去掉herder的位置
    }

}
