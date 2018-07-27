package com.df.xdkconsume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.df.xdkconsume.service.DepositDetailService;


/**
 * <p>
 *  前端控制器  存款记录操作，暂时不用
 * </p>
 *
 * @author df
 * @since 2018-07-12
 */
@RestController
public class DepositDetailController {
	
	@Autowired
	DepositDetailService service;
	 
	@RequestMapping("/index")
	public String getinfo(@RequestBody String name){
		System.out.println(name);
		System.out.println(name.toString());
		return "hello";
	}
}

