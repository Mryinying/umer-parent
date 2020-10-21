package com.umer.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static DateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // TODO Auto-generated method stub
        SimpleDateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = df_time.parse("2018-12-31 14:00:00");
        Date d2 = new Date();
        //System.out.println(DateUtil.daysBetween(d1, d2));
        System.out.println(DateUtil.getDayOfWeek());
        System.out.println(DateUtil.getWeekOfYear() + "\t\t" + DateUtil.getLastWeekOfYear());
        System.out.println(DateUtil.getYear() + "\t\t" + DateUtil.getLastWeekYear());
        System.out.println("===");
        System.out.println(DateUtil.getDayOfWeek(d1));
        System.out.println(DateUtil.getWeekOfYear(d1) + "\t\t" + DateUtil.getLastWeekOfYear(d1));
        System.out.println(DateUtil.getYear(d1) + "\t\t" + DateUtil.getLastWeekYear(d1));
        System.out.println("===");
        System.out.println(DateUtil.getFirstDayOfWeek());
        System.out.println(DateUtil.getLastDayOfWeek());
        System.out.println(DateUtil.getFirstDayOfLastWeek());
        System.out.println(DateUtil.getLastDayOfLastWeek());

        System.out.println("===");
        System.out.println("DateUtil.getYesterdayFrom(): " + df_time.format(DateUtil.getYesterdayFrom()));
        System.out.println("DateUtil.getYesterdayTo(): " + df_time.format(DateUtil.getYesterdayTo()));
        System.out.println("DateUtil.getTodayFrom(): " + df_time.format(DateUtil.getTodayFrom()));

        System.out.println("DateUtil.getMonthOfYear(): " + DateUtil.getMonthOfYear());
        System.out.println("DateUtil.getMonthOfYear(): " + DateUtil.getMonthOfYear(df_time.parse("2018-12-30 00:00:00")));

        System.out.println("DateUtil.getFirstDayOfMonth(): " + df_time.format(DateUtil.getFirstDayOfMonth()));
        System.out.println("DateUtil.getLastDayOfMonth(): " + df_time.format(DateUtil.getLastDayOfMonth()));
        System.out.println("DateUtil.getFirstDayOfMonth(): " + df_time.format(DateUtil.getFirstDayOfMonth(df_time.parse("2016-02-12 00:00:00"))));
        System.out.println("DateUtil.getLastDayOfMonth(): " + df_time.format(DateUtil.getLastDayOfMonth(df_time.parse("2016-02-12 00:00:00"))));
    }

    public static int daysBetween(Date smdate, Date bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int getDayOfWeek() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            //sunday
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }

        return dayOfWeek;
    }

    public static int getDayOfWeek(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            //sunday
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }

        return dayOfWeek;
    }

    public static int getWeekOfYear() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            //sunday
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        return weekOfYear;
    }

    public static int getWeekOfYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            //sunday
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
            if (weekOfYear == 0)
                weekOfYear = 52;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        return weekOfYear;
    }

    public static int getMonthOfYear() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int monthOfYear = calendar.get(Calendar.MONTH) + 1;
        return monthOfYear;
    }

    public static int getMonthOfYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int monthOfYear = calendar.get(Calendar.MONTH) + 1;

        return monthOfYear;
    }

    public static int getLastWeekOfYear() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d.getTime() - 7 * 24 * 3600 * 1000L);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            //sunday
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        return weekOfYear;
    }

    public static int getLastWeekOfYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d.getTime() - 7 * 24 * 3600 * 1000L);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            //sunday
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
            if (weekOfYear == 0)
                weekOfYear = 52;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        return weekOfYear;
    }

    //获取本周第一天
    public static Date getFirstDayOfWeek() throws ParseException {
        Date d = new Date();
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(df_time.parse(df_date.format(d) + " 00:00:00"));
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    public static Date getFirstDayOfWeek(Date d) throws ParseException {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(df_time.parse(df_date.format(d) + " 00:00:00"));
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    //获取本周最后一天
    public static Date getLastDayOfWeek() throws ParseException {
        Date fistDate = getFirstDayOfWeek();
        Date lastDate = new Date(fistDate.getTime() + (7 * 24 * 3600 - 1) * 1000L);
        return lastDate;
    }

    public static Date getLastDayOfWeek(Date d) throws ParseException {
        Date fistDate = getFirstDayOfWeek(d);
        Date lastDate = new Date(fistDate.getTime() + (7 * 24 * 3600 - 1) * 1000L);
        return lastDate;
    }

    //获取上周第一天
    public static Date getFirstDayOfLastWeek() throws ParseException {
        Date now = new Date();
        Date d = new Date(now.getTime() - 7 * 24 * 3600 * 1000L);
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(df_time.parse(df_date.format(d) + " 00:00:00"));
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    //获取上周最后一天
    public static Date getLastDayOfLastWeek() throws ParseException {
        Date fistDate = getFirstDayOfLastWeek();
        Date lastDate = new Date(fistDate.getTime() + (7 * 24 * 3600 - 1) * 1000L);
        return lastDate;
    }

    //获取当前时间属于哪一年
    public static int getYear() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int year = calendar.get(Calendar.YEAR);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
            if (weekOfYear == 0)
                weekOfYear = 52;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        if (weekOfYear == 1) {
            //一年中第一周,既有可能是当年的第一周,也有可能是下一年的第一周
            calendar.setTimeInMillis(d.getTime() + 7 * 24 * 3600 * 1000L);
            int year_nextweek = calendar.get(Calendar.YEAR);
            if (year != year_nextweek)
                year = year_nextweek;
        }

        return year;
    }

    //获取此时间属于哪一年
    public static int getYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int year = calendar.get(Calendar.YEAR);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
            if (weekOfYear == 0)
                weekOfYear = 52;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        if (weekOfYear == 1) {
            //一年中第一周,既有可能是当年的第一周,也有可能是下一年的第一周
            calendar.setTimeInMillis(d.getTime() + 7 * 24 * 3600 * 1000L);
            int year_nextweek = calendar.get(Calendar.YEAR);
            if (year != year_nextweek)
                year = year_nextweek;
        }

        return year;
    }

    //获取当前时间上一周属于哪一年
    public static int getLastWeekYear() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d.getTime() - 7 * 24 * 3600 * 1000L);
        int year = calendar.get(Calendar.YEAR);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
            if (weekOfYear == 0)
                weekOfYear = 52;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        if (weekOfYear == 1) {
            //一年中第一周,既有可能是当年的第一周,也有可能是下一年的第一周
            calendar.setTimeInMillis(d.getTime() + 7 * 24 * 3600 * 1000L);
            int year_nextweek = calendar.get(Calendar.YEAR);
            if (year != year_nextweek)
                year = year_nextweek;
        }

        return year;
    }

    //获取此时间上一周属于哪一年
    public static int getLastWeekYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d.getTime() - 7 * 24 * 3600 * 1000L);
        int year = calendar.get(Calendar.YEAR);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (dayOfWeek == 1) {
            weekOfYear = weekOfYear - 1;
            dayOfWeek = 7;
            if (weekOfYear == 0)
                weekOfYear = 52;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        if (weekOfYear == 1) {
            //一年中第一周,既有可能是当年的第一周,也有可能是下一年的第一周
            calendar.setTimeInMillis(d.getTime() + 7 * 24 * 3600 * 1000L);
            int year_nextweek = calendar.get(Calendar.YEAR);
            if (year != year_nextweek)
                year = year_nextweek;
        }

        return year;
    }

    public static Date getYesterdayFrom() {
        Date d = null;
        try {
            d = df_time.parse(df_date.format(new Date(System.currentTimeMillis() - 24 * 3600 * 1000L)) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Date getYesterdayTo() {
        Date d = null;
        try {
            d = df_time.parse(df_date.format(new Date(System.currentTimeMillis() - 24 * 3600 * 1000L)) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Date getTodayFrom() {
        Date d = null;
        try {
            d = df_time.parse(df_date.format(new Date()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Date getTodayTo() {
        Date d = null;
        try {
            d = df_time.parse(df_date.format(new Date()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    //获取本月第一天
    public static Date getFirstDayOfMonth() throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天 
        String first = df_time.format(ca.getTime());
        return new Date(ca.getTimeInMillis());
    }

    //获取本月最后一天
    public static Date getLastDayOfMonth() throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(ca.getTimeInMillis());
    }

    //获取某时间当前月第一天
    public static Date getFirstDayOfMonth(Date d) throws ParseException {
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(d);

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, ca1.get(Calendar.YEAR));
        ca.set(Calendar.MONTH, ca1.get(Calendar.MONTH));

        //设置日历中月份的最大天数
        ca.set(Calendar.DAY_OF_MONTH, 1);

        return new Date(ca.getTimeInMillis());
    }

    //获取某时间当前月最后一天
    public static Date getLastDayOfMonth(Date d) throws ParseException {
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(d);

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, ca1.get(Calendar.YEAR));
        ca.set(Calendar.MONTH, ca1.get(Calendar.MONTH));

        //获取某月最大天数
        int lastDay = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        ca.set(Calendar.DAY_OF_MONTH, lastDay);

        return new Date(ca.getTimeInMillis());
    }

    // 01. java.util.Date --> java.time.LocalDateTime
    public static LocalDateTime UDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    // 02. java.util.Date --> java.time.LocalDate
    public static LocalDate UDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return  localDateTime.toLocalDate();
    }

    // 03. java.util.Date --> java.time.LocalTime
    public static LocalTime UDateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return  localDateTime.toLocalTime();
    }


    // 04. java.time.LocalDateTime --> java.util.Date
    public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    // 05. java.time.LocalDate --> java.util.Date
    public static Date LocalDateToUdate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    // 06. java.time.LocalTime --> java.util.Date
    public static Date LocalTimeToUdate(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

}
