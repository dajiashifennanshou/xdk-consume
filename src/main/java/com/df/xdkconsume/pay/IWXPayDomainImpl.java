package com.df.xdkconsume.pay;

import com.df.xdkconsume.config.WXPayConfig;

/**
* @author df
* @version 创建时间：2018年8月17日 下午5:15:01
* @Description 类描述
*/
public class IWXPayDomainImpl implements IWXPayDomain{

	@Override
	public void report(String domain, long elapsedTimeMillis, Exception ex) {
		
	}

	@Override
	public DomainInfo getDomain(WXPayConfig config) {
		DomainInfo info = new DomainInfo("pay.swiftpass.cn", true);
		return info;
	}

}
