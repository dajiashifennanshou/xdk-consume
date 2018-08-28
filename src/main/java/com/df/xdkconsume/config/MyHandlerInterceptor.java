package com.df.xdkconsume.config;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.df.xdkconsume.entity.ResultData;
import com.df.xdkconsume.utils.GsonUtils;
import com.df.xdkconsume.utils.HttpHelper;
import com.df.xdkconsume.utils.VerifyUtil;
import com.google.gson.reflect.TypeToken;
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws IOException{
		/*该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller，当返回值为true 时就会继续调用下一个Interceptor的preHandle 方法，如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法；*/
		//		log.info("拦截器开始");//校验签名，时间戳等等
		String requestParam = null;
		requestParam = HttpHelper.getBodyString(request);
		if(StringUtils.isEmpty(requestParam)){
			ResultData result = new ResultData();
			result.setCode(210);
			result.setMsg("no parameter");
			response.getWriter().write(GsonUtils.getInstance().o2J(result));
			return false;
		}
		HashMap<String, String> data = GsonUtils.getInstance().j2T(requestParam, new TypeToken<HashMap<String, String>>(){}.getType());
		ResultData result = VerifyUtil.verifySign(data);
		if(result != null){
			response.getWriter().write(GsonUtils.getInstance().o2J(result));
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object obj,Exception e)throws Exception {
		/*该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作。*/

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response,Object arg2,ModelAndView modelAndView) throws Exception {
		/*该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。用于进行资源清理。*/
		if(response.getStatus() == 500){
			if(modelAndView != null){
				modelAndView.setViewName("/error/500");
			}
		}else if(response.getStatus() == 404){
			if(modelAndView != null){
				modelAndView.setViewName("/error/404");
			}
		}
	} 
}
