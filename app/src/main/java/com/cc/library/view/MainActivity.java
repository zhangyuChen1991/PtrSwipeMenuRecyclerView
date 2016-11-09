package com.cc.library.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.cc.library.view.adapter.CustomAdapter;
import com.cc.library.view.adapter.SwipeAdapter;
import com.cc.library.view.util.ToastUtil;
import com.library.widget.PtrSwipeMenuRecyclerView;
import com.library.widget.SwipeMenuLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout rootContainer;
    private PtrSwipeMenuRecyclerView recyclerView;
    private CustomAdapter adapter;
    private SwipeAdapter swipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initResources();
        initView();
    }

    private void initResources() {
        adapter = new CustomAdapter();
        swipeAdapter = new SwipeAdapter();
    }

    private void initView() {
        rootContainer = (LinearLayout) findViewById(R.id.activity_container);
        recyclerView = (PtrSwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(swipeAdapter);
//        recyclerView.setVisibility(View.GONE);

        rootContainer.setOnClickListener(this);

        additem();
    }

    private void additem() {

        View contentView = View.inflate(this,R.layout.content_view_for_test,null);
        LinearLayout menuView = (LinearLayout) View.inflate(this,R.layout.menu_for_test,null);

        SwipeMenuLayout SwipeMenuLayout = new SwipeMenuLayout(this,contentView,menuView);

        rootContainer.addView(SwipeMenuLayout,0);
    }


    @Override
    public void onClick(View view) {
        ToastUtil.showToast("click", 0);

    }
}
