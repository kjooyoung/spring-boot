package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth.Role;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. Handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. Casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;

		// 3. Method에 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 3-1. Method에 @Auth가 안붙어 있으면 class(type)의 @Auth 받아오기
		if( auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		// 4. Method와 class 둘다 @Auth가 안 붙어 있으면
		if(auth == null) {
			return true;
		}

		// 5. @Auth가 붙어 있기 때문에 로그인 여부(Authorization) 확인
		HttpSession session = request.getSession();
		UserVo authUser = (session == null) ? null : (UserVo)session.getAttribute("authuser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}

		// 5.1 Role 가져오기(권한, Authentication)
		// 접근 권한이 user이면 admin과 user 모두 통과
		Role role = auth.value();
		if(role == Role.USER) {
			return true;
		}
		
		// ADMIN Role 접근
		// ADMIN 권한이 없는 사용자이면 메인으로 보냄
		if("ADMIN".equals(authUser.getRole()) == false) {
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		// 6. 접근 허용
		return true;
	}

}
