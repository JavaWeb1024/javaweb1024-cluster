package com.dinghao.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class SessionContextUtils {
	private static HashMap mymap = new HashMap();

	public static synchronized void sessionCreated(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
		}
	}

	public static synchronized void sessionDestroyed(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
	}

	public static synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) mymap.get(session_id);
	}
}
