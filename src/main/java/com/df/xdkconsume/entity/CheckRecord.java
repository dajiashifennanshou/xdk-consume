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
 * @since 2018-10-19
 */
@TableName("Check_Record")
public class CheckRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    @TableId(value = "cr_autoid", type = IdType.AUTO)
    private Integer crAutoid;
    private String crPdid;
    private String crPdname;
    private String crPdaccountid;
    private String crDepartment;
    private String crDate;
    private String crTime;
    private Integer crMactype;
    private String crCheckmac;
    private Integer crChecktype;
    private Integer crStyle;
    private String crEnabled;
    private String crRoutine;
    private String crLeave;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Integer getCrAutoid() {
        return crAutoid;
    }

    public void setCrAutoid(Integer crAutoid) {
        this.crAutoid = crAutoid;
    }

    public String getCrPdid() {
        return crPdid;
    }

    public void setCrPdid(String crPdid) {
        this.crPdid = crPdid;
    }

    public String getCrPdname() {
        return crPdname;
    }

    public void setCrPdname(String crPdname) {
        this.crPdname = crPdname;
    }

    public String getCrPdaccountid() {
        return crPdaccountid;
    }

    public void setCrPdaccountid(String crPdaccountid) {
        this.crPdaccountid = crPdaccountid;
    }

    public String getCrDepartment() {
        return crDepartment;
    }

    public void setCrDepartment(String crDepartment) {
        this.crDepartment = crDepartment;
    }

    public String getCrDate() {
        return crDate;
    }

    public void setCrDate(String crDate) {
        this.crDate = crDate;
    }

    public String getCrTime() {
        return crTime;
    }

    public void setCrTime(String crTime) {
        this.crTime = crTime;
    }

    public Integer getCrMactype() {
        return crMactype;
    }

    public void setCrMactype(Integer crMactype) {
        this.crMactype = crMactype;
    }

    public String getCrCheckmac() {
        return crCheckmac;
    }

    public void setCrCheckmac(String crCheckmac) {
        this.crCheckmac = crCheckmac;
    }

    public Integer getCrChecktype() {
        return crChecktype;
    }

    public void setCrChecktype(Integer crChecktype) {
        this.crChecktype = crChecktype;
    }

    public Integer getCrStyle() {
        return crStyle;
    }

    public void setCrStyle(Integer crStyle) {
        this.crStyle = crStyle;
    }

    public String getCrEnabled() {
        return crEnabled;
    }

    public void setCrEnabled(String crEnabled) {
        this.crEnabled = crEnabled;
    }

    public String getCrRoutine() {
        return crRoutine;
    }

    public void setCrRoutine(String crRoutine) {
        this.crRoutine = crRoutine;
    }

    public String getCrLeave() {
        return crLeave;
    }

    public void setCrLeave(String crLeave) {
        this.crLeave = crLeave;
    }

    @Override
    public String toString() {
        return "CheckRecord{" +
        ", clientid=" + clientid +
        ", crAutoid=" + crAutoid +
        ", crPdid=" + crPdid +
        ", crPdname=" + crPdname +
        ", crPdaccountid=" + crPdaccountid +
        ", crDepartment=" + crDepartment +
        ", crDate=" + crDate +
        ", crTime=" + crTime +
        ", crMactype=" + crMactype +
        ", crCheckmac=" + crCheckmac +
        ", crChecktype=" + crChecktype +
        ", crStyle=" + crStyle +
        ", crEnabled=" + crEnabled +
        ", crRoutine=" + crRoutine +
        ", crLeave=" + crLeave +
        "}";
    }
}
