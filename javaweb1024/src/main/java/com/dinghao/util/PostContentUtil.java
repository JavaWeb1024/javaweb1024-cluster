package com.dinghao.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostContentUtil {//post�����ʱ��õ�������
public static void main(String[] args) {

}
static private String cookie = null;
public static String openurl(String url,String charset,String postParam,String proxy){
	boolean isproxy=true;
	String p[]={"",""};
	if(proxy==null||proxy.equals("")||proxy.indexOf(":")==-1){
		isproxy=false;
	}else{
		p=proxy.split(":");
		if(Integer.parseInt(p[1])>65535)isproxy=false;
	}
	String content="";
	if(charset==null||charset.equals(""))charset="gb2312";
	try {
		URL source = new URL(url);
		URLConnection c=null;
		
		if(isproxy){
			InetSocketAddress addr = new InetSocketAddress(p[0],Integer.parseInt(p[1]));
            Proxy px = new Proxy(Proxy.Type.HTTP, addr); // http ����
			c=source.openConnection(px);
		}else{
			c=source.openConnection();
		}
		c.setConnectTimeout(20000);
		c.setReadTimeout(20000);
		c.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
//		c.setRequestProperty("X-Forwarded-For",p[0]);
//		c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0) Gecko/20100101 Firefox/5.0");
//		c.setRequestProperty("Host","www.foodszs.com");
//		c.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		c.setRequestProperty("Accept-Language","zh-cn,zh;q=0.5");
//		c.setRequestProperty("Accept-Encoding","gzip, deflate");
//		c.setRequestProperty("Accept-Charset","GB2312,utf-8;q=0.7,*;q=0.7");
//		c.setRequestProperty("Connection","keep-alive");
//		c.setRequestProperty("Referer","http://www.guidechem.com/product/list_catid-"+cc+++".html");
		
//		gettingOrSettingCookie(c,true);//����set cookie
		OutputStreamWriter out =null;
		if(postParam!=null&&!postParam.trim().equals("")){
		     c.setDoOutput(true);
		     out=new OutputStreamWriter(c.getOutputStream(), charset);
		     out.write(postParam);
		     out.flush();
		    }
		c.connect();
		InputStream in = c.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line = new String();
		StringBuffer temp = new StringBuffer(1024);
		while ((line = reader.readLine()) != null) {
			temp.append(line);
			temp.append(System.getProperty("line.separator"));
		}
		
		reader.close();
		in.close();
		if(out!=null)out.close();
		c=null;
		content = temp.toString();
		
	}  catch (MalformedURLException e) {
		System.out.println(e.getMessage());
		System.out.println("Unable to connect to URL: " + url);
	}catch (IOException e) {
		System.out.println(e.getMessage());
	    System.out.println("IOException when connecting to URL: " + url);
	}finally{
	}
	return content;
}
private static void gettingOrSettingCookie(URLConnection httpURLConnection,  boolean keepCookie) {  
    // Getting or setting cookie.  
	System.out.println("Cookie�ǣ�"+cookie+"*********");
    if (cookie == null || cookie.length() == 0) {  
        String setCookie = httpURLConnection.getHeaderField("Set-Cookie");
//        List<String> list=httpURLConnection.getHeaderFields().get("Set-Cookie");
//        String setCookie="";
//        for(int i=0;list!=null&&i<list.size();i++){
//        	setCookie+=list.get(i);
//        }
        if(setCookie==null)return ;
        cookie = setCookie.substring(0, setCookie.indexOf(";"))+";	__utma=5287605.1708580466.1310438279.1310438348.1310440428.3; __utmz=5287605.1310438279.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); c_referrer=http%3A//www1.tradekey.com/product_view/id/1640792.htm; c_landing=http%3A//www1.tradekey.com/index.html%3Faction%3Dlogin_signin; c_login_required=no; __utmc=5287605; c_pos=pr%3A1702001%7Ec%7E0%7Cso%3A5535804%7Ei%7E0%2C5535816%7Ei%7E0%2C5535811%7Ei%7E0%2C5535822%7Ei%7E0; c_memtype=3; c_name=huang+jie; c_email=707807876%40qq.com; c_userid=5503962; c_buyer_seller=1; __utmb=5287605.7.10.1310440428; login=1; verfication_code=5a0ed80ca99ddd8c002e6d27c383a5bb";  
//        cookie=setCookie;
    } else if (keepCookie) {  
        httpURLConnection.setRequestProperty("Cookie", cookie); 
        httpURLConnection.setRequestProperty("Referer", "http://www1.tradekey.com/product_cat/cid/3583/Food-Beverage.htm"); 
    }  
} 
}
