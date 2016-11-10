package com.dinghao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/**
	 * ��ʽ������
	 * 
	 * @param dateStr
	 *            �ַ�������
	 * @param format
	 *            ��ʽ
	 * @return ��������
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
	 * �õ������ʱ�䡣�賿��ʼ
	 */
	public static String getToday(){
		return format(new Date(), "yyyy-MM-dd")+" 00:00:00";
	}
	/**
	 * ��ʽ���������
	 * 
	 * @param date
	 *            ����
	 * @param format
	 *            ��ʽ
	 * @return �����ַ�������
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
	 * �������
	 * 
	 * @param date
	 *            ����
	 * @return �������
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * �����·�
	 * 
	 * @param date
	 *            ����
	 * @return �����·�
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * �����շ�
	 * 
	 * @param date
	 *            ����
	 * @return �����շ�
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * ����Сʱ
	 * 
	 * @param date
	 *            ����
	 * @return ����Сʱ
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * ���ط���
	 * @param date
	 * ����
	 * @return ���ط���
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * ��������
	 * 
	 * @param date
	 *            ����
	 * @return ��������
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	/**
	 * ���غ���
	 * 
	 * @param date
	 *            ����
	 * @return ���غ���
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * �����ַ�������
	 * 
	 * @param date
	 *            ����
	 * @return �����ַ�������
	 */
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * �����ַ���ʱ��
	 * 
	 * @param date
	 *            ����
	 * @return �����ַ���ʱ��
	 */
	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}
	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}


	/**
	 * �������
	 * 
	 * @param date
	 *            ����
	 * @param day
	 *            ����
	 * @return ������Ӻ������
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * �������
	 * 
	 * @param date
	 *            ����
	 * @param date1
	 *            ����
	 * @return ����date - date1 ����������
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}
	
	/**
	 * �ж������Ƿ�����ڿ���
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
	 * ��������������������� today-datestr
	 * @param today
	 * @param datestr
	 * @return
	 */
	public static int diffDate(String datestr){
		
		return (int) ((getMillis(new Date()) - getMillis(getDate(datestr))) / (24 * 3600 * 1000));
	}
	/**
	 * 
	 * @param datestr ʱ��������
	 * @return
	 */
	public static Long diffDateBySecond(String datestr){
        //����ʱ���뵱ǰʱ���������
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
	 * ��ȡ��ǰ��� <br>
	 * @param @return   
	 * @return String
	 * @throws
	 */
	public static String getCurYear() {
		return DateUtil.getFormatCurTime("yyyy");
	}
	
	/**
	 * ��ȡ��ǰ�·� <br>
	 * @param @return   
	 * @return String
	 * @throws
	 */
	public static String getCurMonth() {
		return DateUtil.getFormatCurTime("MM");
	}
	
	/**
	 * ��ȡ��ǰ���� <br>
	 * @param @return   
	 * @return String
	 */
	public static String getCurDay() {
		return DateUtil.getFormatCurTime("dd");
	}
	
	/**
	 * ��ȡ��ǰ���ڡ� <br>
	 * @param format ��ʽ(���磺yyyyMMdd yyyy/MM/dd HH:MM:ss)
	 * @return String - ���ص�ǰ����
	 */
	public static String getFormatCurTime(String format) {
		SimpleDateFormat dataFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String dateString = dataFormat.format(date);
		return dateString;
	}
	
	/**
	  * @Title: getCurrentTimeMillis
	  * @Description: ��ȡ��ǰϵͳʱ���
	  * @param @return
	  * @return long
	  * @throws
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	/**
	 * ��ȡ��ǰ���ڡ� <br>��ʽ yyyyMMdd
	 * @return String - ���ص�ǰ����
	 */
	public static String getCurDate() {
		return DateUtil.getFormatCurTime("yyyyMMdd");
	}
	
}
