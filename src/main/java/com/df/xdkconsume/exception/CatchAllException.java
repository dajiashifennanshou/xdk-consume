package com.df.xdkconsume.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.df.xdkconsume.entity.ResultData;

import lombok.extern.slf4j.Slf4j;

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
		log.info("全局异常捕获==="+data.toString());
		e.printStackTrace();
		return data;
	}
	
	

}
