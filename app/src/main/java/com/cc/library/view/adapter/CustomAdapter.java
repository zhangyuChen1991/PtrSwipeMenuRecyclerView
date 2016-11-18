package com.cc.library.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.library.view.R;


/**
 * RecyclerView使用的Adapter
 * 1.需要手动实现ViewHolder类(这里写成内部类)
 * 2.实现RecyclerView.Adapter的相关抽象方法:onCreateViewHolder()、onBindViewHolder()、getItemCount()
 * 3.额外实现了点击事件的监听
 * Created by zhangyu on 2016/10/31.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private RecyclerViewOnClickListener recyclerViewOnClick;
    private String[] data;

    //RecyclerView需要手动实现一个ViewHolder ，跟ListView写ViewHolder差不多样
    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv;
        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv = (TextView) itemView.findViewById(R.id.ard_tv);

            //设置点击监听
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //用getAdapterPosition获取item在adapter中的位置
            if (v.getId() == itemView.getId()) {
                if (null != recyclerViewOnClick)
                    recyclerViewOnClick.onItemClick(getAdapterPosition());
            }
        }
    }

    //设置ViewHolder 把需要绑定的布局传进去
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.adapter_recyclerview_menu_demo, null);
        return new ViewHolder(itemView);
    }

    //onBind 给布局设置数据
    // 同时添加点击事件监听(RecyclerView 默认未设置点击监听)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (data != null && position < data.length)
            holder.tv.setText(data[position]);

    }


    //item数目
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public void setRecyclerViewOnClick(RecyclerViewOnClickListener recyclerViewOnClick) {
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 监听回调接口
     */
    public interface RecyclerViewOnClickListener {
        void onItemClick(int position);

    }

}
