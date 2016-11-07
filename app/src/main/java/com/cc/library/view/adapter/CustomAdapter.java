package com.cc.library.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.library.view.R;
import com.library.widget.SwipeMenuLayout;

/**
 * RecyclerView使用的Adapter
 * 1.需要手动实现ViewHolder类(这里写成内部类)
 * 2.实现RecyclerView.Adapter的相关抽象方法:onCreateViewHolder()、onBindViewHolder()、getItemCount()
 * 3.额外实现了点击事件的监听
 * Created by zhangyu on 2016/10/31.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private RecyclerViewOnClick recyclerViewOnClick;
    private String[] data;

    //RecyclerView需要手动实现一个ViewHolder ，跟ListView写ViewHolder差不多样
    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
//            tv = (TextView) itemView.findViewById(R.id.ard_tv);
        }
    }

    //设置ViewHolder 把需要绑定的布局传进去
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.default_item, parent, false);

        return new ViewHolder(swipeMenuLayout);
    }

    //onBind 给布局设置数据
    // 同时添加点击事件监听(RecyclerView 默认未设置点击监听)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        if (data != null && position < data.length)
//            holder.tv.setText(data[position]);
//
//        setOnClick(holder.itemView,holder.tv,position);
    }

    /**
     * 设置监听
     * @param itemView 需要被监听的itemView
     * @param tv 需要被监听的textview
     * @param position item位置
     */
    private void setOnClick(final View itemView, final TextView tv, final int position){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == itemView.getId()){
                    if(null != recyclerViewOnClick)
                        recyclerViewOnClick.onItemClick(position);
                }else if(v.getId() == tv.getId()){
                    if(null != recyclerViewOnClick)
                        recyclerViewOnClick.onTextOnclick(position);
                }
            }
        };
        itemView.setOnClickListener(onClickListener);
        tv.setOnClickListener(onClickListener);
    }

    //item数目
    @Override
    public int getItemCount() {
        return data == null ? 10 : data.length;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public void setRecyclerViewOnClick(RecyclerViewOnClick recyclerViewOnClick) {
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

    /**
     * 监听回调接口
     */
    public interface RecyclerViewOnClick{
        void onItemClick(int position);
        void onTextOnclick(int position);
    }
}
