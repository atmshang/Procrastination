package com.atmshang.toolkit.adapter.swipe;

import android.support.v7.widget.RecyclerView;

/**
 * Created by atmshang on 2017/1/19.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position, RecyclerView.ViewHolder viewHolder);
}
