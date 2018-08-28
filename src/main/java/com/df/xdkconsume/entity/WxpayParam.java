package com.df.xdkconsume.entity;

/**
* @author df
* @version 创建时间：2018年8月17日 上午10:32:42
* @Description 类描述 餐盘支付 微信支付参数
*/
public class WxpayParam extends BaseParam {
	private String spendTime,spendDate,authCode;
	private String cashList;//餐盘序号
	

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getCashList() {
		return cashList;
	}

	public void setCashList(String cashList) {
		this.cashList = cashList;
	}


	public String getSpendDate() {
		return spendDate;
	}
	public void setSpendDate(String spendDate) {
		this.spendDate = spendDate;
	}

	public String getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}
}
