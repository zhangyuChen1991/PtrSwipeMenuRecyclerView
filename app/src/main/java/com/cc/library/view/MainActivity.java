package com.cc.library.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.cc.library.view.adapter.CustomAdapter;
import com.cc.library.view.util.ToastUtil;

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

    View menuView;

    private void initView() {
        rootContainer = (LinearLayout) findViewById(R.id.activity_container);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        menuView = View.inflate(this, R.layout.menu_for_test, null);

        rootContainer.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        ToastUtil.showToast("click", 0);

    }
}
