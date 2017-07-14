package com.atmshang.procrastination.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atmshang.procrastination.R;
import com.atmshang.procrastination.entity.History;
import com.atmshang.procrastination.event.FreezeEvent;
import com.atmshang.toolkit.adapter.OnItemClickListener;
import com.atmshang.toolkit.adapter.swipe.ItemTouchHelperAdapter;
import com.atmshang.toolkit.adapter.swipe.ItemTouchHelperViewHolder;
import com.atmshang.toolkit.adapter.swipe.OnStartDragListener;
import com.atmshang.toolkit.util.IUtil;
import com.atmshang.toolkit.view.LoadLayout;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by atmshang on 2016/12/27.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.IndexViewHolder> implements ItemTouchHelperAdapter {


    private LayoutInflater mInflater;
    private Context mContext;
    private RealmResults<History> mList;
    private OnItemClickListener mOnItemClickListener;
    private OnStartDragListener mOnStartDragListener;

    private LoadLayout mLoadLayout;

    private History lastHistory, lastHeader;
    private int lastPosition, lastHeaderPosition;

    private Handler mHandler = new Handler();

    public HistoryAdapter(@NonNull Context context, @NonNull RealmResults<History> mList, @NonNull LoadLayout mLoadLayout) {
        this.mContext = context;
        this.mList = mList;
        this.mLoadLayout = mLoadLayout;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView.ViewHolder viewHolder) {
        EventBus.getDefault().post(new FreezeEvent(true));

        if (mList.get(position).isHeader()) {
            notifyDataSetChanged();
            return;
        }
        Realm realmDo = Realm.getDefaultInstance();
        realmDo.executeTransaction(realm -> {
            lastHistory = realm.copyFromRealm(mList.get(position));
            lastPosition = position;

            if (mList.get(position - 1).isHeader()
                    && ((position < mList.size() - 1 && mList.get(position).getDayTimestamp() != mList.get(position + 1).getDayTimestamp())
                    || position == (mList.size() - 1))) {
                lastHeader = realm.copyFromRealm(mList.get(position - 1));
                lastHeaderPosition = position - 1;
                mList.get(position).deleteFromRealm();
                notifyItemRemoved(position);
                mList.get(position - 1).deleteFromRealm();
                notifyItemRemoved(position - 1);
            } else {
                lastHeader = null;
                lastHeaderPosition = -1;
                mList.get(position).deleteFromRealm();
                notifyItemRemoved(position);
            }
        });
        realmDo.close();

        Snackbar snackbar = Snackbar.make(viewHolder.itemView, "已删除", Snackbar.LENGTH_LONG);
        snackbar.setAction("撤销", v -> {
            snackbar.dismiss();
            Realm realmUndo = Realm.getDefaultInstance();
            realmUndo.executeTransaction(realm -> {
                mLoadLayout.dismiss();
                if (lastHeader != null) {
                    realm.copyToRealm(lastHeader);
                    notifyItemInserted(lastHeaderPosition);
                }
                realm.copyToRealm(lastHistory);
                notifyItemInserted(lastPosition);
            });
            realmUndo.close();
        });
        snackbar.show();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnStartDragListener(OnStartDragListener mOnStartDragListener) {
        this.mOnStartDragListener = mOnStartDragListener;
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    protected void setUpEvent(final IndexViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int position = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView, position);
            });
        }
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        View view = mInflater.inflate(R.layout.item_history, parent, false);
        IndexViewHolder holder = new IndexViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        History item = mList.get(position);
        //加载控件内容
        String header = IUtil.getTimeYMD(item.getCreateTimestamp());
        if (item.isHeader()) {
            holder.mTvHeader.setText(header);
            holder.mTvHeader.setVisibility(View.VISIBLE);
            holder.mLlContent.setVisibility(View.GONE);
        } else {
            holder.mTvHeader.setVisibility(View.GONE);
            holder.mLlContent.setVisibility(View.VISIBLE);
            holder.mMliPic.setLetter(item.getName());
            holder.mMliPic.setShapeColor(mContext.getResources().getColor(R.color.colorPrimary));
            if (item.getProcess() > 1000 * 60 * 60) {
                holder.mTvProcess.setText(String.format(Locale.getDefault(), "%s : %d 时 %d 分 %02d 秒", item.getName(), item.getProcess() / 1000 / 60 / 60, item.getProcess() / 1000 / 60 % 60, item.getProcess() / 1000 % 60));
            } else {
                holder.mTvProcess.setText(String.format(Locale.getDefault(), "%s : %d 分 %02d 秒", item.getName(), item.getProcess() / 1000 / 60 % 60, item.getProcess() / 1000 % 60));
            }
            if (item.getProcrastinate() > 1000 * 60 * 60) {
                holder.mTvProcrastinate.setText(String.format(Locale.getDefault(), "%s : %d 时 %d 分 %02d 秒", "拖延的时间", item.getProcrastinate() / 1000 / 60 / 60, item.getProcrastinate() / 1000 / 60 % 60, item.getProcrastinate() / 1000 % 60));
            } else {
                holder.mTvProcrastinate.setText(String.format(Locale.getDefault(), "%s : %d 分 %02d 秒", "拖延的时间", item.getProcrastinate() / 1000 / 60 % 60, item.getProcrastinate() / 1000 % 60));
            }
            setUpEvent(holder);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class IndexViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        //定义控件
        private TextView mTvHeader;
        private LinearLayout mLlContent;
        private MaterialLetterIcon mMliPic;
        private TextView mTvProcess;
        private TextView mTvProcrastinate;

        public IndexViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            mTvHeader = (TextView) itemView.findViewById(R.id.tv_header);
            mLlContent = (LinearLayout) itemView.findViewById(R.id.ll_content);
            mMliPic = (MaterialLetterIcon) itemView.findViewById(R.id.mli_pic);
            mTvProcess = (TextView) itemView.findViewById(R.id.tv_process);
            mTvProcrastinate = (TextView) itemView.findViewById(R.id.tv_procrastinate);
        }

        @Override
        public void onItemSelected(Context context) {
            EventBus.getDefault().post(new FreezeEvent(false));
        }

        @Override
        public void onItemClear(Context context) {
            EventBus.getDefault().post(new FreezeEvent(true));
        }
    }
}