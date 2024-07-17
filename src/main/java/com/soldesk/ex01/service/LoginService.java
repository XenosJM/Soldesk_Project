package com.soldesk.ex01.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {
	
	String memberCheckin(Map<String, String> map, HttpServletResponse res);
//	String memberCheckout(String memberId);
}
