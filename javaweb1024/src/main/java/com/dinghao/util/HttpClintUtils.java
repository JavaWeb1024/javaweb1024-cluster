/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.dinghao.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * 通信工具类
 * 
 * @author zihan
 * @time 2016-09-20
 */
public class HttpClintUtils {
	/**
	 * 连接超时时间,单位：毫秒
	 */
	private static int CONNECTION_TIME_OUT = 5000;
	/**
	 * 等待超时时间,单位：毫秒
	 */
	private static int READ_TIME_OUT = 60000;
	
	/**
	 * post方式请求服务器(https协议)
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @param charset
	 *            编码
	 * @param flag
	 *            参数类型
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 */
	public static String postHttps(String url, String content, String charset,
			String flag) throws Exception {

		URL console = new URL(url);
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
				new java.security.SecureRandom());
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		// 读写属性
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 连接超时秒数
		conn.setConnectTimeout(CONNECTION_TIME_OUT);
		// 读取超时秒数
		conn.setReadTimeout(READ_TIME_OUT);
		// 请求方式
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// 不使用缓存
		conn.setUseCaches(false);

		// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		// conn.setRequestProperty("Content-type","application/x-java-serialized-object");
		if ("json".equals(flag)) {
			conn.setRequestProperty("Content-Type",
					"application/json;charset=UTF-8");
		} else if ("webService".equalsIgnoreCase(flag)) {
			conn.setRequestProperty("SOAPAction", "");
			conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
		} else if ("form".equalsIgnoreCase(flag)) {
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
		}
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// 刷新、关闭
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, charset);
		if (is != null) {
			StringBuffer temp = new StringBuffer();
			int len = 0;
			while ((len = isr.read()) != -1) {
				temp.append((char) len);
			}
			isr.close();
			is.close();
			return temp.toString();
		}
		return null;
	}

	public static String postHttp(String url, String content, String charset,
			String flag) throws Exception {

		URL console = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) console.openConnection();
		// 读写属性
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 连接超时秒数
		conn.setConnectTimeout(CONNECTION_TIME_OUT);
		// 读取超时秒数
		conn.setReadTimeout(READ_TIME_OUT);
		// 请求方式
		conn.setRequestMethod("POST");
		// 不使用缓存
		conn.setUseCaches(false);
		// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		if ("json".equals(flag)) {
			conn.setRequestProperty("Content-Type",
					"application/json;charset=UTF-8");
		} else if ("webService".equalsIgnoreCase(flag)) {
			conn.setRequestProperty("SOAPAction", "");
			conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
		} else if ("form".equalsIgnoreCase(flag)) {
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
		}else if("xml".equalsIgnoreCase(flag)){
			conn.setRequestProperty("Content-Type",
					"application/xml");
		}
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		if (content != null) {
			out.write(content.getBytes(charset));
		}
		// 刷新、关闭
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, charset);
		if (is != null) {
			StringBuffer temp = new StringBuffer();
			int len = 0;
			while ((len = isr.read()) != -1) {
				temp.append((char) len);
			}
			isr.close();
			is.close();
			return temp.toString();
		}
		return null;
	}

	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

}
