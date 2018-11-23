package com.df.xdkconsume.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author df
 * @since 2018-07-14
 */
@TableName("Spend_Detail")
public class SpendDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    @TableId(value = "sd_autoid", type = IdType.AUTO)
    private Integer sdAutoid;
    private String sdPdid;
    private String sdPdname;
    private String sdPdaccountid;
    private String sdDepartment;
    private String sdDatatype;
    private String sdMeal;
    private Double sdMoney;
    private Double sdOldmoney;
    private Double sdNewmoney;
    private String sdMoneyaccount;
    private String sdSpendtype;
    private String sdSpenddate;
    private String sdSpendtime;
    private String sdCldate;
    private String sdCltime;
    private String sdOperator;
    private String sdWindowno;
    private String sdComputer;
    private String sdFoid;


    public String getClientid() {
        return clientid;
    }

    public String getSdFoid() {
		return sdFoid;
	}

	public void setSdFoid(String sdFoid) {
		this.sdFoid = sdFoid;
	}

	public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Integer getSdAutoid() {
        return sdAutoid;
    }

    public void setSdAutoid(Integer sdAutoid) {
        this.sdAutoid = sdAutoid;
    }

    public String getSdPdid() {
        return sdPdid;
    }

    public void setSdPdid(String sdPdid) {
        this.sdPdid = sdPdid;
    }

    public String getSdPdname() {
        return sdPdname;
    }

    public void setSdPdname(String sdPdname) {
        this.sdPdname = sdPdname;
    }

    public String getSdPdaccountid() {
        return sdPdaccountid;
    }

    public void setSdPdaccountid(String sdPdaccountid) {
        this.sdPdaccountid = sdPdaccountid;
    }

    public String getSdDepartment() {
        return sdDepartment;
    }

    public void setSdDepartment(String sdDepartment) {
        this.sdDepartment = sdDepartment;
    }

    public String getSdDatatype() {
        return sdDatatype;
    }

    public void setSdDatatype(String sdDatatype) {
        this.sdDatatype = sdDatatype;
    }

    public String getSdMeal() {
        return sdMeal;
    }

    public void setSdMeal(String sdMeal) {
        this.sdMeal = sdMeal;
    }

    public Double getSdMoney() {
        return sdMoney;
    }

    public void setSdMoney(Double sdMoney) {
        this.sdMoney = sdMoney;
    }

    public Double getSdOldmoney() {
        return sdOldmoney;
    }

    public void setSdOldmoney(Double sdOldmoney) {
        this.sdOldmoney = sdOldmoney;
    }

    public Double getSdNewmoney() {
        return sdNewmoney;
    }

    public void setSdNewmoney(Double sdNewmoney) {
        this.sdNewmoney = sdNewmoney;
    }

    public String getSdMoneyaccount() {
        return sdMoneyaccount;
    }

    public void setSdMoneyaccount(String sdMoneyaccount) {
        this.sdMoneyaccount = sdMoneyaccount;
    }

    public String getSdSpendtype() {
        return sdSpendtype;
    }

    public void setSdSpendtype(String sdSpendtype) {
        this.sdSpendtype = sdSpendtype;
    }

    public String getSdSpenddate() {
        return sdSpenddate;
    }

    public void setSdSpenddate(String sdSpenddate) {
        this.sdSpenddate = sdSpenddate;
    }

    public String getSdSpendtime() {
        return sdSpendtime;
    }

    public void setSdSpendtime(String sdSpendtime) {
        this.sdSpendtime = sdSpendtime;
    }

    public String getSdCldate() {
        return sdCldate;
    }

    public void setSdCldate(String sdCldate) {
        this.sdCldate = sdCldate;
    }

    public String getSdCltime() {
        return sdCltime;
    }

    public void setSdCltime(String sdCltime) {
        this.sdCltime = sdCltime;
    }

    public String getSdOperator() {
        return sdOperator;
    }

    public void setSdOperator(String sdOperator) {
        this.sdOperator = sdOperator;
    }

    public String getSdWindowno() {
        return sdWindowno;
    }

    public void setSdWindowno(String sdWindowno) {
        this.sdWindowno = sdWindowno;
    }

    public String getSdComputer() {
        return sdComputer;
    }

    public void setSdComputer(String sdComputer) {
        this.sdComputer = sdComputer;
    }

    @Override
    public String toString() {
        return "SpendDetail{" +
        ", clientid=" + clientid +
        ", sdAutoid=" + sdAutoid +
        ", sdPdid=" + sdPdid +
        ", sdPdname=" + sdPdname +
        ", sdPdaccountid=" + sdPdaccountid +
        ", sdDepartment=" + sdDepartment +
        ", sdDatatype=" + sdDatatype +
        ", sdMeal=" + sdMeal +
        ", sdMoney=" + sdMoney +
        ", sdOldmoney=" + sdOldmoney +
        ", sdNewmoney=" + sdNewmoney +
        ", sdMoneyaccount=" + sdMoneyaccount +
        ", sdSpendtype=" + sdSpendtype +
        ", sdSpenddate=" + sdSpenddate +
        ", sdSpendtime=" + sdSpendtime +
        ", sdCldate=" + sdCldate +
        ", sdCltime=" + sdCltime +
        ", sdOperator=" + sdOperator +
        ", sdWindowno=" + sdWindowno +
        ", sdComputer=" + sdComputer +
        "}";
    }
}
