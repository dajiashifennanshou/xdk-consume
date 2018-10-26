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
@TableName("Routine_Time")
public class RoutineTime implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private Integer rtId;
    private String rtStartdate;
    private String rtEnddate;
    private Integer rtWeek1;
    private Integer rtWeek2;
    private Integer rtWeek3;
    private Integer rtWeek4;
    private Integer rtWeek5;
    private Integer rtWeek6;
    private Integer rtWeek7;
    private String rtName;
    private String rtStarttime;
    private String rtEndtime;
    private Integer rtSchool;
    private Integer rtClass;
    private Integer rtApartment;
    private String rtPdstatus;
    private String changedate;
    private Integer changestate;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Integer getRtId() {
        return rtId;
    }

    public void setRtId(Integer rtId) {
        this.rtId = rtId;
    }

    public String getRtStartdate() {
        return rtStartdate;
    }

    public void setRtStartdate(String rtStartdate) {
        this.rtStartdate = rtStartdate;
    }

    public String getRtEnddate() {
        return rtEnddate;
    }

    public void setRtEnddate(String rtEnddate) {
        this.rtEnddate = rtEnddate;
    }

    public Integer getRtWeek1() {
        return rtWeek1;
    }

    public void setRtWeek1(Integer rtWeek1) {
        this.rtWeek1 = rtWeek1;
    }

    public Integer getRtWeek2() {
        return rtWeek2;
    }

    public void setRtWeek2(Integer rtWeek2) {
        this.rtWeek2 = rtWeek2;
    }

    public Integer getRtWeek3() {
        return rtWeek3;
    }

    public void setRtWeek3(Integer rtWeek3) {
        this.rtWeek3 = rtWeek3;
    }

    public Integer getRtWeek4() {
        return rtWeek4;
    }

    public void setRtWeek4(Integer rtWeek4) {
        this.rtWeek4 = rtWeek4;
    }

    public Integer getRtWeek5() {
        return rtWeek5;
    }

    public void setRtWeek5(Integer rtWeek5) {
        this.rtWeek5 = rtWeek5;
    }

    public Integer getRtWeek6() {
        return rtWeek6;
    }

    public void setRtWeek6(Integer rtWeek6) {
        this.rtWeek6 = rtWeek6;
    }

    public Integer getRtWeek7() {
        return rtWeek7;
    }

    public void setRtWeek7(Integer rtWeek7) {
        this.rtWeek7 = rtWeek7;
    }

    public String getRtName() {
        return rtName;
    }

    public void setRtName(String rtName) {
        this.rtName = rtName;
    }

    public String getRtStarttime() {
        return rtStarttime;
    }

    public void setRtStarttime(String rtStarttime) {
        this.rtStarttime = rtStarttime;
    }

    public String getRtEndtime() {
        return rtEndtime;
    }

    public void setRtEndtime(String rtEndtime) {
        this.rtEndtime = rtEndtime;
    }

    public Integer getRtSchool() {
        return rtSchool;
    }

    public void setRtSchool(Integer rtSchool) {
        this.rtSchool = rtSchool;
    }

    public Integer getRtClass() {
        return rtClass;
    }

    public void setRtClass(Integer rtClass) {
        this.rtClass = rtClass;
    }

    public Integer getRtApartment() {
        return rtApartment;
    }

    public void setRtApartment(Integer rtApartment) {
        this.rtApartment = rtApartment;
    }

    public String getRtPdstatus() {
        return rtPdstatus;
    }

    public void setRtPdstatus(String rtPdstatus) {
        this.rtPdstatus = rtPdstatus;
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
        return "RoutineTime{" +
        ", clientid=" + clientid +
        ", rtId=" + rtId +
        ", rtStartdate=" + rtStartdate +
        ", rtEnddate=" + rtEnddate +
        ", rtWeek1=" + rtWeek1 +
        ", rtWeek2=" + rtWeek2 +
        ", rtWeek3=" + rtWeek3 +
        ", rtWeek4=" + rtWeek4 +
        ", rtWeek5=" + rtWeek5 +
        ", rtWeek6=" + rtWeek6 +
        ", rtWeek7=" + rtWeek7 +
        ", rtName=" + rtName +
        ", rtStarttime=" + rtStarttime +
        ", rtEndtime=" + rtEndtime +
        ", rtSchool=" + rtSchool +
        ", rtClass=" + rtClass +
        ", rtApartment=" + rtApartment +
        ", rtPdstatus=" + rtPdstatus +
        ", changedate=" + changedate +
        ", changestate=" + changestate +
        "}";
    }
}
