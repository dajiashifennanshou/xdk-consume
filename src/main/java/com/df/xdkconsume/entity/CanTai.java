package com.df.xdkconsume.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author df
 * @since 2018-11-20
 */
@TableName("CanTai")
public class CanTai implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String outTradeNo;
    private String totalFee;
    private String authCode;
    private String mchCreateIp;
    private String spenddate;
    private String spendtime;
    private String cashliststring;
    private String one;
    private String two;
    private String onename;
    private String twoname;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getMchCreateIp() {
        return mchCreateIp;
    }

    public void setMchCreateIp(String mchCreateIp) {
        this.mchCreateIp = mchCreateIp;
    }

    public String getSpenddate() {
        return spenddate;
    }

    public void setSpenddate(String spenddate) {
        this.spenddate = spenddate;
    }

    public String getSpendtime() {
        return spendtime;
    }

    public void setSpendtime(String spendtime) {
        this.spendtime = spendtime;
    }

    public String getCashliststring() {
        return cashliststring;
    }

    public void setCashliststring(String cashliststring) {
        this.cashliststring = cashliststring;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getOnename() {
        return onename;
    }

    public void setOnename(String onename) {
        this.onename = onename;
    }

    public String getTwoname() {
        return twoname;
    }

    public void setTwoname(String twoname) {
        this.twoname = twoname;
    }

    @Override
    public String toString() {
        return "CanTai{" +
        ", clientid=" + clientid +
        ", outTradeNo=" + outTradeNo +
        ", totalFee=" + totalFee +
        ", authCode=" + authCode +
        ", mchCreateIp=" + mchCreateIp +
        ", spenddate=" + spenddate +
        ", spendtime=" + spendtime +
        ", cashliststring=" + cashliststring +
        ", one=" + one +
        ", two=" + two +
        ", onename=" + onename +
        ", twoname=" + twoname +
        "}";
    }
}
