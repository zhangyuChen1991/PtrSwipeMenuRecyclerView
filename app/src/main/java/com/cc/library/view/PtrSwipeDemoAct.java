package com.cc.library.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cc.library.view.adapter.SwipeAdapter;
import com.cc.library.view.util.ToastUtil;
import com.cc.library.view.widget.MFooterView;
import com.cc.library.view.widget.MHeaderView;
import com.library.widget.baseview.FooterView;
import com.library.widget.baseview.HeaderView;
import com.library.widget.baseview.PtrSwipeMenuRecyclerView;
import com.library.widget.interfaces.OnMenuClickListener;

import java.util.ArrayList;
import java.util.List;

public class PtrSwipeDemoAct extends AppCompatActivity {
    private PtrSwipeMenuRecyclerView recyclerView;
    private SwipeAdapter swipeAdapter;
    public List<Integer> datas;
    private MHeaderView mHeaderView;
    private MFooterView mFooterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_demo);
        initResources();
        initView();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initResources() {
        mHeaderView = new MHeaderView(this);
        mFooterView = new MFooterView(this);
        initAdapter(null, null);
    }

    private void initAdapter(HeaderView headerView, FooterView footerView) {
        swipeAdapter = new SwipeAdapter();

        //不设置Header和Footer则会使用默认的布局
        if (headerView != null)
            swipeAdapter.setHeaderView(headerView);
        if (footerView != null)
            swipeAdapter.setFooterView(footerView);
        datas = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            datas.add(i);
        }
        swipeAdapter.setDatas(datas);
    }

    private void initView() {
        initActionBar();

        recyclerView = (PtrSwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(swipeAdapter);
//        //设置不允许上拉加载更多
//        recyclerView.setPullLoadMoreEnable(false);
//        //设置不允许下拉刷新
//        recyclerView.setPullToRefreshEnable(false);
        //添加菜单点击监听事件
        recyclerView.setOnMenuClickListener(onMenuClickListener);
        recyclerView.setOnPullListener(onPullListener);

    }

    private void initActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    /**
     * 菜单点击事件监听
     */
    private OnMenuClickListener onMenuClickListener = new OnMenuClickListener() {
        @Override
        public void onMenuClick(View view, int position) {
            if (view.getId() == R.id.menu1)
                ToastUtil.showToast("position:" + position + ",menu1", 0);
            if (view.getId() == R.id.menu2)
                ToastUtil.showToast("position:" + position + ",menu2", 0);
        }
    };

    /**
     * 上拉加载、下拉刷新监听
     */
    private PtrSwipeMenuRecyclerView.OnPullListener onPullListener = new PtrSwipeMenuRecyclerView.OnPullListener() {
        @Override
        public void onRefresh() {
            new GetDataTask().execute();
        }

        @Override
        public void onLoadMore() {
            new LoadMoreTask().execute();
        }
    };

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            recyclerView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private class LoadMoreTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            int count = datas.size();
            for (int i = count; i < count + 10; i++) {
                datas.add(i);
            }
            swipeAdapter.setDatas(datas);
            swipeAdapter.notifyDataSetChanged();
            recyclerView.onLoadMoreComplete();
            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.prt_act_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.change_header_footer:
                initAdapter(mHeaderView, mFooterView);//重置adapter，改变header和footer
                recyclerView.setAdapter(swipeAdapter);
                ToastUtil.showToast("Header&Footer已改变", 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
