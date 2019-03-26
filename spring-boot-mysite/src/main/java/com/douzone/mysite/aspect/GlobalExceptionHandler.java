package com.douzone.mysite.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component
public class GlobalExceptionHandler {
	//global exception
	@AfterThrowing(
			value="execution(* *..*.*.*(..))",
			throwing="ex")
	public void afterThrowingAdvice(Throwable ex) {
		System.out.println("call [afterThrowing advice]" + ex);
	}
}
