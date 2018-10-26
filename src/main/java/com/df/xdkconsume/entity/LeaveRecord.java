package com.df.xdkconsume.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author df
 * @since 2018-10-23
 */
@TableName("Leave_Record")
public class LeaveRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String lrBillid;
    private String lrPdid;
    private String lrPdname;
    private String lrPdaccountid;
    private String lrDepartment;
    private String lrLeavetype;
    private String lrReason;
    private String lrStartdate;
    private String lrStarttime;
    private String lrEnddate;
    private String lrEndtime;
    private Double lrLong;
    private String lrApprover;
    private Integer lrResults;
    private String lrNote;
    private String lrOperator;
    private String changedate;
    private Integer changestate;
    private Integer autoid;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getLrBillid() {
        return lrBillid;
    }

    public void setLrBillid(String lrBillid) {
        this.lrBillid = lrBillid;
    }

    public String getLrPdid() {
        return lrPdid;
    }

    public void setLrPdid(String lrPdid) {
        this.lrPdid = lrPdid;
    }

    public String getLrPdname() {
        return lrPdname;
    }

    public void setLrPdname(String lrPdname) {
        this.lrPdname = lrPdname;
    }

    public String getLrPdaccountid() {
        return lrPdaccountid;
    }

    public void setLrPdaccountid(String lrPdaccountid) {
        this.lrPdaccountid = lrPdaccountid;
    }

    public String getLrDepartment() {
        return lrDepartment;
    }

    public void setLrDepartment(String lrDepartment) {
        this.lrDepartment = lrDepartment;
    }

    public String getLrLeavetype() {
        return lrLeavetype;
    }

    public void setLrLeavetype(String lrLeavetype) {
        this.lrLeavetype = lrLeavetype;
    }

    public String getLrReason() {
        return lrReason;
    }

    public void setLrReason(String lrReason) {
        this.lrReason = lrReason;
    }

    public String getLrStartdate() {
        return lrStartdate;
    }

    public void setLrStartdate(String lrStartdate) {
        this.lrStartdate = lrStartdate;
    }

    public String getLrStarttime() {
        return lrStarttime;
    }

    public void setLrStarttime(String lrStarttime) {
        this.lrStarttime = lrStarttime;
    }

    public String getLrEnddate() {
        return lrEnddate;
    }

    public void setLrEnddate(String lrEnddate) {
        this.lrEnddate = lrEnddate;
    }

    public String getLrEndtime() {
        return lrEndtime;
    }

    public void setLrEndtime(String lrEndtime) {
        this.lrEndtime = lrEndtime;
    }

    public Double getLrLong() {
        return lrLong;
    }

    public void setLrLong(Double lrLong) {
        this.lrLong = lrLong;
    }

    public String getLrApprover() {
        return lrApprover;
    }

    public void setLrApprover(String lrApprover) {
        this.lrApprover = lrApprover;
    }

    public Integer getLrResults() {
        return lrResults;
    }

    public void setLrResults(Integer lrResults) {
        this.lrResults = lrResults;
    }

    public String getLrNote() {
        return lrNote;
    }

    public void setLrNote(String lrNote) {
        this.lrNote = lrNote;
    }

    public String getLrOperator() {
        return lrOperator;
    }

    public void setLrOperator(String lrOperator) {
        this.lrOperator = lrOperator;
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

    public Integer getAutoid() {
        return autoid;
    }

    public void setAutoid(Integer autoid) {
        this.autoid = autoid;
    }

    @Override
    public String toString() {
        return "LeaveRecord{" +
        ", clientid=" + clientid +
        ", lrBillid=" + lrBillid +
        ", lrPdid=" + lrPdid +
        ", lrPdname=" + lrPdname +
        ", lrPdaccountid=" + lrPdaccountid +
        ", lrDepartment=" + lrDepartment +
        ", lrLeavetype=" + lrLeavetype +
        ", lrReason=" + lrReason +
        ", lrStartdate=" + lrStartdate +
        ", lrStarttime=" + lrStarttime +
        ", lrEnddate=" + lrEnddate +
        ", lrEndtime=" + lrEndtime +
        ", lrLong=" + lrLong +
        ", lrApprover=" + lrApprover +
        ", lrResults=" + lrResults +
        ", lrNote=" + lrNote +
        ", lrOperator=" + lrOperator +
        ", changedate=" + changedate +
        ", changestate=" + changestate +
        ", autoid=" + autoid +
        "}";
    }
}
