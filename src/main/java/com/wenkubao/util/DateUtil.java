package com.wenkubao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil(){
        throw new IllegalAccessError("Utility class");
    }

    public static synchronized Timestamp getNowTimeStamp() {
        return new Timestamp(getTime());
    }

    public static int day() {
        Calendar now = getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取系统当前日期,返回long类型格式数据
     *
     * @return
     */
    public static long getTime() {
        Calendar now = getInstance();
        return now.getTimeInMillis();
    }

    /**
     * 获取系统距离今天的日期,如day为-1,则返回一天前的当前时间
     *
     * @param day
     * @return 返回long类型格式数据
     */
    public static long getTimeSpecifyDay(int day) {
        Calendar now = getInstance();
        now.add(Calendar.DATE, day);
        return now.getTimeInMillis();
    }

    /**
     * 获取系统距离今天的日期,hour为-1,则返回1小时前的当前时间
     *
     * @param hour
     * @return 返回long类型格式数据
     */
    public static long getTimeSpecifyHour(int hour) {
        Calendar now = getInstance();
        now.add(Calendar.HOUR, hour);
        return now.getTimeInMillis();
    }

    /**
     * 获取系统距离今天的日期,min-1,则返回1分钟前的当前时间
     *
     * @param min
     * @return 返回long类型格式数据
     */
    public static long getTimeSpecifyMin(int min) {
        Calendar now = getInstance();
        now.add(Calendar.MINUTE, min);
        return now.getTimeInMillis();
    }

    private static Calendar getInstance() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
    }

    public static String getStringTime() {
        return getTime() + "";
    }

    public static String getFormatDate(String pattern) {
        Calendar now = getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(now.getTime());
    }

    public static String getFormatDate(String pattern, int year) {
        Calendar now = getInstance();
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(now.getTime());
    }


    public static String getFormatDateMonth(String pattern, int month) {
        Calendar now = getInstance();
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(now.getTime());
    }

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            LOGGER.error("系统异常:" + e.getMessage());
        }
        return null;
    }


    public static Long getDayStart() {
        Calendar calendar = getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime().getTime();
    }

    public static Long getDayEnd() {
        Calendar calendar = getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime().getTime();
    }

    public static String getDateFormatToday(String pattern) {
        Calendar now = getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(now.getTime());
    }

    public static String getDateFormatYesterDay(String pattern) {
        Calendar now = getInstance();
        now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(now.getTime());
    }

    public static Long getDateBeforeMinutes(Long date, int minute) {
        Date temp = new Date(date);
        Calendar calendar = getInstance();
        calendar.setTime(temp);
        calendar.add(Calendar.MINUTE, -minute);
        return calendar.getTimeInMillis();
    }

    public static Long getDateAfterMinutes(Long date, int minute) {
        return getDateBeforeMinutes(date, -minute);
    }

    /**
     * 加Long数据(秒数)转为对应的Calendar对象
     *
     * @param time
     * @return
     */
    private static Calendar getCalendarByLong(Long time) {
        Date date = new Date(time);
        Calendar now = getInstance();
        now.setTime(date);
        return now;
    }

    /**
     * 比较两个long类型日期是否是同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isTheSameDay(Long time1, Long time2) {
        Calendar day1 = getCalendarByLong(time1);
        Calendar day2 = getCalendarByLong(time2);
        int day1Year = day1.get(Calendar.YEAR);
        int day2Year = day2.get(Calendar.YEAR);
        if (day1Year != day2Year) {
            return false;
        }
        int day1Day = day1.get(Calendar.DAY_OF_YEAR);
        int day2Day = day2.get(Calendar.DAY_OF_YEAR);
        if (day1Day != day2Day) {
            return false;
        }
        return true;
    }

    /**
     * 比较time1比time2是不是早step天
     *
     * @param time1
     * @param time2
     * @param step
     * @return
     */
    public static boolean compareByStep(Long time1, Long time2, int step) {
        Calendar day1 = getCalendarByLong(time1);
        Calendar day2 = getCalendarByLong(time2);
        int day1Day = day1.get(Calendar.DAY_OF_YEAR);
        int day2Day = day2.get(Calendar.DAY_OF_YEAR);
        if (day2Day - day1Day == step) {
            return true;
        }
        return false;
    }

    /**
     * 24小时制,返回time1小时数，比time2小时数早几小时
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compareByHour(Long time1, Long time2) {
        Calendar day1 = getCalendarByLong(time1);
        Calendar day2 = getCalendarByLong(time2);
        int hour1 = day1.get(Calendar.HOUR_OF_DAY);
        int hour2 = day2.get(Calendar.HOUR_OF_DAY);
        if (hour2 - hour1 < 0) {
            return -1;
        }
        return hour2 - hour1;
    }

    /**
     * 60分钟制,返回time1分钟数，比time2分钟数早几分钟
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compareByMinute(Long time1, Long time2) {
        Calendar day1 = getCalendarByLong(time1);
        Calendar day2 = getCalendarByLong(time2);
        int minute1 = day1.get(Calendar.MINUTE);
        int minute2 = day2.get(Calendar.MINUTE);
        if (minute2 - minute1 < 0) {
            return -1;
        }
        return minute2 - minute1;
    }

    public static String dateToString(Date date, String formatType){
        return new SimpleDateFormat(formatType).format(date);
    }

    public static Date stringToDate(String strTime, String formatType) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static Date longToDate(long currentTime, String formatType) throws ParseException{
        Date dateOld = new Date(currentTime);
        String sDateTime = dateToString(dateOld, formatType);
        Date date = stringToDate(sDateTime, formatType);
        return date;
    }

    public static String longToString(long currentTime, String formatType) throws ParseException{
        Date date = longToDate(currentTime, formatType);
        String strTime = dateToString(date, formatType);
        return strTime;
    }

    public static String getLastMonthDate(String date) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.add(Calendar.MONTH, -1);
        return dateFormat.format(calendar.getTime());
    }

    public static String getLastMonthDateOnlyMonth(String date) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.add(Calendar.MONTH, -1);
        return dateFormat.format(calendar.getTime());
    }

    public static String getMinMonthDate(String date) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    public static Long getMinMonthDateToLong(String date) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(getMinMonthDate(date));
        return date1.getTime();
    }

    public static String getMaxMonthDate(String date) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.add(calendar.DATE,1);
        return dateFormat.format(calendar.getTime());
    }

    public static Long getMaxMonthDateToLong(String date) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(getMaxMonthDate(date));
        return date1.getTime();
    }

    public static void main(String[] args) {
        try{
//            System.out.println(longToString(Long.valueOf("1502791200000"), "yyyy-MM"));
            String month = DateUtil.getLastMonthDateOnlyMonth(DateUtil.longToString(Long.valueOf("1502791200000"), "yyyy-MM"));
            String dateStr = DateUtil.getLastMonthDate(DateUtil.longToString(Long.valueOf("1502791200000"), "yyyy-MM-dd"));
            Long minDate = DateUtil.getMinMonthDateToLong(dateStr);
            Long maxDate = DateUtil.getMaxMonthDateToLong(dateStr);
            System.out.println(month);
            System.out.println(dateStr);
            System.out.println(minDate);
            System.out.println(maxDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
//        long time1 = getTime();
//        long time2= getTime();
//        int i = compareByMinute(time1, time2);
//        System.out.println(i);
    }

}