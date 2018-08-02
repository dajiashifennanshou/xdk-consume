package com.df.xdkconsume.log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.df.xdkconsume.utils.GsonUtils;

import lombok.extern.slf4j.Slf4j;
/**
* @author df
* @version 创建时间：2018年7月11日 下午5:13:44
* @Description 类描述:拦截所有请求，签名认证
*/
@Component
@Aspect
@Slf4j
public class ControllerAop {
	/**
	 * 定义Pointcut，Pointcut的名称 就是simplePointcut，此方法不能有返回值，该方法只是一个标示
	 */
	@Pointcut("execution(public * com.df.xdkconsume.controller.*.*(..))")
	public void recordLog() {
	}

	@Before("recordLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		log.info("=====================================");
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		log.info("网址 : " + request.getRequestURL().toString());
		log.info("请求方式 : " + request.getMethod());
		log.info("IP : " + request.getRemoteAddr());
		// 获取参数, 只取自定义的参数, 自带的HttpServletRequest, HttpServletResponse不管
		log.info("请求参数 : ");
        if (joinPoint.getArgs().length > 0) {
            for (Object o : joinPoint.getArgs()) {
                if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                    continue;
                }
                log.info(GsonUtils.getInstance().o2J(o));
            }
        }
        //		log.info("请求参数:"+body);
		//		Enumeration<String> enu = request.getParameterNames();
		//		log.info("请求参数");
		//		while (enu.hasMoreElements()) {
		//			String name = (String) enu.nextElement();
		//			log.info(name+":"+request.getParameter(name));
		//		}
		Enumeration<String> enu = request.getParameterNames();
		log.info("请求参数");
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			log.info(name+":"+request.getParameter(name));
		}
	}

	@AfterReturning(returning = "ret", pointcut = "recordLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.info("返回参数 ");
		if(ret != null){
			log.info(ret.toString());
		}
		log.info("=====================================");
	}
	
 
	@Around("recordLog()")
	public Object aroundLogCalls(ProceedingJoinPoint jp) throws Throwable {
		return jp.proceed();
	}

	@AfterThrowing("recordLog()")
	public void catchInfo() {
		log.info("异常信息");
	}

	@After("recordLog()")
	public void after(JoinPoint jp) {
//		log.info("" + jp.getSignature().getName() + "()方法-结束！");
	}

}
