package com.df.xdkconsume.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.df.xdkconsume.pay.WXPay;

/**
* @author df
* @version 创建时间：2018年8月17日 下午3:14:32
* @Description 类描述  支付类
*/
@Configuration
public class WxPayRq {
	
	@Autowired
	WXPayConfig config;
	
	@Bean()
	public WXPay wxPay() throws Exception{
		WXPay wxPay = new WXPay(config,false);
		return wxPay;
	}
}
