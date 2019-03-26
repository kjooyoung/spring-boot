package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// UserService us = new UserService();
		// 이렇게하면 service 내부의 dao 클래스의 객체주입이 안되어있기 때문에
		// 만들어진 UserService 클래스 가져와야함
//		ApplicationContext ac = 
//				WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
//		UserService userService = ac.getBean(UserService.class);
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		UserVo userVo = userService.login(vo);
		
		if(userVo == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//로그인 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authuser", userVo);
		response.sendRedirect(request.getContextPath()+"/");
		return false;
	}

}
