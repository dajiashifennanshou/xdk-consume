package com.df.xdkconsume.exception;

import com.df.xdkconsume.entity.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class CatchAllException {
	
	//声明要捕获的异常
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResultData defultExcepitonHandler(HttpServletRequest request,Exception e) {
		ResultData data = new ResultData();
		data.setCode(290);
		data.setMsg(e.getMessage());
		log.error("全局异常捕获==="+data.toString());
		e.printStackTrace();
		return data;
	}
	
	

}
