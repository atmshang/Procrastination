package com.atmshang.toolkit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 留着给自己快速复制使用的RecyleAdapter
 * Created by atmshang on 2016/12/27.
 */
public class BaseRecycleAdapter extends RecyclerView.Adapter<BaseRecycleAdapter.IndexViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<Object> mList;
    private OnItemClickListener mOnItemClickListener;

    public BaseRecycleAdapter(@NonNull Context context, @NonNull ArrayList<Object> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    protected void setUpEvent(final IndexViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }

            });
        }
    }

    public void setData(@NonNull ArrayList<Object> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(@NonNull ArrayList<Object> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        //  View view = mInflater.inflate(R.layout.item_checkup_simple, parent, false);
        //  IndexViewHolder holder = new IndexViewHolder(view);
        //  return holder;
        return null;
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        Object item = mList.get(position);
        //加载控件内容
        setUpEvent(holder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class IndexViewHolder extends RecyclerView.ViewHolder {
        //定义控件
        public IndexViewHolder(View itemView) {
            super(itemView);
            //绑定控件
        }
    }
}