package com.fivefivelike.mybaselibrary.view.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.wheelview.NumberWheelAdapter;
import com.wheelview.OnWheelChangedListener;
import com.wheelview.Wheel3DView;
import com.wheelview.WheelView;
import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.utils.DateUtils;
import com.fivefivelike.mybaselibrary.utils.StringUtil;

/**
 * Created by liugongce on 2016/11/16.
 * 个人资料
 */

public class BirthdayDialog extends BaseDialog implements OnWheelChangedListener {
    private Wheel3DView year;
    private Wheel3DView month;
    private Wheel3DView day;
    private String yearStr, monthStr, dayStr;
    private OnTimeChooseListener listener;

    public BirthdayDialog(Activity context, OnTimeChooseListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_birthday;
    }

    @Override
    protected void startInit() {
        getWindow().setGravity(Gravity.BOTTOM);
        setWindowNoPadding();
        year = (Wheel3DView) findViewById(R.id.year);
        month = (Wheel3DView) findViewById(R.id.month);
        day = (Wheel3DView) findViewById(R.id.day);
        int textsize = mContext.getResources().getDimensionPixelSize(R.dimen.trans_28px);
        year.setTextSize(textsize);
        month.setTextSize(textsize);
        day.setTextSize(textsize);
        day.setOpenVoice(true);
        int currentyear = Integer.parseInt(DateUtils.getCurrYear());
        setYear(currentyear);
        int currMonth = DateUtils.getCurrMonth();
        int currDay = DateUtils.getCurrDay();
        month.setAdapter(new NumberWheelAdapter(1, 12, "%02d月"));
        month.setOnWheelChangedListener(this);
        month.setCurrentIndex(currMonth - 1);
        monthStr = month.getAdapter().getItem(currMonth - 1);
        day.setOnWheelChangedListener(this);
        day.setAdapter(new NumberWheelAdapter(1, getDayNum()));
        day.setCurrentIndex(currDay - 1);
        dayStr = day.getAdapter().getItem(currDay - 1);
        getView(R.id.cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getView(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnTimeChooseListener(yearStr.substring(0, yearStr.length() - 1) + "-" + monthStr.substring(0, monthStr.length() - 1) + "-"
                        + dayStr);
                dismiss();
            }
        });
    }

    public BirthdayDialog setYear(int maxYear) {
        year.setAdapter(new NumberWheelAdapter(1900, maxYear, "%s年"));
        year.setOnWheelChangedListener(this);
        year.setCurrentIndex(Integer.parseInt(DateUtils.getCurrYear()) - 1900);
        yearStr = year.getAdapter().getItem(Integer.parseInt(DateUtils.getCurrYear()) - 1900);
        return this;
    }

    private int getDayNum() {
        int m = monthStr.length() - 1;
        int y = yearStr.length() - 1;

        String month = monthStr.substring(0, m);
        String year = yearStr.substring(0, y);
        if (month.equals("01") || month.equals("03") || month.equals("05") ||
                month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            return 31;
        } else if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
            return 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer
                    .parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {

                return 29;
            } else {
                return 28;
            }
        }

    }

    public interface OnTimeChooseListener {
        void setOnTimeChooseListener(String time);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == year) {
            yearStr = year.getAdapter().getItem(newValue);
        }
        if (wheel == month) {
            monthStr = month.getAdapter().getItem(newValue);
            if (!StringUtil.isBlank(dayStr)) {
                day.setAdapter(new NumberWheelAdapter(1, getDayNum(), "%02d"));
                day.setCurrentIndex(0);
            }
        }
        if (wheel == day) {
            dayStr = day.getAdapter().getItem(newValue);
        }
    }
}
