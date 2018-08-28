package com.df.xdkconsume.entity;

/**
* @author df
* @version 创建时间：2018年8月22日 上午9:17:41
* @Description 类描述 手持机用户
*/
public class NfcUser {
	private String pdName,pdCardid,PdAccountid,clientid;
	private double pdCashmoney,pdSubsidymoney;
	private int PdLoss;

	

	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	public String getPdCardid() {
		return pdCardid;
	}

	public void setPdCardid(String pdCardid) {
		this.pdCardid = pdCardid;
	}

	public int getPdLoss() {
		return PdLoss;
	}

	public void setPdLoss(Integer pdLoss) {
		PdLoss = pdLoss;
	}

	public String getPdAccountid() {
		return PdAccountid;
	}

	public void setPdAccountid(String pdAccountid) {
		PdAccountid = pdAccountid;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public double getPdCashmoney() {
		return pdCashmoney;
	}

	public void setPdCashmoney(double pdCashmoney) {
		this.pdCashmoney = pdCashmoney;
	}

	public double getPdSubsidymoney() {
		return pdSubsidymoney;
	}

	public void setPdSubsidymoney(double pdSubsidymoney) {
		this.pdSubsidymoney = pdSubsidymoney;
	}


}
