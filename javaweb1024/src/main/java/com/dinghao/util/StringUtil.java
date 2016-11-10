package com.dinghao.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 作者：无 一些操作字符串的工具函数
 */
public class StringUtil {
	private static final String PASSWORD_CRYPT_KEY = "__Roc_a_";
private final static String DES = "DES";
public static boolean isCasNo(String cas){
      if(cas==null) return false;
      cas=cas.trim();
      if(!isNum(cas.replaceAll("-", ""))) return false;
      String s[]=cas.split("-");
      if(s.length!=3) return false;
      String numstr=s[0]+s[1];
      int num=0;
      int k=1;
      for(int i=numstr.length()-1;i>=0;i--){
	  num+=Integer.parseInt(String.valueOf(numstr.charAt(i)))*k;
	  k++;
      }
      return(num%10==Integer.parseInt(s[2]));
  }
 
/**
 * 得到当前的时间，时间格式跟食品机械网一样
 */
public static String getToday(){
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	String today=sdf.format(new Date());
	String[] tt=today.split("\\-");
	if(tt[1].startsWith("0")){
		tt[1]=tt[1].replaceFirst("0", "");
	}if(tt[2].startsWith("0")){
		tt[2]=tt[2].replaceFirst("0", "");
	}
	today=tt[0]+"-"+tt[1]+"-"+tt[2];
	return today;
}
/**
 * 对Sep 16,2011这种格式的日期进行修正
 */
	public static String getGoodDate(Map<String,Integer> map,String date){
		int kong_index=date.indexOf(" ");
		if(kong_index==-1)return "";
		String month=date.substring(0, kong_index);
		int m=map.get(month);
		
		int dou_index=date.indexOf(",");
		String year=date.substring(dou_index+1);
		
		String day=date.substring(kong_index+1, dou_index);
		date=year+"-"+m+"-"+day;
		return date;
		
	}
/**
 * 获得made-in-china的会员名称
 */
//http://shiboliangfood.en.made-in-china.com/product/yoaEfjHTOArX/China-Chestnut.html
//http://www.made-in-china.com/showroom/orientalchemwj/product-detailxopJzFecbSWP/China-Tea-Seed-Powder-NON-GM.html
public static String getUserName(String url){
	int sindex=0;
	int eindex=0;
	String username="";
	if(url.contains("/showroom/")){
		sindex=url.indexOf("/showroom/")+"/showroom/".length();
		eindex=url.indexOf("/", sindex);
		username=url.substring(sindex, eindex);
	}else{
		sindex=url.indexOf("//");
		eindex=url.indexOf(".en.made");
		username=url.substring(sindex+2, eindex);
	}
	return username;
}
	
	/**
	 * 得到两个日期格式的字符串的相差天数
	 */
	public static int getDate(String period,String createtime){
		if(period.equals("")||createtime.equals("")){
			return 30;
		}
		
		java.util.Date date=null;   
		java.util.Date mydate=null; 
		try{
		int index1=period.indexOf(" ");
		int index2=createtime.indexOf(" ");
		if(index1!=-1){
			period=period.substring(0, index1).trim();
		}
		if(index2!=-1){
			createtime=createtime.substring(0, index2).trim();
		}
		
		SimpleDateFormat myFormatter=new SimpleDateFormat("yyyy-MM-dd"); 

		date=myFormatter.parse(period);
		mydate=myFormatter.parse(createtime); 
		}catch(Exception e){
			e.printStackTrace();
			return 30;
		}
		long day=(date.getTime()-mydate.getTime())/(24*60*60*1000); 
		return (int)day;
	}
	/**
	 * 得到两个日期格式的字符串的相差天数,日期的格式不同
	 */
	public static int getDate1(String period,String createtime){
		if(period.equals("")||createtime.equals("")){
			return 30;
		}
		
		java.util.Date date=null;   
		java.util.Date mydate=null; 
		try{
		period=period.trim();
		createtime=createtime.trim();
		SimpleDateFormat myFormatter=new SimpleDateFormat("MM-dd-yyyy"); 
		date=myFormatter.parse(period);
		mydate=myFormatter.parse(createtime); 
		}catch(Exception e){
			e.printStackTrace();
			return 30;
		}
		long day=(date.getTime()-mydate.getTime())/(24*60*60*1000); 
		return (int)Math.abs(day);
	}
	
	/**
	 * 取得chemiclbook网站中所有的用途
	 */
	public static String getAllUsage(String content){
		List<String> lists=getalltagslist(content, "用途&nbsp;</td>", "</tr>");
		String usage="";
		for(String list:lists){
			list="用途&nbsp;</td>"+list+"</tr>";
			usage+=getFirststr(list, "用途&nbsp;</td>", "</tr>", 1)+";";
		}
		return usage;
	}
	/**
	 * 取得chemiclbook网站中所有的生产方法
	 */
	public static String getAllMethod(String content){
		List<String> lists=getalltagslist(content, "生产方法&nbsp;</td>", "</tr>");
		String method="";
		for(String list:lists){
			list="生产方法&nbsp;</td>"+list+"</tr>";
			method+=getFirststr(list, "生产方法&nbsp;</td>", "</tr>", 1)+";";
		}
		return method;
	}
 
	/**
	 * url 编码，加码，防止有空格之类url的不能访问，对它进行加码
	 * 
	 * @param src
	 *            对应javascript 中的escape()
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * url 解码，想看到加码后的url原来的地址，可以进行解码
	 * 
	 * @param src
	 *            对应javascript 中的unescape()
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 过滤introduce，盖德化工网的介绍
	 */
	public static String filterIntro(String introduce1){
		String introduce=introduce1;
		if(introduce.contains("<div class=\"bugbox\">")){
			introduce=introduce.replaceAll("<div class=\"bugbox\">", "");
		if(introduce.contains("<b>Profile: </b>")){
			introduce=introduce.replace("<b>Profile: </b>","");
		}
		}if(introduce.contains("</a>")){
			introduce=introduce.replaceAll("</a>", "");
		}
		  String flag="";
		  
	  while(introduce.contains("<a href=")){
		   flag=getFirststr(introduce, "<a href=","\">", 0);
		   flag="<a href="+flag+"\">";
		   introduce=introduce.replace(flag, "");
		   //System.out.println("flag:"+flag);
		   
		 }//System.out.println(introduce);
		   return introduce;
	}
	/**
	 * 去掉字符串中的连接
	 */
	public static String removeLink(String s){
		while(s.contains("<a href=")){
			String flag1=getFirststr(s, "<a href=","</a>", 1);
			String flag=getFirststr(s, "<a href=","</a>", 0);
			flag="<a href="+flag+"</a>";
			s=s.replace(flag, flag1);
		}
		if(s.contains("Back to Contents")){
			s=s.replaceAll("Back to Contents", "");
		}if(s.contains("回目录")){
			s=s.replaceAll("回目录", "");
		}if(s.contains("\"#Top\">")){
			s=s.replaceAll("\"#Top\">", "");
		}return s;
	}
    /**
     * 如果一个url有na_id,那么把它变成page
     */
	public static String changeURL(String s){
		if(s.contains("na_id")){
			s=s.replaceAll("na_id", "page");
		}
		int index=-1;
		index=s.indexOf("page=");
		s=s.substring(0, index+5);
		return s;
	}
	
	/**
	 * 把一些没用的东西去掉的方法，
	 */
	public static String removeGarbage(String s){
		int index=-1;
		if(s.contains("</span>")){
			index=s.indexOf("</span>");
			s=s.substring(index+7);
		}
		s=removeBR(s);
		return s;
	}
	/**
	 * 把</SPAN>去掉
	 */
	public static String removeSpan(String s){
		if(s.contains("</SPAN>")){
			s=s.replaceAll("</SPAN>", "");
		}if(s.contains("<SPAN>")){
			s=s.replaceAll("<SPAN>", "");
		}
		if(s.contains("</B>")){
			s=s.replaceAll("</B>", "");
		}
		return s;
	}
	/**
	 * 把&amp;替换成&
	 */
	public static String replaceAmp(String s){
		if(s.contains("&amp;")){
			s=s.replaceAll("&amp;", "&");
		}
		return s;
	}
	
	/** 
	 * 把"<"给去掉
	 */
	public static String removeXyh(String s){
		if(s.contains("<")){
			s=s.replaceAll("<", "");
			return s;
		}else return s;
	}
	
	/**
	 * 把用途前面两个字给去掉
	 */
	public static String delYongTu(String yongtu){
		int index=-1;
		if(yongtu.contains(yongtu)){
			index=yongtu.indexOf("用途");
			return yongtu.substring(index+1);
		}else return yongtu;
	}
	
	/**
	 * 把成分和制法给分开
	 */
	/*public static List<String> separateC_Z(String chengfen_zhizuo){
		int indexC=-1;
		int indexZ=-1;
		List<String> list=new ArrayList<String>();
		if(chengfen_zhizuo.contains("成分")){
			indexC=chengfen_zhizuo.indexOf("成分");
		}
		if(chengfen_zhizuo.contains("制法")){
			indexZ=chengfen_zhizuo.indexOf("制法");
		}
		if(indexC!=-1&&indexZ!=-1){
			list.add(chengfen_zhizuo.substring(indexC+2, indexZ));
			list.add(chengfen_zhizuo.substring(indexZ+2));
		}else if(indexC!=-1&&indexZ==-1){
			list.add(chengfen_zhizuo.substring(indexC+2));
			list.add("");
		}else if(indexC==-1&&indexZ!=-1){
			list.add("");
			list.add(chengfen_zhizuo.substring(indexZ+2));
		}else{
			list.add("");
			list.add("");
		}return list;
		
		
	}
	*/
	/**
	 * 去掉字符串中的<br>
	 */
	public static String removeBR1(String s){
		String ss=s;
		if(s!=null&&!s.equals("")){
		if(s.contains("<br>")){
			ss=s.replaceAll("<br>","");
		  }
		}
		return ss;
	}
	
	/**
	 * 去掉字符串中的<br />
	 */
	public static String removeBR(String s){
		String ss=s;
		if(s!=null&&!s.equals("")){
		if(s.contains("<br />")){
			ss=s.replaceAll("<br />","");
		  }
		}
		return ss;
	}
	
	
	/**
	 * 去掉字符串中的nbsp；
	 */
	public static String removeNbsp(String s){
		String ss=s;
		if(s!=null&&!s.equals("")){
		if(s.contains("&nbsp;")){
			ss=s.replaceAll("&nbsp;","");
		}
		if(ss.contains("&nbsp")){
			ss=ss.replaceAll("&nbsp","");		
		}
		if(ss.contains("&nbs")){//因为有些字符串会只剩下这四个字符，所以用它来判断
			ss=ss.replaceAll("&nbs","");		
		}
		}
		return ss;
	}
	
	/**
	 * 把相关限量属性的"<<<更多限量... 后面的字符串"给过滤掉
	 */
	public static String getXgxl(String s){
		String xgxl=s;
		if(xgxl.contains("<<<更多限量")){
		if(!s.equals("")&&s!=null){
			int index=s.indexOf("<<<更多限量");
			xgxl=s.substring(0, index-2);
			}
		}
		return xgxl;
	}
	/**
	 * 把相关讨论属性的"<<<更多阿维菌素标签讨论... 后面的字符串"给过滤掉
	 */
	public static String getXgtl(String s){
		String xgtl=s;
		if(xgtl.contains("<<<更多")){
		if(!s.equals("")&&s!=null){
			int index=s.indexOf("<<<更多");
			xgtl=s.substring(0, index-2);
			}
		}
		return xgtl;
	}
	
	/**
	 * 把" [进入食品百科查看-- 环丙嘧啶醇 的信息]"这段字符串给过滤掉
	 */
	public static String getChineseTName(String s){
		String chineseTname=s;
		if(chineseTname.contains("[进入食品百科查看")){
		if(!s.equals("")&&s!=null){
		int index=s.indexOf("[进入食品百科查看");
		chineseTname=s.substring(0, index-2);
		  }
		}
		chineseTname=removeNbsp(chineseTname);
		return chineseTname;
	}
	
	
	/**
	 * 一段有重复html标签的代码，我们需要把它们进行分割，把取得每个重复标签内的文字
	 * @param s
	 * @return
	 */
	public static String getString(String s){
		List<String> list=new ArrayList<String>();
		String newValue=s;
		while(newValue.length()>10){
		String str=StringUtil.getFirststr(newValue, "color='blue'>", "</font>", 1);	
		int restart=newValue.indexOf("</a>");
		newValue=newValue.substring(restart+4);
		list.add(str);
		}
		if(list.size()>0){
		String Value=list.get(0);//如果不这样的话，得到的Value值的第一个会为空
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Value = Value+(String) iterator.next()+",";	
		}
		Value=Value.substring(0, Value.length()-2);//把最后的逗号给去掉
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+Value);
		return Value;
		}else return newValue;
		
	}
/**
 *把InputStream转化为字符串
 * @param is
 * @return
 */	
	public static String convertStreamToString(InputStream is) {      
       BufferedReader reader =  new  BufferedReader( new  InputStreamReader(is));    
       StringBuilder sb =  new  StringBuilder();    
    
       String line =  null ;    
        try  {    
            while  ((line = reader.readLine()) !=  null ) {    
               sb.append(line +  "\n" );    
           }    
       }  catch  (IOException e) {    
           e.printStackTrace();    
       }  finally  {    
            try  {    
               is.close();    
           }  catch  (IOException e) {    
               e.printStackTrace();    
           }    
       }    
    
        return  sb.toString();    
   }    
  

	/**
	 * 传入一个url和数字,如果有url中有(*)，把它替换成改数字
	 * @param s
	 * @param x
	 * @return
	 */
	public static String changeURL(String s, int x) {
		String newValue = null;
		if(s.contains("(*)")){
		newValue = s.replace("(*)", String.valueOf(x));
		}else newValue=s;
		return newValue;
	}
	/**
	 * 取得自定义位数随机数
	 * 
	 * @param size
	 *            位数
	 * @return
	 */
	public static String RandomStr(int size) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < size; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}

		return sRand;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNum(String num) {
		try {
			Long.parseLong(num);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 取得文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName) {
		if (fileName == null)
			return "";
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	/**
	 * &amp;-->&
	 * 
	 * @param input
	 * @return
	 */
	public static String tohtml(String input) {
		input = replaceString(input, "&amp;", "&"); // & 仺 &amp;
		input = replaceString(input, "&lt;", "<"); // < 仺 &lt;
		input = replaceString(input, "&gt;", ">"); // > 仺 &gt;
		input = replaceString(input, "&quot;", "\""); // " 仺 &quot;
		input = replaceString(input, "&#39;", "'"); // ' 仺 &#39;
		return input;
	}

	/**
	 * > -->&gt;
	 * 
	 * @param input
	 * @return
	 */
	public static String Htmlclear(String input) {
		input = replaceString(input, "&", "&amp;"); // & 仺 &amp;
		input = replaceString(input, "<", "&lt;"); // < 仺 &lt;
		input = replaceString(input, ">", "&gt;"); // > 仺 &gt;
		input = replaceString(input, "\"", "&quot;"); // " 仺 &quot;
		input = replaceString(input, "'", "&#39;"); // ' 仺 &#39;
		return input;
	}

	/**
	 * 字符串替换
	 * 
	 * @param input
	 * @param befor
	 * @param after
	 * @return
	 */
	public static String replaceString(String input, String befor, String after) {
		befor = befor.toLowerCase();
		if (after.toLowerCase().indexOf(befor) != -1)
			return input;
		StringBuffer output = new StringBuffer(input);
		StringBuffer output1 = new StringBuffer(input.toLowerCase());
		String input1 = input.toLowerCase();
		int index = input1.indexOf(befor);
		while (index != -1) {
			output1.replace(index, index + befor.length(), after);
			output.replace(index, index + befor.length(), after);
			index = output1.toString().indexOf(befor, index);
		}
		return output.toString();
	}

	/**
	 * 用来替换以start开始，end结束的字符串
	 * 
	 * @param input
	 * @param start
	 * @param end
	 * @param str
	 * @return
	 */
	public static String replaceString(String input, String start, String end,
			String after) {
		start = start.toLowerCase();
		end = end.toLowerCase();
		if (after.toLowerCase().indexOf(start) != -1)
			return input;
		StringBuffer output = new StringBuffer(input);
		StringBuffer output1 = new StringBuffer(input.toLowerCase());
		String input1 = input.toLowerCase();
		int spoint = input1.indexOf(start);
		int epoint = input1.indexOf(end, spoint + 1);
		while (spoint != -1 && epoint != -1) {
			output1.replace(spoint, epoint + end.length(), after);
			output.replace(spoint, epoint + end.length(), after);
			spoint = output1.toString().indexOf(start);
			epoint = output1.toString().indexOf(end, spoint + 1);
		}
		return output.toString();
	}

	/**
	 * 返回以第一处以 start开始， end 结束字符串
	 * 
	 * @param input
	 * @param start
	 * @param end
	 * @param key
	 *            0-保留html标签 ，1 -除去html标签
	 * @return
	 */
	public static String getFirststr(String input, String start, String end,
			int key) {
		if (input == null)
			return "";
		String output = input.toLowerCase();
		start = start.toLowerCase();
		end = end.toLowerCase();
		if (output.indexOf(start) == -1 && start.indexOf("^^^") == -1)
			return "";
		String temp = "";
		int spoint = -1;
		if (!output.equals("")) {
			if (start.indexOf("^^^") == -1) {
				spoint = output.indexOf(start) + start.length();
				if (spoint != -1 && spoint < output.length())
					temp = output.substring(spoint, output.length());
			} else {
				String[] l = start.split("\\^\\^\\^");
				int tempsport = 0;
				temp = output;

				for (int i = 0; i < l.length; i++) {
					spoint = temp.indexOf(l[i]);
					if (spoint == -1)
						return "";
					spoint += l[i].length();
					if (spoint == -1 || spoint > temp.length())
						break;
					temp = temp.substring(spoint, temp.length());
					tempsport += spoint;
				}
				spoint = tempsport;
			}
		}
		int epoint = -1;
		if (!end.equals("")) {
			epoint = temp.indexOf(end);// + end.length();
		}
		if (spoint != -1 && epoint != -1
				&& (spoint + epoint <= output.length())) {
			output = input.substring(spoint, spoint + epoint);
		} else {
			output = "";
		}
		if (key == 1) {
			output = replaceString(output, "<", ">", "").replaceAll("\\n", "")
					.replaceAll("\\t", "").trim();
		}
		return output;
	}

	/**
	 * 取得某tag标识前倒数第一个符合条件的字符串
	 * 
	 * @param input
	 * @param tag
	 *            标识tag
	 * @param start
	 *            开始字符串
	 * @param end
	 *            结束字符串
	 * @return
	 */
	public static String getbefortagstr(String input, String tag, String start,
			String end) {
		if (input == null || tag == null)
			return "";
		tag = tag.toLowerCase();
		String output = input.toLowerCase();
		int pos = output.lastIndexOf(tag);
		if (pos == -1)
			return "";
		start = start.toLowerCase();
		end = end.toLowerCase();
		input = input.substring(0, pos);
		output = output.substring(0, pos);
		int spont = output.lastIndexOf(start);
		int epont = output.lastIndexOf(end);
		while (spont > epont) {
			output = output.substring(0, spont);
			input = input.substring(0, spont);
			spont = output.lastIndexOf(start);
			if (spont == -1) {
				break;
			}
		}
		if (spont == -1)
			return "";
		output = input.substring(spont, output.length());
		return output;
	}

	public static String getbefortagstrsub(String input, String tag,
			String start, String end) {
		input = getbefortagstr(input, tag, start, end);
		return getFirststr(input, start, end, 0);
	}

	public static String getnextpage(String input, String tag) {
		String temp = getbefortagstrsub(input, tag, "href=", ">");
		if (temp.equals(""))
			return "";
		String url = getbefortagstrsub(input, tag, "href=" + temp.charAt(0), ""
				+ temp.charAt(0));
		return url;
	}

	/**
	 * 取得输入文本 以 start 开始， end 结束的列表
	 * 
	 * @param input
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> getalltagslist(String input, String start,
			String end) {
		List<String> list = new LinkedList<String>();
		int i = 0;
		int index = 0;
		start = start.toLowerCase();
		end = end.toLowerCase();
		String input1 = input.toLowerCase();
		int endIndex = 0;
		while ((index = input1.indexOf(start, i)) != -1
				&& (endIndex = input1.indexOf(end, index + start.length())) != -1
				&& index < endIndex) {
			i = endIndex + 1;
			input1.substring(index + start.length(), endIndex);
			list.add(input.substring(index + start.length(), endIndex));
		}
		return list;
	}
	/**
	 * 取得输入文本 以 start 开始， end 结束的列表，传入一个不等于0的数后会过滤掉html数据
	 * 
	 * @param input
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> getalltagslist(String input, String start,
			String end,int flag) {
		if(flag==0){
			return getalltagslist(input, start, end);
		}
		List<String> list = new LinkedList<String>();
		int i = 0;
		int index = 0;
		start = start.toLowerCase();
		end = end.toLowerCase();
		String input1 = input.toLowerCase();
		int endIndex = 0;
		while ((index = input1.indexOf(start, i)) != -1
				&& (endIndex = input1.indexOf(end, index + start.length())) != -1
				&& index < endIndex) {
			i = endIndex + 1;
			input1.substring(index + start.length(), endIndex);
			list.add(input.substring(index + start.length(), endIndex).replaceAll("<.+?>", ""));
		}
		return list;
	}
	/**
	 * 提取纯文本信息
	 * 
	 * @param input
	 * @return
	 */
	public static String noHtmltags(String input) {
		input = replaceString(input, "<script", "</script>", "");
		input = replaceString(input, "<style", "</style>", "");
		input = replaceString(input, "<!--", "-->", "");
		input = replaceString(input, "<embed", "</embed>", "");
		input = replaceString(input, "<NOEMBED>", "</NOEMBED>", "");
		input = replaceString(input, "<NOSCRIPT>", "</NOSCRIPT>", "");
		input = replaceString(input, "<", ">", "");
		input = input.replaceAll("<", "");
		input = input.replaceAll(">", "");
		input = input.replaceAll("&nbsp;", "");
		return input;
	}

	/**
	 * 提取纯文本信息
	 * 
	 * @param input
	 * @return
	 */
	public static String noObjSrc(String input) {
		input = replaceString(input, "<script", "</script>", "");
		input = replaceString(input, "<style", "</style>", "");
		input = replaceString(input, "<!--", "-->", "");
		input = replaceString(input, "<embed", ">", "");
		input = replaceString(input, "<iframe", "</iframe>", "");
		input = replaceString(input, "</embed>", "");
		input = replaceString(input, "<NOEMBED>", "</NOEMBED>", "");
		input = replaceString(input, "<object", "</object>", "");
		input = replaceString(input, "<NOSCRIPT>", "</NOSCRIPT>", "");
		input = input.replaceAll("<>", "");
		return input;
	}

	/**
	 * 功能：日期转换 参数： String 返回： String
	 */
	public static String formatDate(String date) {
		if (date == null || date.equals(""))
			return "";
		date = codatestr(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(Timestamp.valueOf(date));
	}

	public static String formatDate(String date, String style) {
		return formatDate(date, style, "");
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @param style：yyyy/MM/dd
	 *            HH:mm:ss
	 * @return
	 */
	public static String formatDate(String date, String style, String language) {
		if (date == null || date.equals(""))
			return "";
		if (style.equals(""))
			style = "yyyy-MM-dd";
		date = codatestr(date);
		Locale locale = new Locale(language);
		SimpleDateFormat sdf = new SimpleDateFormat(style, locale);
		return sdf.format(Timestamp.valueOf(date));
	}

	private static String codatestr(String datein) {
		if (datein.length() <= 10) {
			datein = datein + " 00:00:000";
		}
		return datein;
	}

	/**
	 * 检查域名是否有效。
	 * 
	 * @param domain
	 * @return
	 */
	public static boolean isdomain(String domain) {
		if (domain.length() == 0 || domain.charAt(0) == '-'
				|| domain.charAt(domain.length() - 1) == '-') {
			return false;
		}
		if (domain.charAt(0) == '.'
				|| domain.charAt(domain.length() - 1) == '.') {
			return false;
		}
		String strSource = "-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int p = 0;
		for (int i = 0; i < (domain.length()); i++) {
			p = strSource.indexOf(domain.charAt(i));
			if (p == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 功能：得到当前时间 返回： String
	 */
	public static String getCurformatTime() {
		String datestr = "";
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datestr = sdf.format(time);
		return datestr;
	}

	/**
	 * 返回指定格式化后的当前日期
	 * 
	 * @param stly=yyyy-MM-dd
	 *            HH:mm:ss
	 * @return
	 */
	public static String getCurformatTime(String stly) {
		String datestr = "";
		Date time = new Date();
		if (stly == null || stly.equals(""))
			stly = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(stly);
		datestr = sdf.format(time);
		return datestr;

	}

	/**
	 * 
	 * 功能：数字编辑,传入".2"形式的字符串，返回"0.2" 参数： String 返回： String
	 * 
	 * @param numerical_value
	 * @param style
	 *            0.####
	 * @return
	 */
	public static String numberformat(String numerical_value, String style) {
		if (style == null || style.equals(""))
			style = "0.####";
		if (numerical_value.equals(""))
			return "";

		DecimalFormat df = new DecimalFormat(style);

		Double num = new Double(numerical_value);

		return (df.format(num.doubleValue())).toString();
	}

	/**
	 * 截取字符串 取前len/2个汉字，一个汉字占两个字节
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String maxLenString(String str, int len) {
		return maxLenString(str, len, "...");
	}

	/**
	 * 截取字符串 取前len/2个汉字，一个汉字占两个字节
	 * 
	 * @param str
	 * @param len
	 * @param zhui
	 *            ...
	 * @return
	 */
	public static String maxLenString(String str, int len, String zhui) {
		if (len == 0)
			return str;
		if (str != null) {
			str = noHtmltags(str);
			StringBuffer output = new StringBuffer();
			String mstr = "";
			int t = 0;
			int l = str.length();
			for (int i = 0; i < l; i++) {
				mstr = str.substring(i, i + 1);
				t += mstr.getBytes().length;
				if (t <= len) {
					output.append(mstr);
				} else {
					i = l;
					output.append(zhui);
				}
			}
			return output.toString();
		} else {
			return "";
		}
	}


	/**
	 * 判断字符串是否为时间格式
	 * 
	 * @param timestr
	 * @param str
	 * @return
	 */
	public static boolean isDate(String timestr, String str) {
		if (str.equals(""))
			str = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		try {
			if (sdf.format(sdf.parse(timestr)).equalsIgnoreCase(timestr))
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null)
			return false;
		email = email.trim();
		if (email.indexOf(' ') != -1)
			return false;

		int idx = email.indexOf('@');
		if (idx == -1 || idx == 0 || (idx + 1) == email.length())
			return false;
		if (email.indexOf('@', idx + 1) != -1)
			return false;
		if (email.indexOf('.') == -1)
			return false;
		return true;
	}

	public static String getsmallimg(Object obj) {
		String img = (String) obj;
		if (img == null || img.length() < 10)
			return "/images/img_no.jpg";
		return img;
	}

	public static String getbigimg(Object obj) {
		String img = (String) obj;
		if (img == null || img.length() < 10)
			return "/images/img_no.jpg";
		return img;
	}

	public static String keywordFilter(String keyword) {
		keyword = keyword.replaceAll("\\\\", "");
		keyword = keyword.replaceAll("\\+", "");
		keyword = keyword.replaceAll("-", "");
		keyword = keyword.replaceAll("&&", "");
		keyword = keyword.replaceAll("\\|\\|", "");
		keyword = keyword.replaceAll("!", "");
		keyword = keyword.replaceAll("\\(", "");
		keyword = keyword.replaceAll("\\)", "");
		keyword = keyword.replaceAll("\\{", "");
		keyword = keyword.replaceAll("\\}", "");
		keyword = keyword.replaceAll("\\[", "");
		keyword = keyword.replaceAll("\\]", "");
		keyword = keyword.replaceAll("\\^", "");
		keyword = keyword.replaceAll("\"", "");
		keyword = keyword.replaceAll("\\~", "");
		keyword = keyword.replaceAll("\\*", "");
		keyword = keyword.replaceAll("\\?", "");
		keyword = keyword.replaceAll("\\:", "");
		return keyword;
	}

	/**
	 * 取得字符串中的Email 地址
	 * 
	 * @param line
	 * @return
	 */
	public static Vector<String> getEmaillist(String content) {
		Pattern p = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
		Matcher m = p.matcher(content);
		Vector<String> v = new Vector<String>();
		String email = "";
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				email = m.group(i);
				if (!v.contains(email))
					v.add(email);
			}
		}
		return v;
	}

	/**
	 * 取得内容中符合 regex 列表 不重复
	 * 
	 * @param content
	 * @param regex
	 * @return
	 */

	public static ArrayList<String> getMatchlist(String content, String regex) {
		return getMatchlist(content, regex, false);
	}

	/**
	 * 取得内容中符合 regex 列表
	 * 
	 * @param content
	 * @param regex
	 * @paramr epeat 是否允许重复
	 * @return
	 */
	public static ArrayList<String> getMatchlist(String content, String regex,
			boolean repeat) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		ArrayList<String> v = new ArrayList<String>();
		String str = "";
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				str = m.group(i);
				if (repeat) {
					v.add(str);
				} else {
					if (!v.contains(str))
						v.add(str);
				}
			}
		}
		return v;
	}

	

	/**
	 * 判断 charset 是否是正确的字符编码
	 * 
	 * @param input
	 * @return
	 */
	public static boolean ischarset(String charset) {
		if (charset == null || charset.equals(""))
			return false;
		boolean isok = true;
		int n = charset.length();
		for (int i = 0; i < n; i++) {
			char c = charset.charAt(i);
			if (c >= 'A' && c <= 'Z')
				continue;
			if (c >= 'a' && c <= 'z')
				continue;
			if (c >= '0' && c <= '9')
				continue;
			if (c == '-' && i != 0)
				continue;
			if (c == ':' && i != 0)
				continue;
			if (c == '_' && i != 0)
				continue;
			if (c == '.' && i != 0)
				continue;
			isok = false;
		}
		if (isok) {
			isok = Charset.isSupported(charset);
		}
		return isok;
	}

	public static String formaturl(String input) {
		input = tohtml(input);
		String outstr = input.trim().replaceAll(" ", "-")
				.replaceAll("\\\"", "").replaceAll("\\?", "").replaceAll("'",
						"").replaceAll("%", "").replaceAll("#", "-")
				.replaceAll("\\+", "-").replaceAll("@", "").replaceAll(";", "")
				.replace("%", "");
		return outstr;
	}

	/**
	 * sql 注入过滤
	 * 
	 * @param input
	 * @return
	 */
	public static String Filtersql(String input) {
		if (input == null)
			return "";
		input = input.replaceAll(",", "''");
		input = input.replaceAll(";", "；");
		input = replaceString(input, "select", "ｓelect");
		input = replaceString(input, "exec ", "ｅxec ");
		input = replaceString(input, "declare", "ｄeclare");
		input = replaceString(input, "set @", "set ＠");
		input = replaceString(input, "master.", "master·");
		return input;
	}

	/**
	 * @param h
	 * @return 实现对map按照value升序排序
	 */
	@SuppressWarnings("unchecked")
	public static Map.Entry[] getSortedHashtableByValue(Map h) {
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Long key1 = Long.valueOf(((Map.Entry) arg0).getValue()
						.toString());
				Long key2 = Long.valueOf(((Map.Entry) arg1).getValue()
						.toString());
				return key1.compareTo(key2);
			}
		});
		return entries;
	}

	/**
	 * @param h
	 * @return 实现对map按照key排序
	 */
	@SuppressWarnings("unchecked")
	public static Map.Entry[] getSortedHashtableByKey(Map h) {
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Object key1 = ((Map.Entry) arg0).getKey();
				Object key2 = ((Map.Entry) arg1).getKey();
				return ((Comparable) key1).compareTo(key2);
			}
		});
		return entries;
	}

	// ===========================================================================================
	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	/**
	 * 数据解密
	 * 
	 * @param data
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public final static String decrypt(String data, String key) {
		if (data != null)
			try {
				return new String(decrypt(hex2byte(data.getBytes()), key
						.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 数据加密
	 * 
	 * @param data
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public final static String encrypt(String data, String key) {
		if (data != null)
			try {
				return byte2hex(encrypt(data.getBytes(), key.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decryptPassword(String data) {
		if (data != null)
			try {
				return new String(decrypt(hex2byte(data.getBytes()),
						PASSWORD_CRYPT_KEY.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final static String encryptPassword(String password) {
		if (password != null)
			try {
				return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY
						.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * BASE64编码
	 * 
	 * @param s
	 * @return String
	 */
	
	/**
	 * BASE64反编码
	 * 
	 * @param bytes
	 * @return byte[]
	 */
	
	/**
	 * BASE64编码
	 * 
	 * @param s
	 * @return String
	 */
	

	/**
	 * BASE64反编码
	 * 
	 * @param s
	 * @return String
	 */
	
	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 根据汉字字符获得笔画数,拼音和非法字符默认为0
	 * 
	 * @param charcator
	 * @return int
	 */
	

	/**
	 * @param highByte
	 *            高位字节
	 * @param lowByte
	 *            低位字节
	 * @return int
	 */
	

	/**
	 * 该方法返回一个字符串的拼音，对于要做敏感字 检查时应该一个字一个字来获取其拼音以免无法 得知每个字对应的拼音。
	 * 
	 * @param word
	 * @return String
	 */
	

	/**
	 * 该方法返回一个字符的DBCS编码值
	 * 
	 * @param cc
	 * @return int
	 */
	protected static int getCode(char cc) {
		byte[] bs = String.valueOf(cc).getBytes();
		int code = (bs[0] << 8) | (bs[1] & 0x00FF);
		if (bs.length < 2)
			code = (int) cc;
		bs = null;
		return code;
	}
	
	/**
	 * 左补齐 <br>
	 * @param @param length 补齐位数
	 * @param @param number 补齐数值
	 * @param @return   
	 * @return String
	 */
	public final static String lpad(Long length, Long number) {
		String f = "%0" + length + "d";
        return String.format(f, number);
	}
	
	/**
	 * 验证字符串为空 <br>
	 * @param @param str
	 * @param @return   
	 * @return boolean
	 */
	public final static boolean isEmpty(String str) {
		if(str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 该方法通过DBCS的编码值到哈希表中查询得到对应的拼音串
	 * 
	 * @param hz
	 * @return String
	 */
	
	/**
	 * 用户名必须是数字或者字母的结合
	 * 
	 * @param username
	 * @return
	

	/**
	 * 判断是否是字母和数字的结合
	 * 
	 * @param name
	 * @return
	 */
	
	public static String getEncoding(String str) {      
	    String encode = "GB2312";      
        try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
               String s = encode;      
              return s;      
           }      
        } catch (Exception exception) {      
        }      
        encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   }      
}