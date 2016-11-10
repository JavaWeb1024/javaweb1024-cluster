/*



 */

package com.dinghao.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.dinghao.constant.SystemConstant;

public class HttpUtils {
	private static final String[] ADDR_HEADER = { "X-Forwarded-For",
			"Proxy-Client-IP", "WL-Proxy-Client-IP", "X-Real-IP" };
	private static final String NUKNOWN = "unknown";

	/**
	 * 获得真实IP地址。在使用了反向代理时，直接用HttpServletRequest.getRemoteAddr()无法获取客户真实的IP地址。
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(ServletRequest request) {
		String addr = null;
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsr = (HttpServletRequest) request;
			for (String header : ADDR_HEADER) {
				if (StringUtils.isBlank(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
					addr = hsr.getHeader(header);
				} else {
					break;
				}
			}
		}
		if (StringUtils.isBlank(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
			addr = request.getRemoteAddr();
		} else {
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按','分割
			int i = addr.indexOf(",");
			if (i > 0) {
				addr = addr.substring(0, i);
			}
		}
		return addr;
	}

	/**
	 * 得到请求的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip)) {
			ip = request.getHeader("Host");
		}
		if (StringUtils.isBlank(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isBlank(ip)) {
			ip = "0.0.0.0";
		}
		return ip;
	}

	/**
	 * 得到请求的根目录
	 * 
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path;
		return basePath;
	}

	/**
	 * 得到结构目录
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return path;
	}

	/**
	 * @return
	 */
	public static String getRealPath() {
		return SystemConstant.DINGHAO_CMS_ROOT;
	}

}
