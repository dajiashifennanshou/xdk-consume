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
 * @since 2018-07-12
 */
@TableName("Deposit_Detail")
public class DepositDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    @TableId(value = "dd_autoid", type = IdType.AUTO)
    private Integer ddAutoid;
    private String ddPdid;
    private String ddPdname;
    private String ddPdaccountid;
    private String ddDepartment;
    private String ddMoneyaccount;
    private String ddDepositkind;
    private Double ddMoney;
    private Double ddOldmoney;
    private Double ddNewmoney;
    private Double ddExpense;
    private String ddDate;
    private String ddTime;
    private String ddOperator;
    private String ddComputer;
    private String outTradeNo;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Integer getDdAutoid() {
        return ddAutoid;
    }

    public void setDdAutoid(Integer ddAutoid) {
        this.ddAutoid = ddAutoid;
    }

    public String getDdPdid() {
        return ddPdid;
    }

    public void setDdPdid(String ddPdid) {
        this.ddPdid = ddPdid;
    }

    public String getDdPdname() {
        return ddPdname;
    }

    public void setDdPdname(String ddPdname) {
        this.ddPdname = ddPdname;
    }

    public String getDdPdaccountid() {
        return ddPdaccountid;
    }

    public void setDdPdaccountid(String ddPdaccountid) {
        this.ddPdaccountid = ddPdaccountid;
    }

    public String getDdDepartment() {
        return ddDepartment;
    }

    public void setDdDepartment(String ddDepartment) {
        this.ddDepartment = ddDepartment;
    }

    public String getDdMoneyaccount() {
        return ddMoneyaccount;
    }

    public void setDdMoneyaccount(String ddMoneyaccount) {
        this.ddMoneyaccount = ddMoneyaccount;
    }

    public String getDdDepositkind() {
        return ddDepositkind;
    }

    public void setDdDepositkind(String ddDepositkind) {
        this.ddDepositkind = ddDepositkind;
    }

    public Double getDdMoney() {
        return ddMoney;
    }

    public void setDdMoney(Double ddMoney) {
        this.ddMoney = ddMoney;
    }

    public Double getDdOldmoney() {
        return ddOldmoney;
    }

    public void setDdOldmoney(Double ddOldmoney) {
        this.ddOldmoney = ddOldmoney;
    }

    public Double getDdNewmoney() {
        return ddNewmoney;
    }

    public void setDdNewmoney(Double ddNewmoney) {
        this.ddNewmoney = ddNewmoney;
    }

    public Double getDdExpense() {
        return ddExpense;
    }

    public void setDdExpense(Double ddExpense) {
        this.ddExpense = ddExpense;
    }

    public String getDdDate() {
        return ddDate;
    }

    public void setDdDate(String ddDate) {
        this.ddDate = ddDate;
    }

    public String getDdTime() {
        return ddTime;
    }

    public void setDdTime(String ddTime) {
        this.ddTime = ddTime;
    }

    public String getDdOperator() {
        return ddOperator;
    }

    public void setDdOperator(String ddOperator) {
        this.ddOperator = ddOperator;
    }

    public String getDdComputer() {
        return ddComputer;
    }

    public void setDdComputer(String ddComputer) {
        this.ddComputer = ddComputer;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    @Override
    public String toString() {
        return "DepositDetail{" +
        ", clientid=" + clientid +
        ", ddAutoid=" + ddAutoid +
        ", ddPdid=" + ddPdid +
        ", ddPdname=" + ddPdname +
        ", ddPdaccountid=" + ddPdaccountid +
        ", ddDepartment=" + ddDepartment +
        ", ddMoneyaccount=" + ddMoneyaccount +
        ", ddDepositkind=" + ddDepositkind +
        ", ddMoney=" + ddMoney +
        ", ddOldmoney=" + ddOldmoney +
        ", ddNewmoney=" + ddNewmoney +
        ", ddExpense=" + ddExpense +
        ", ddDate=" + ddDate +
        ", ddTime=" + ddTime +
        ", ddOperator=" + ddOperator +
        ", ddComputer=" + ddComputer +
        ", outTradeNo=" + outTradeNo +
        "}";
    }
}
