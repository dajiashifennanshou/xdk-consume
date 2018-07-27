package com.df.xdkconsume.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	/**
	 * 自己定义的拦截器类
	 * @return
	 */
	@Bean
	MyHandlerInterceptor myInterceptor() {
		return new MyHandlerInterceptor();
	}

	/**
	 * 添加拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor()).addPathPatterns("/**").excludePathPatterns("/error/**");
	}

}
