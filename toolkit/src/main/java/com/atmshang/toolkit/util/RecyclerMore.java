package com.atmshang.toolkit.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * RecyclerMore
 * Created by atmshang on 2016/12/28.
 */

public class RecyclerMore {
    private boolean isLoading = false;

    public void finishLoad() {
        isLoading = false;
    }

    public RecyclerMore(final RecyclerView mRecyclerView, final RecyclerMore.onMoreListener moreListener) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager mManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int lastVisibleItem = mManager.findLastVisibleItemPosition();
                int totalItemCount = mRecyclerView.getAdapter().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2) {
                    if (!isLoading) {
                        moreListener.onMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public interface onMoreListener {
        void onMore();
    }
}
