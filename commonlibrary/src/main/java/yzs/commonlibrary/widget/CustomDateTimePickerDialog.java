package yzs.commonlibrary.widget;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import yzs.commonlibrary.R;
import yzs.commonlibrary.util.ToastUtil;


/**
 * Des：自定义封装 日期时间 选择弹窗
 * creat by Zishu.Ye on 2016/10/24  9:49
 */
public class CustomDateTimePickerDialog {

    private static final String DATE_SYMBOL = "-";
    private static final String TIME_SYMBOL = ":";
    private static final String DATE_PICKER = "datePicker";
    private static final String TIME_PICKER = "timePicker";

    private Activity activity;

    private DatePickerDialog dpd;//日期选择器
    private TimePickerDialog tpd;//时间选择器

    private Calendar c;//当前控件的时间

    private boolean isFutureTime = false;//是否可以选择未来时间

    private int curY, curM, curD;//当前选择的 年月日

    private DateSetListener dateSetListener;//日期选择回调


    /**
     * 实例化一个日期时间选择器
     *
     * @param activity 对应的ac
     * @param dateTv   日期展示view
     * @param c        日期
     * @param listener 日期选择监听
     */
    private CustomDateTimePickerDialog(Activity activity, TextView dateTv, TextView timeTv, Calendar c, DateSetListener listener) {
        this(activity, dateTv, timeTv, c);
        this.dateSetListener = listener;
    }

    /**
     * 实例化一个 指定（c） 日期时间选择器
     *
     * @param activity 对应的ac
     * @param dateTv   日期展示view
     * @param timeTv   时间展示view
     * @param c        指定的日期        如果指定日期为空则自动创建当前日期
     */
    private CustomDateTimePickerDialog(Activity activity, TextView dateTv, TextView timeTv, Calendar c) {
        this.activity = activity;
        if (c == null) {
            c = Calendar.getInstance();
        }
        this.c = c;

        dpd = getDefaultDateDialog(dateTv, this.c);
        dpd.setAccentColor(ContextCompat.getColor(activity, R.color.colorPrimary));

        tpd = getDefaultTimeDialog(timeTv, this.c);
        tpd.setAccentColor(ContextCompat.getColor(activity, R.color.colorPrimary));

    }

    /**
     * 设置是否需要 限制不能选择未来时间
     *
     * @return this
     */
    private CustomDateTimePickerDialog setMaxTime() {
        dpd.setMaxDate(Calendar.getInstance());
        isFutureTime = true;
        return this;
    }

    /**
     * 显示日期选择器
     */
    public void showDatePicker() {
        if(dpd!=null
                &&!dpd.isAdded())
            dpd.show(activity.getFragmentManager(), DATE_PICKER);
    }

    /**
     * 显示时间选择器
     */
    public void showTimePicker() {
        if(null==tpd
                ||tpd.isAdded())
            return;
        if (isFutureTime)
            checkDateIsNow();
        tpd.show(activity.getFragmentManager(), TIME_PICKER);
    }


    /**
     * @param tv       选中日期设置对象
     * @param calendar 初始化日期
     * @return 返回指定默认日期 选择器
     */
    private DatePickerDialog getDefaultDateDialog(final TextView tv, Calendar calendar) {
        final int y = calendar.get(Calendar.YEAR),
                m = calendar.get(Calendar.MONTH),
                d = calendar.get(Calendar.DAY_OF_MONTH);
        curY = y;
        curM = m;
        curD = d;
        if(null!=tv) {
            tv.setText(getDateTem(y, m, d));
        }
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                curY = year;
                curM = monthOfYear;
                curD = dayOfMonth;
                String dateTemStr=getDateTem(year, monthOfYear, dayOfMonth);
                if(null!=tv)
                    tv.setText(dateTemStr);
                if(null!=dateSetListener)
                    dateSetListener.onSet(dateTemStr,year,monthOfYear,dayOfMonth);
            }
        };
        return DatePickerDialog.newInstance(listener, y, m, d);
    }



    /**
     * @param tv       选中日期设置对象
     * @param calendar 初始化日期
     * @return 返回指定默认时间 选择器
     */
    private TimePickerDialog getDefaultTimeDialog(final TextView tv, Calendar calendar) {
        if(null!=tv) {
            tv.setText(getTimeTem(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE)));
        }

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                if(null!=tv)
                    tv.setText(getTimeTem(hourOfDay, minute));
            }
        };

        return TimePickerDialog.newInstance(
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
    }

    /**
     * @param y 年
     * @param m 月
     * @param d 日
     * @return 返回日期字符串模板
     */
    private String getDateTem(int y, int m, int d) {
        int nM = m + 1;
        String mStr = String.valueOf(nM), dStr = String.valueOf(d);
        if (nM < 10)
            mStr = "0" + nM;
        if (d < 10)
            dStr = "0" + d;

        return y + DATE_SYMBOL + mStr + DATE_SYMBOL + dStr;
    }

    /**
     * @param h 小时
     * @param m 分
     * @return 返回时间字符串模板
     */
    private String getTimeTem(int h, int m) {
        String hStr = String.valueOf(h), mStr = String.valueOf(m);
        if (h < 10)
            hStr = "0" + h;
        if (m < 10)
            mStr = "0" + m;

        return hStr + TIME_SYMBOL + mStr;
    }


    /**
     * 检查当前选择日期是否是当天
     */
    private void checkDateIsNow() {
        if (c.get(Calendar.YEAR) == curY &&
                c.get(Calendar.MONTH) == curM &&
                c.get(Calendar.DAY_OF_MONTH) == curD) {
            Calendar newNow = Calendar.getInstance();
            tpd.setMaxTime(newNow.get(Calendar.HOUR_OF_DAY),
                    newNow.get(Calendar.MINUTE),
                    newNow.get(Calendar.SECOND));

        } else
            tpd.setMaxTime(23, 59, 59);
    }

    /**
     * 日期选择监听
     */
    public interface DateSetListener {
        /**
         * @param dateStr       格式化好的时间字符串
         * @param year          年
         * @param monthOfYear   月
         * @param dayOfMonth    日
         */
        void onSet(String dateStr, int year, int monthOfYear, int dayOfMonth);
    }

    public static class DateTimePDCreator {
        private Activity activity;      //ac
        private TextView dateTv;        //日期text view
        private TextView timeTv;        //时间text view
        private Calendar specificTime;  //指定的时间点
        private boolean isFutureTime;   //是否需要限制可以选择未来时间
        private DateSetListener dateListener;//日期选择回调

        public CustomDateTimePickerDialog create() {
            if (activity == null//限制使用回调 或者 直接传入日期或者时间的textView
                    || (dateListener == null
                    && (dateTv == null && timeTv == null))) {
                ToastUtil.showMessage("创建条件不符合");
                return null;
            }
            CustomDateTimePickerDialog dialog =
                    new CustomDateTimePickerDialog(activity, dateTv, timeTv, specificTime,dateListener);
            if(!isFutureTime)
                dialog.setMaxTime();

            return dialog;
        }

        public DateTimePDCreator(Activity activity) {
            this.activity = activity;
        }


        /**
         * 设置日期选择控件回调显示的TextView
         * @param dateTv    日期text view
         */
        public DateTimePDCreator setDateTv(TextView dateTv) {
            this.dateTv = dateTv;
            return this;
        }

        /**
         * 设置时间选择控件回调显示的TextView
         * @param timeTv    时间text view
         */
        public DateTimePDCreator setTimeTv(TextView timeTv) {
            this.timeTv = timeTv;
            return this;
        }

        /**
         * 设置指定的时间点，不设置默认则当前时间
         * @param specificTime  Calendar
         */
        public DateTimePDCreator setSpecificTime(Calendar specificTime) {
            this.specificTime = specificTime;
            return this;
        }

        /**
         * 设置指定的日期
         * @param specificTimeStr   日期字符串 格式 yyyy-MM-dd
         */
        public DateTimePDCreator setSpecificDate(String specificTimeStr){
            Calendar c=Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                c.setTime(sdf.parse(specificTimeStr));
                specificTime=c;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 设置是否限制选择未来时间 默认不限制
         * @param futureTime true false
         */
        public DateTimePDCreator setFutureTime(boolean futureTime) {
            isFutureTime = futureTime;
            return this;
        }

        /**
         * 设置日期选择回调监听
         * @param dateListener  回调监听
         */
        public DateTimePDCreator setDateListener(DateSetListener dateListener) {
            this.dateListener = dateListener;
            return this;
        }
    }
}
