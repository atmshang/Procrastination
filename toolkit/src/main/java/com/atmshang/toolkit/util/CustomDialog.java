package com.atmshang.toolkit.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.atmshang.toolkit.R;

/**
 * CustomDialog
 * Created by Parents on 2017/1/10.
 */

public class CustomDialog {
    private AlertDialog dialog;
    private View mView;
    private TextView mTvMessage, mTvPositive, mTvNegative;
    private Context mContext;

    public CustomDialog(Context mContext) {
        this.mContext = mContext;
        mView = View.inflate(mContext, R.layout.dialog_custom, null);
        mTvMessage = (TextView) mView.findViewById(R.id.tv_message);
        mTvNegative = (TextView) mView.findViewById(R.id.tv_negative);
        mTvPositive = (TextView) mView.findViewById(R.id.tv_positive);

        mTvMessage.setVisibility(View.GONE);
        mTvNegative.setVisibility(View.GONE);
        mTvPositive.setVisibility(View.GONE);

        dialog = new AlertDialog.Builder(mContext).setView(mView).create();
    }

    public interface onClickListener {
        void onClick();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public CustomDialog setCancelable(boolean b) {
        dialog.setCancelable(b);
        return this;
    }

    public CustomDialog setMessage(String str) {
        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(str);
        return this;
    }

    public CustomDialog setPositiveListener(String str, final onClickListener listener) {
        mTvPositive.setVisibility(View.VISIBLE);
        mTvPositive.setText(str);
        mTvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
        return this;
    }

    public CustomDialog setNegativeListener(String str, final onClickListener listener) {
        mTvNegative.setVisibility(View.VISIBLE);
        mTvNegative.setText(str);
        mTvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
        return this;
    }
}
