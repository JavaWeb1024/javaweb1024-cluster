package com.dinghao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/**
	 * 格式化日期
	 * 
	 * @param dateStr
	 *            字符型日期
	 * @param format
	 *            格式
	 * @return 返回日期
	 */

	public static java.util.Date parseDate(java.sql.Date date) {
		return date;
	}

	public static java.sql.Date parseSqlDate(java.util.Date date) {
		if (date != null)
			return new java.sql.Date(date.getTime());
		else
			return null;
	}
	/**
	 * 得到当天的时间。凌晨开始
	 */
	public static String getToday(){
		return format(new Date(), "yyyy-MM-dd")+" 00:00:00";
	}
	/**
	 * 格式化输出日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static String format(java.util.Date date, String stly) {
	    Locale locale =null;
	    if(stly.indexOf("MMM")!=-1){
		locale= new Locale("en");
	    	}
		String result = "";
		String str="yyyy-MM-dd HH:mm:ss";
		if(stly!=null&&!stly.equals(""))str=stly;
		try {
			if (date != null) {
			    	if(locale==null){
				java.text.DateFormat df = new java.text.SimpleDateFormat(str);
				result = df.format(date);
			    	}else{
			    	java.text.DateFormat df = new java.text.SimpleDateFormat(str,locale);
				result = df.format(date); 
			    	}
				
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String format(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * @param date
	 * 日期
	 * @return 返回分钟
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期
	 */
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 返回字符型时间
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型时间
	 */
	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}
	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}


	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回date - date1 相减后的日期
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}
	
	/**
	 * 判断日期是否比现在靠后
	 * @param date
	 * @return
	 */
	public static boolean isbefortoday(Date date){
		boolean ok=true;
		if(getMillis(date)-getMillis(new Date())>60000){
			ok=false;
		}
		return ok;
	}
	
	
	
	/**
	 * 返回日期与今天相差的天数 today-datestr
	 * @param today
	 * @param datestr
	 * @return
	 */
	public static int diffDate(String datestr){
		
		return (int) ((getMillis(new Date()) - getMillis(getDate(datestr))) / (24 * 3600 * 1000));
	}
	/**
	 * 
	 * @param datestr 时间总秒数
	 * @return
	 */
	public static Long diffDateBySecond(String datestr){
        //发布时间与当前时间相差天数
		return (DateUtil.getMillis(new Date())/1000 - Long.parseLong(datestr)) / (24 * 3600);
	}
	public static boolean isSameDay(java.util.Date date, java.util.Date date1) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date);
		c2.setTime(date1);
		int c1_day = c1.get(Calendar.DAY_OF_YEAR);
		int c1_y = c1.get(Calendar.YEAR);

		int c2_day = c2.get(Calendar.DAY_OF_YEAR);
		int c2_y = c2.get(Calendar.YEAR);
		
		if(c1_day == c2_day && c1_y == c2_y) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static Date getDate(String datestr) {
		String format="";
			if(datestr.length()<=10) {
				format="yyyy-MM-dd";
			}else {
				format="yyyy-MM-dd HH:mm:ss";
			}
		SimpleDateFormat sd=new SimpleDateFormat(format);
		try {
			Date d = sd.parse(datestr);
			return d;
		} catch (ParseException e) {
			return null;
		}
		
	}
	
	/**
	 * 获取当前年份 <br>
	 * @param @return   
	 * @return String
	 * @throws
	 */
	public static String getCurYear() {
		return DateUtil.getFormatCurTime("yyyy");
	}
	
	/**
	 * 获取当前月份 <br>
	 * @param @return   
	 * @return String
	 * @throws
	 */
	public static String getCurMonth() {
		return DateUtil.getFormatCurTime("MM");
	}
	
	/**
	 * 获取当前日期 <br>
	 * @param @return   
	 * @return String
	 */
	public static String getCurDay() {
		return DateUtil.getFormatCurTime("dd");
	}
	
	/**
	 * 获取当前日期。 <br>
	 * @param format 格式(比如：yyyyMMdd yyyy/MM/dd HH:MM:ss)
	 * @return String - 返回当前日期
	 */
	public static String getFormatCurTime(String format) {
		SimpleDateFormat dataFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String dateString = dataFormat.format(date);
		return dateString;
	}
	
	/**
	  * @Title: getCurrentTimeMillis
	  * @Description: 获取当前系统时间戳
	  * @param @return
	  * @return long
	  * @throws
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取当前日期。 <br>格式 yyyyMMdd
	 * @return String - 返回当前日期
	 */
	public static String getCurDate() {
		return DateUtil.getFormatCurTime("yyyyMMdd");
	}
	
}
