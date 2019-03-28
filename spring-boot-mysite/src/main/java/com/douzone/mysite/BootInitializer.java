package com.douzone.mysite;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableAutoConfiguration
public class BootInitializer extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// BootApplication 클래스의 메인메소드를 실행시키는게 아니라 스프링부트 어노테이션의 설정을 봄
		return builder.sources(BootApplication.class);
	}
	
	
}
