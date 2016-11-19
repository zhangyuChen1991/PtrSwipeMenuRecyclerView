package com.cc.library.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.cc.library.view.adapter.DragSwipeAdapter;
import com.cc.library.view.util.ToastUtil;
import com.library.widget.baseview.PtrSwipeMenuRecyclerView;
import com.library.widget.interfaces.OnMenuClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangyu on 2016/11/17.
 */

public class DragSwipeDemoAct extends AppCompatActivity {
    private PtrSwipeMenuRecyclerView recyclerView;
    private DragSwipeAdapter adapter;
    public List<Integer> datas;

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
        adapter = new DragSwipeAdapter();
        datas = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            datas.add(i);
        }
        adapter.setDatas(datas);
    }

    private void initView() {
        initActionBar();

        recyclerView = (PtrSwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
//        //设置不允许上拉加载更多
//        recyclerView.setPullLoadMoreEnable(false);
//        //设置不允许下拉刷新
        recyclerView.setPullToRefreshEnable(false);
        //添加菜单点击监听事件
        recyclerView.setOnMenuClickListener(onMenuClickListener);
        recyclerView.setOnPullListener(onPullListener);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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


    /**
     * 使用itemTouchHelper完成拖拽效果
     * 由于拖拽与下拉刷新有冲突，所以暂时不建议放在一起使用
     */
    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            return makeMovementFlags(dragFlag, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            if (to < datas.size() - 1) {//把Footerview的位置减掉（ViewType不一样）
                Collections.swap(datas, from, to);
                adapter.notifyItemMoved(from, to);
                return true;
            }
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            DragSwipeAdapter.ViewHolder holder = (DragSwipeAdapter.ViewHolder) viewHolder;
            //被选中时，改背景色
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                holder.tv.setBackgroundColor(getResources().getColor(R.color.deepskyblue_pressed));
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            DragSwipeAdapter.ViewHolder holder = (DragSwipeAdapter.ViewHolder) viewHolder;
            holder.tv.setBackgroundColor(getResources().getColor(R.color.deepskyblue));
        }
    });

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
            adapter.setDatas(datas);
            adapter.notifyDataSetChanged();
            recyclerView.onLoadMoreComplete();
            super.onPostExecute(result);
        }
    }
}
