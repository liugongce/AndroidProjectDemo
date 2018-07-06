package com.fivefivelike.androidprojectdemo.mvp.activity;

import com.wheelview.OnWheelChangedListener;
import com.wheelview.WheelAdapter;
import com.wheelview.WheelView;
import com.fivefivelike.androidprojectdemo.mvp.delegate.TestDelegate;
import com.fivefivelike.mybaselibrary.base.BaseActivity;

import java.util.Calendar;

/**
 * Created by liugongce on 2017/10/17.
 */

public class TestActivity extends BaseActivity<TestDelegate> {
    int mYear, mMonth, mDay;
    String[] yearStr= new String[]{
        "1980年", "1981年", "1982年", "1983年", "1984年", "1985年", "1986年", "1987年", "1988年", "1989年",
                "1990年", "1991年", "1992年", "1993年", "1994年", "1995年", "1996年", "1997年", "1998年", "1999年",
                "2000年", "2001年", "2002年", "2003年", "2004年", "2005年", "2006年", "2007年", "2008年", "2009年",
                "2010年", "2011年", "2012年", "2013年", "2014年", "2015年", "2016年", "2017年", "2018年", "2019年", "2020年"};
    String [] monthStr=new String[] {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    String [] day28=new String[] {
            "1日", "2日", "3日", "4日", "5日", "6日", "7日", "8日", "9日", "10日",
            "11日", "12日", "13日", "14日", "15日", "16日", "17日", "18日", "19日", "20日",
            "21日", "22日", "23日", "24日", "25日", "26日", "27日", "28日"};
    String [] day29=new String[] {
            "1日", "2日", "3日", "4日", "5日", "6日", "7日", "8日", "9日", "10日",
            "11日", "12日", "13日", "14日", "15日", "16日", "17日", "18日", "19日", "20日",
            "21日", "22日", "23日", "24日", "25日", "26日", "27日", "28日", "29日"};
    String [] day30=new String[] {
            "1日", "2日", "3日", "4日", "5日", "6日", "7日", "8日", "9日", "10日",
            "11日", "12日", "13日", "14日", "15日", "16日", "17日", "18日", "19日", "20日",
            "21日", "22日", "23日", "24日", "25日", "26日", "27日", "28日", "29日", "30日"};
    String [] day31=new String[] {
            "1日", "2日", "3日", "4日", "5日", "6日", "7日", "8日", "9日", "10日",
            "11日", "12日", "13日", "14日", "15日", "16日", "17日", "18日", "19日", "20日",
            "21日", "22日", "23日", "24日", "25日", "26日", "27日", "28日", "29日", "30日",
            "31日"};
    @Override
    protected Class<TestDelegate> getDelegateClass() {
        return TestDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.viewHolder.wv_year.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return yearStr.length;
            }

            @Override
            public String getItem(int index) {
                return yearStr[index];
            }

            @Override
            public int getMaximumLength() {
                return 0;
            }
        });

        viewDelegate.viewHolder.wv_month.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return monthStr.length;
            }

            @Override
            public String getItem(int index) {
                return monthStr[index];
            }

            @Override
            public int getMaximumLength() {
                return 0;
            }
        });

        viewDelegate.viewHolder.wv_year.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                String text =  viewDelegate.viewHolder.wv_year.getItem(newIndex);
                mYear = Integer.parseInt(text.substring(0, text.length() - 1));
                updateDayEntries();
                updateTextView();
            }
        });
        viewDelegate.viewHolder.wv_month.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                String text =  viewDelegate.viewHolder.wv_month.getItem(newIndex);
                mMonth = Integer.parseInt(text.substring(0, text.length() - 1));
                updateDayEntries();
                updateTextView();
            }
        });
        viewDelegate.viewHolder.wv_day.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                String text = viewDelegate.viewHolder.wv_day.getItem(newIndex);
                mDay = Integer.parseInt(text.substring(0, text.length() - 1));
                updateTextView();
            }
        });

        mYear = 1980;
        mMonth = 1;
        mDay = 1;
        //        updateDayEntries();
        updateTextView();
    }
    private void updateDayEntries() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth - 1);

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        switch (days) {
            case 28:
                viewDelegate.viewHolder.wv_day.setAdapter(new WheelAdapter() {
                    @Override
                    public int getItemsCount() {
                        return day28.length;
                    }

                    @Override
                    public String getItem(int index) {
                        return day28[index];
                    }

                    @Override
                    public int getMaximumLength() {
                        return 0;
                    }
                });
                break;
            case 29:
                viewDelegate.viewHolder.wv_day.setAdapter(new WheelAdapter() {
                    @Override
                    public int getItemsCount() {
                        return day29.length;
                    }

                    @Override
                    public String getItem(int index) {
                        return day29[index];
                    }

                    @Override
                    public int getMaximumLength() {
                        return 0;
                    }
                });
                break;
            case 30:
                viewDelegate.viewHolder.wv_day.setAdapter(new WheelAdapter() {
                    @Override
                    public int getItemsCount() {
                        return day30.length;
                    }

                    @Override
                    public String getItem(int index) {
                        return day30[index];
                    }

                    @Override
                    public int getMaximumLength() {
                        return 0;
                    }
                });
                break;
            case 31:
            default:
                viewDelegate.viewHolder.wv_day.setAdapter(new WheelAdapter() {
                    @Override
                    public int getItemsCount() {
                        return day31.length;
                    }

                    @Override
                    public String getItem(int index) {
                        return day31[index];
                    }

                    @Override
                    public int getMaximumLength() {
                        return 0;
                    }
                });
                break;
        }
    }

    private void updateTextView() {
        String text = String.format("%04d年%d月%d日", mYear, mMonth, mDay);
        viewDelegate.viewHolder.textView.setText(text);
    }
}
