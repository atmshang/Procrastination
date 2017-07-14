package com.atmshang.toolkit.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by atmshang on 2017/1/2.
 */

public class GridDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int column;

    public GridDecoration(int space, int column) {
        this.space = space;
        this.column = column;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) >= column) {
            outRect.top = space;
        }
    }
}
