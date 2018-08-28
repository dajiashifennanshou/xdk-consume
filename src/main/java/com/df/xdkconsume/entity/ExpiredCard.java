package com.df.xdkconsume.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author df
 * @since 2018-08-21
 */
@TableName("Expired_Card")
public class ExpiredCard implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String ecCardid;
    private String ecCardxf;
    private String ecLongcardid;
    private String ecPdid;
    private String ecPdaccountid;
    private String ecPdname;
    private String ecDepartment;
    private String ecDate;
    private String ecTime;
    private String ecOperator;
    private String ecComputer;
    private String changedate;
    private Integer changestate;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getEcCardid() {
        return ecCardid;
    }

    public void setEcCardid(String ecCardid) {
        this.ecCardid = ecCardid;
    }

    public String getEcCardxf() {
        return ecCardxf;
    }

    public void setEcCardxf(String ecCardxf) {
        this.ecCardxf = ecCardxf;
    }

    public String getEcLongcardid() {
        return ecLongcardid;
    }

    public void setEcLongcardid(String ecLongcardid) {
        this.ecLongcardid = ecLongcardid;
    }

    public String getEcPdid() {
        return ecPdid;
    }

    public void setEcPdid(String ecPdid) {
        this.ecPdid = ecPdid;
    }

    public String getEcPdaccountid() {
        return ecPdaccountid;
    }

    public void setEcPdaccountid(String ecPdaccountid) {
        this.ecPdaccountid = ecPdaccountid;
    }

    public String getEcPdname() {
        return ecPdname;
    }

    public void setEcPdname(String ecPdname) {
        this.ecPdname = ecPdname;
    }

    public String getEcDepartment() {
        return ecDepartment;
    }

    public void setEcDepartment(String ecDepartment) {
        this.ecDepartment = ecDepartment;
    }

    public String getEcDate() {
        return ecDate;
    }

    public void setEcDate(String ecDate) {
        this.ecDate = ecDate;
    }

    public String getEcTime() {
        return ecTime;
    }

    public void setEcTime(String ecTime) {
        this.ecTime = ecTime;
    }

    public String getEcOperator() {
        return ecOperator;
    }

    public void setEcOperator(String ecOperator) {
        this.ecOperator = ecOperator;
    }

    public String getEcComputer() {
        return ecComputer;
    }

    public void setEcComputer(String ecComputer) {
        this.ecComputer = ecComputer;
    }

    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    public Integer getChangestate() {
        return changestate;
    }

    public void setChangestate(Integer changestate) {
        this.changestate = changestate;
    }

    @Override
    public String toString() {
        return "ExpiredCard{" +
        ", clientid=" + clientid +
        ", ecCardid=" + ecCardid +
        ", ecCardxf=" + ecCardxf +
        ", ecLongcardid=" + ecLongcardid +
        ", ecPdid=" + ecPdid +
        ", ecPdaccountid=" + ecPdaccountid +
        ", ecPdname=" + ecPdname +
        ", ecDepartment=" + ecDepartment +
        ", ecDate=" + ecDate +
        ", ecTime=" + ecTime +
        ", ecOperator=" + ecOperator +
        ", ecComputer=" + ecComputer +
        ", changedate=" + changedate +
        ", changestate=" + changestate +
        "}";
    }
}
