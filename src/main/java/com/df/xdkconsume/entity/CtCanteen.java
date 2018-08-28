package com.df.xdkconsume.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author df
 * @since 2018-08-28
 */
@TableName("Ct_Canteen")
public class CtCanteen implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String canId;
    private String canName;
    private String canTelephone;
    private String canAddress;
    private String canOrderstime;
    private String canOrderetime;
    private String canDeliverstime;
    private String canDeliveretime;
    private Double canStartmoney;
    private Double canDelivermoney;
    private String canNote;
    private byte[] canNotepicture;
    private String canDisputes;
    private String canWindowno;
    private String canComputer;
    private Integer canEnabled;
    private String changedate;
    private Integer changestate;
    private String canPicturefile;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    public String getCanName() {
        return canName;
    }

    public void setCanName(String canName) {
        this.canName = canName;
    }

    public String getCanTelephone() {
        return canTelephone;
    }

    public void setCanTelephone(String canTelephone) {
        this.canTelephone = canTelephone;
    }

    public String getCanAddress() {
        return canAddress;
    }

    public void setCanAddress(String canAddress) {
        this.canAddress = canAddress;
    }

    public String getCanOrderstime() {
        return canOrderstime;
    }

    public void setCanOrderstime(String canOrderstime) {
        this.canOrderstime = canOrderstime;
    }

    public String getCanOrderetime() {
        return canOrderetime;
    }

    public void setCanOrderetime(String canOrderetime) {
        this.canOrderetime = canOrderetime;
    }

    public String getCanDeliverstime() {
        return canDeliverstime;
    }

    public void setCanDeliverstime(String canDeliverstime) {
        this.canDeliverstime = canDeliverstime;
    }

    public String getCanDeliveretime() {
        return canDeliveretime;
    }

    public void setCanDeliveretime(String canDeliveretime) {
        this.canDeliveretime = canDeliveretime;
    }

    public Double getCanStartmoney() {
        return canStartmoney;
    }

    public void setCanStartmoney(Double canStartmoney) {
        this.canStartmoney = canStartmoney;
    }

    public Double getCanDelivermoney() {
        return canDelivermoney;
    }

    public void setCanDelivermoney(Double canDelivermoney) {
        this.canDelivermoney = canDelivermoney;
    }

    public String getCanNote() {
        return canNote;
    }

    public void setCanNote(String canNote) {
        this.canNote = canNote;
    }

    public byte[] getCanNotepicture() {
        return canNotepicture;
    }

    public void setCanNotepicture(byte[] canNotepicture) {
        this.canNotepicture = canNotepicture;
    }

    public String getCanDisputes() {
        return canDisputes;
    }

    public void setCanDisputes(String canDisputes) {
        this.canDisputes = canDisputes;
    }

    public String getCanWindowno() {
        return canWindowno;
    }

    public void setCanWindowno(String canWindowno) {
        this.canWindowno = canWindowno;
    }

    public String getCanComputer() {
        return canComputer;
    }

    public void setCanComputer(String canComputer) {
        this.canComputer = canComputer;
    }

    public Integer getCanEnabled() {
        return canEnabled;
    }

    public void setCanEnabled(Integer canEnabled) {
        this.canEnabled = canEnabled;
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

    public String getCanPicturefile() {
        return canPicturefile;
    }

    public void setCanPicturefile(String canPicturefile) {
        this.canPicturefile = canPicturefile;
    }

    @Override
    public String toString() {
        return "CtCanteen{" +
        ", clientid=" + clientid +
        ", canId=" + canId +
        ", canName=" + canName +
        ", canTelephone=" + canTelephone +
        ", canAddress=" + canAddress +
        ", canOrderstime=" + canOrderstime +
        ", canOrderetime=" + canOrderetime +
        ", canDeliverstime=" + canDeliverstime +
        ", canDeliveretime=" + canDeliveretime +
        ", canStartmoney=" + canStartmoney +
        ", canDelivermoney=" + canDelivermoney +
        ", canNote=" + canNote +
        ", canNotepicture=" + canNotepicture +
        ", canDisputes=" + canDisputes +
        ", canWindowno=" + canWindowno +
        ", canComputer=" + canComputer +
        ", canEnabled=" + canEnabled +
        ", changedate=" + changedate +
        ", changestate=" + changestate +
        ", canPicturefile=" + canPicturefile +
        "}";
    }
}
