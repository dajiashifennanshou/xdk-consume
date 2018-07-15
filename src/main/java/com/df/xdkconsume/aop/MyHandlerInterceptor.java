package com.df.xdkconsume.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyHandlerInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)throws Exception{
		/*该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller，当返回值为true 时就会继续调用下一个Interceptor的preHandle 方法，如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法；*/
		log.info("拦截器开始");//校验签名，时间戳等等
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object obj,Exception e)throws Exception {
		/*该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作。*/
		

	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response,Object arg2,ModelAndView modelAndView) throws Exception {
		/*该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。用于进行资源清理。*/
		if(response.getStatus() == 500){
			modelAndView.setViewName("/errorpage/500.html");
			/*
			 * setViewName(String viewName);
			 * 为此ModelAndView设置视图名称，由DispatcherServlet通过ViewResolver解析。 将覆盖任何预先存在的视图名称或视图。
			 */
		}else if(response.getStatus() == 404){
			if(modelAndView != null){
				modelAndView.setViewName("/errorpage/404.html");
			}
		}
	} 
}
