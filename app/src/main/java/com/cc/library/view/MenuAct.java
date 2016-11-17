package com.cc.library.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.library.view.adapter.CustomAdapter;


/**
 * Created by zhangyu on 2016/10/31.
 */

public class MenuAct extends AppCompatActivity {
    private static final String TAG = "RVDMenu";
    private RecyclerView recyclerView;
    private CustomAdapter adapter = new CustomAdapter();
    private String[] data = {"下拉刷新、自动加载及侧滑菜单Demo", "长按拖拽Item及侧滑菜单Demo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        initResources();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.arvdm_recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 点击事件监听
     */
    private CustomAdapter.RecyclerViewOnClickListener recyclerViewOnClick = new CustomAdapter.RecyclerViewOnClickListener() {
        @Override
        public void onItemClick(int position) {
            switch (position){
                case 0:
                    startActivity(new Intent(MenuAct.this,PtrSwipeDemoAct.class));
                    break;
                case 1:
                    startActivity(new Intent(MenuAct.this,DragSwipeDemoAct.class));
                    break;
            }
        }
    };

    private void initResources() {
        adapter.setData(data);
        adapter.setRecyclerViewOnClick(recyclerViewOnClick);
        adapter.notifyDataSetChanged();
    }
}
