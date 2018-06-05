package com.techm.smart.parking.solution.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ControllerValidator extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		System.out.println("Got request to save data : name:"+request.getContentType());
		System.out.println("Handler Details: " + handler);
		return true;
	}
}
