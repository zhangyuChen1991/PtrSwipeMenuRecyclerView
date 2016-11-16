package com.cc.library.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import com.cc.library.view.adapter.SwipeAdapter;
import com.cc.library.view.util.ToastUtil;
import com.library.widget.PtrSwipeMenuRecyclerView;
import com.library.widget.interfaces.OnMenuClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private PtrSwipeMenuRecyclerView recyclerView;
    private SwipeAdapter swipeAdapter;
    public List<Integer> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initResources();
        initView();
    }

    private void initResources() {
        swipeAdapter = new SwipeAdapter();
        datas = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            datas.add(i);
        }
        swipeAdapter.setDatas(datas);
    }

    private void initView() {
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
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            return makeMovementFlags(dragFlag,0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            Collections.swap(datas,from,to);

            swipeAdapter.notifyItemMoved(from,to);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            SwipeAdapter.ViewHolder holder = (SwipeAdapter.ViewHolder) viewHolder;
            //被选中时，改背景色
            if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                holder.tv.setBackgroundColor(getResources().getColor(R.color.deepskyblue_pressed));
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            SwipeAdapter.ViewHolder holder = (SwipeAdapter.ViewHolder) viewHolder;
            holder.tv.setBackgroundColor(getResources().getColor(R.color.deepskyblue));
        }
    });

    /**
     * 菜单点击事件监听
     */
    private OnMenuClickListener onMenuClickListener = new OnMenuClickListener() {
        @Override
        public void onMenuClick(View view, int position) {
            if (view.getId() == R.id.menu1)
                ToastUtil.showToast("position:" + position + "menu1", 0);
            if (view.getId() == R.id.menu2)
                ToastUtil.showToast("position:" + position + "menu2", 0);
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
            // Simulates a background job.
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
}
