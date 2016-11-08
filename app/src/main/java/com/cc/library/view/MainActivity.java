package com.cc.library.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.library.view.adapter.CustomAdapter;
import com.cc.library.view.util.DisplayUtils;
import com.cc.library.view.util.ToastUtil;
import com.library.widget.SwipeMenuLayout2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout rootContainer;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initResources();
        initView();
    }

    private void initResources() {
        adapter = new CustomAdapter();
    }

    private void initView() {
        rootContainer = (LinearLayout) findViewById(R.id.activity_container);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);

//        rootContainer.setOnClickListener(this);

        additem();
    }

    private void additem() {

        TextView contentView = new TextView(this);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(8,9);
        contentView.setLayoutParams(params1);
        contentView.setGravity(Gravity.CENTER);
        contentView.setText("content");
        contentView.setTextSize(25);
        contentView.setBackgroundColor(getResources().getColor(R.color.gray));

        LinearLayout menuView = (LinearLayout) View.inflate(this,R.layout.menu_for_test,null);

        SwipeMenuLayout2 SwipeMenuLayout2 = new SwipeMenuLayout2(contentView,menuView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DisplayUtils.dip2px(this,50));
        SwipeMenuLayout2.setLayoutParams(params);

//        SwipeMenuLayout2.smoothScrollTo(0, 190);

        rootContainer.addView(SwipeMenuLayout2,0);
    }


    @Override
    public void onClick(View view) {
        ToastUtil.showToast("click", 0);

    }
}
