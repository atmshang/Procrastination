package com.atmshang.toolkit.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atmshang.toolkit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * IUtil
 * Created by atmshang on 2016/10/19.
 */

public class IUtil {

    public static void ShowToast(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ShowToast(Context mContext, int msgID) {
        Toast.makeText(mContext, mContext.getText(msgID), Toast.LENGTH_SHORT).show();
    }

    public static void ShowInputDialog(Context mContext, String title, final TextView resultText) {
        View view = View.inflate(mContext, R.layout.dialog_custom_input, null);
        TextView mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        final EditText mEtInput = (EditText) view.findViewById(R.id.et_input);
        TextView mTvNegative = (TextView) view.findViewById(R.id.tv_negative);
        TextView mTvPositive = (TextView) view.findViewById(R.id.tv_positive);
        mTvMessage.setText(title);
        mEtInput.setText(resultText.getText());
        mEtInput.setSelection(mEtInput.getText().length());
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();
        mTvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mTvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(mEtInput.getText());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void ShowInputDialog(Context mContext, String title, final TextView resultText, final Object emptyValue) {
        View view = View.inflate(mContext, R.layout.dialog_custom_input, null);
        TextView mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        final EditText mEtInput = (EditText) view.findViewById(R.id.et_input);
        TextView mTvNegative = (TextView) view.findViewById(R.id.tv_negative);
        TextView mTvPositive = (TextView) view.findViewById(R.id.tv_positive);
        mTvMessage.setText(title);
        mEtInput.setText(resultText.getText());
        mEtInput.setSelection(mEtInput.getText().length());
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();
        mTvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mTvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(mEtInput.getText().length() == 0 ? emptyValue.toString() : mEtInput.getText());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void ShowInputDialog(Context mContext, String title, final TextView resultText, int inputType) {
        View view = View.inflate(mContext, R.layout.dialog_custom_input, null);
        TextView mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        final EditText mEtInput = (EditText) view.findViewById(R.id.et_input);
        TextView mTvNegative = (TextView) view.findViewById(R.id.tv_negative);
        TextView mTvPositive = (TextView) view.findViewById(R.id.tv_positive);
        mTvMessage.setText(title);
        mEtInput.setText(resultText.getText());
        mEtInput.setInputType(inputType);
        mEtInput.setSelection(mEtInput.getText().length());
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();
        mTvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mTvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(mEtInput.getText());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void ShowInputDialog(Context mContext, String title, final TextView resultText, int inputType, final Object emptyValue) {
        View view = View.inflate(mContext, R.layout.dialog_custom_input, null);
        TextView mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        final EditText mEtInput = (EditText) view.findViewById(R.id.et_input);
        TextView mTvNegative = (TextView) view.findViewById(R.id.tv_negative);
        TextView mTvPositive = (TextView) view.findViewById(R.id.tv_positive);
        mTvMessage.setText(title);
        mEtInput.setText(resultText.getText());
        mEtInput.setInputType(inputType);
        mEtInput.setSelection(mEtInput.getText().length());
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();
        mTvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mTvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultText.setText(mEtInput.getText().length() == 0 ? emptyValue.toString() : mEtInput.getText());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void InitSwipeRefreshLayout(SwipeRefreshLayout layout, SwipeRefreshLayout.OnRefreshListener listener) {
        layout.setColorSchemeResources(R.color.colorPrimary);
        layout.setOnRefreshListener(listener);
        layout.setRefreshing(true);
    }

//    public static void bitmap(String url, ImageView imageView, int defResId) {
//        if (!TextUtils.isEmpty(url)) {
//            DrawableRequestBuilder builder = Glide.with(imageView.getContext().getApplicationContext())
//                    .load(url)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL);
//            if (defResId >= 0) builder.placeholder(defResId).error(defResId);
//            builder.into(imageView);
//        }
//    }
//
//    public static void circleBitmap(String url, ImageView imageView, int defResId) {
//        if (!TextUtils.isEmpty(url)) {
//            DrawableRequestBuilder builder = Glide.with(imageView.getContext().getApplicationContext())
//                    .load(url)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .bitmapTransform(new CropCircleTransformation(imageView.getContext().getApplicationContext()));
//            if (defResId >= 0) builder.placeholder(defResId).error(defResId);
//            builder.into(imageView);
//        }
//    }


    public static int getAge(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR) >= 0 ? Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR) : 0;
    }

    public static long getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(getTimeYMD(System.currentTimeMillis())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -28800000;
    }

    public static long getTodayLast() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(getTimeYMD(System.currentTimeMillis())).getTime() + 86399000L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -28800000;
    }

    public static String getTimeYMD(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTimeYMDHM(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getTimeSYMD(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
        return format.format(date);
    }

    public static String getTimeMDHM(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }

    //以下静态类

    public static class ITimeChooseDialog {
        public ITimeChooseDialog(Context mContext, long preTimestamp, final TextView resultText, final onDateSetListener listener) {
            final Calendar c = Calendar.getInstance();
            c.setTimeInMillis(preTimestamp);
            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    resultText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    c.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    listener.onDateSet(c.getTimeInMillis());
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }

        public interface onDateSetListener {
            void onDateSet(long result);
        }
    }

    public static class IAgeSetDialog {
        public IAgeSetDialog(final Context mContext, long preTimestamp, final TextView resultText, final onDateSetListener listener) {
            final Calendar c = Calendar.getInstance();
            c.setTimeInMillis(preTimestamp);
            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    int age = Calendar.getInstance().get(Calendar.YEAR) - year;
                    if (age < 0) {
                        Toast.makeText(mContext, "日期选择错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resultText.setText(age + "岁");
                    c.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    listener.onDateSet(c.getTimeInMillis());
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }

        public interface onDateSetListener {
            void onDateSet(long result);
        }
    }
}
