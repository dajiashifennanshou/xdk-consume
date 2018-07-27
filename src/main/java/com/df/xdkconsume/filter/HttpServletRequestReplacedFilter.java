package com.df.xdkconsume.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.df.xdkconsume.entity.RequestReaderHttpServletRequestWrapper;
/**
 * 过滤器包装请求
 * @author 段帆
 * 2018年7月27日上午10:09:46
 */
@WebFilter(filterName="paramFilter",urlPatterns="/*")  //@WebFilter是定义过滤器的注解 ，urlPatterns="/*" 定义过滤器过滤的路径
public class HttpServletRequestReplacedFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if(request instanceof HttpServletRequest) {
			requestWrapper = new RequestReaderHttpServletRequestWrapper((HttpServletRequest) request);
		}
		//获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
		// 在chain.doFiler方法中传递新的request对象
		if(requestWrapper == null) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}