package com.library.widget.adapter;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.library.widget.FooterView;
import com.library.widget.HeaderView;
import com.library.widget.PtrSwipeMenuRecyclerView;
import com.library.widget.SwipeMenuLayout;

/**
 * Created by zhangyu on 2016/11/9.
 */
public abstract class SwipeMenuAdapter<V extends PtrSwipeMenuRecyclerView.ViewHolder> extends RecyclerView.Adapter {
    private static final String TAG = "SwipeMenuAdapter";
    private LinearLayout menuView;
    private View contentView;
    public static final int HeaderType = 0x1099, FooterType = 0x1101;
    private HeaderFooterViewHolder headerViewHolder, footerViewHolder;
    private boolean footerViewEnable = true;

    public class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder.. viewType = " + viewType + "  HT:" + HeaderType + "  FT:" + FooterType);
        if (viewType == HeaderType) {
            headerViewHolder = new HeaderFooterViewHolder(new HeaderView(parent.getContext()));
            return headerViewHolder;
        }
        if (viewType == FooterType) {
            footerViewHolder = new HeaderFooterViewHolder(new FooterView(parent.getContext()));
            if(!footerViewEnable) { //不允许上拉加载更多，隐藏FooterView
                FooterView footerView = (FooterView) footerViewHolder.itemView;
                footerView.setNowState(FooterView.STATE.HIND);
            }
            return footerViewHolder;
        }

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
     * 创建菜单view的布局(高度最好与contentView布局高度一致，否则可能引起布局混乱)
     * 如果不需要侧滑菜单，返回null
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0 && position != getItemCount() - 1)
            onBindThisViewHolder((V) holder, position);
    }

    public abstract void onBindThisViewHolder(V holder, int position);

    @Override
    public int getItemCount() {
        //添加Header和Footer的数目
        return getThisItemCount() + 2;
    }

    /**
     * 此方法执行RecyclerView.Adapter中getItemCount()的逻辑
     *
     * @return
     */
    public abstract int getThisItemCount();

    /**
     * 重写此方法时请注意保留父类方法的逻辑，否则导致header计数混乱，下拉刷新出错
     * 使用position时注意减1(减去header的位置)
     *
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


    /**
     * 获取HeaderView
     *
     * @return
     */
    public HeaderView getHeaderView() {
        return (HeaderView) headerViewHolder.itemView;
    }

    /**
     * 获取FooterView
     *
     * @return
     */
    public FooterView getFooterView() {
        if (null == footerViewHolder)
            return null;
        return (FooterView) footerViewHolder.itemView;
    }

    public void setFooterViewEnable(boolean enable) {
        this.footerViewEnable = enable;
    }

}
