package com.library.widget.interfaces;

import android.view.View;

/**
 * Created by zhangyu on 2016/11/9.
 */
public interface OnMenuClickListener {
    /**
     * 菜单点击监听
     * @param view 被点击菜单view
     * @param position 被点击菜单所在item的位置
     */
    void onMenuClick(View view,int position);
}
