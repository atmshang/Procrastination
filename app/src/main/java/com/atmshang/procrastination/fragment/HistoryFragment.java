package com.atmshang.procrastination.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.atmshang.procrastination.R;
import com.atmshang.procrastination.adapter.HistoryAdapter;
import com.atmshang.procrastination.entity.History;
import com.atmshang.procrastination.event.ControlEvent;
import com.atmshang.toolkit.adapter.swipe.SimpleItemTouchHelperCallback;
import com.atmshang.toolkit.util.IUtil;
import com.atmshang.toolkit.view.LoadLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by atmshang on 2017/1/16.
 */

public class HistoryFragment extends BaseFragment {
    private LoadLayout mLoadLayout;
    private FrameLayout mFlGroup;
    private RecyclerView mRecyclerView;
    private TextView mTvHeader;

    private HistoryAdapter mAdapter;

    private ItemTouchHelper mItemTouchHelper;

    private RealmResults<History> mList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootView(R.layout.fragment_history);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar("历史");
        mLoadLayout = (LoadLayout) mRootView.findViewById(R.id.loadLayout);
        mFlGroup = (FrameLayout) mRootView.findViewById(R.id.fl_group);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mTvHeader = (TextView) mRootView.findViewById(R.id.tv_header);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mList = mRealm.where(History.class).findAll().sort("createTimestamp", Sort.DESCENDING);
        mAdapter = new HistoryAdapter(mActivity, mList, mLoadLayout);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager mManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int pos = mManager.findFirstVisibleItemPosition();
                if (pos >= 0) {
                    mLoadLayout.dismiss();
                    mTvHeader.setText(IUtil.getTimeYMD(mList.get(pos).getDayTimestamp()));
                    if (mList.get(pos).isHeader()) {
                        if (dy == 0) {
                            mTvHeader.setVisibility(View.GONE);
                        } else {
                            mTvHeader.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mTvHeader.setVisibility(View.VISIBLE);
                    }
                } else {
                    mLoadLayout.empty();
                }
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter, mActivity);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        getData();
    }

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onControlEvent(ControlEvent event) {
        switch (event.getState()) {
            case stop:
                getData();
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    protected void getData() {
        if (mList.size() > 0) {
            mLoadLayout.dismiss();
        } else {
            mLoadLayout.empty();
        }
    }

}
