package com.acceval.core.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets the principal provider in PrincipalUtil to be handled by RequestContextHolder
 */
@Component
public class PrincipalProviderHandlerInterceptor implements HandlerInterceptor {
	private RequestContextPrincipalProvider principalProvider = new RequestContextPrincipalProvider();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		PrincipalUtil.setProvider(principalProvider);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		PrincipalUtil.removeProvider();
	}
}