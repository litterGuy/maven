package org.cc.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
	 /**
     * 针对传入的date,最近n天的那天的日期
     * @param n  n=0:今天;n=n-1:最近n天.
     */
     public static Date getRecentDay(Date d, int n)
     {
             int specdate_day;
             int specdate_month;
             int specdate_year;
             Calendar cal_spec = Calendar.getInstance();
             cal_spec.setTime(d);
             cal_spec.add(Calendar.DATE, -n);
             specdate_day = cal_spec.get(Calendar.DAY_OF_MONTH);
             specdate_month = cal_spec.get(Calendar.MONTH);
             specdate_year = cal_spec.get(Calendar.YEAR);
             Calendar cal_this = new GregorianCalendar(specdate_year,
                     specdate_month, specdate_day);
             return cal_this.getTime();
     }
}
