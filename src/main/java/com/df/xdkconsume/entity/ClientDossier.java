package com.df.xdkconsume.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author df
 * @since 2018-10-26
 */
@TableName("ClientDossier")
public class ClientDossier implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String clientname;
    private String nature;
    private String address;
    private String contacts;
    private String contactsphone;
    @TableField("Contactsposition")
    private String Contactsposition;
    private Integer message;
    private String homepage;
    private String company;
    @TableField("Cooperativeproduct")
    private String Cooperativeproduct;
    @TableField("Cooperativedate")
    private String Cooperativedate;
    @TableField("Cooperativevaliddate")
    private String Cooperativevaliddate;
    @TableField("Cooperativemode")
    private String Cooperativemode;
    private String parentmodular;
    private String teachermodular;
    private String paymode;
    private String vippaynumber;
    private Integer vippaylen1;
    private Double vippaymoney1;
    private Double vippaymoney12;
    private Double vippaymoney13;
    private Double vippaymoney14;
    private Integer vippaylen2;
    private Double vippaymoney2;
    private Double vippaymoney22;
    private Double vippaymoney23;
    private Double vippaymoney24;
    private String parentspendpaynumber1;
    private String parentspendpaynumber2;
    private String parentspendpaynumber3;
    private String parentspendpaynumber4;
    private String teacherspendpaynumber1;
    private String teacherspendpaynumber2;
    private String teacherspendpaynumber3;
    private String teacherspendpaynumber4;
    private String teachermanager1;
    private String teachermanager2;
    @TableField("Clientcheck")
    private Integer Clientcheck;
    private Long smsnumber;
    private String machineservicetel1;
    private String machineservicetel2;
    private Double moneyrate;
    private Double phonemoney;
    private String appid;
    private String appsecret;
    private String bindmsgid;
    private String bindmsgidteacher;
    private String checkmsgid;
    private String noticemsgid;
    private String homeworkmsgid;
    private String paysuccessmsgid;
    private String payfailmsgid;
    private String feedbackmsgid;
    private String leavemsgid;
    private String praisemsgid;
    private String noticestmsgid;
    private String machinemsgid;
    private String mchid;
    private String mchkey;
    private String vipmchid;
    private String vipmchidkey;
    private String loginmsgid;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsphone() {
        return contactsphone;
    }

    public void setContactsphone(String contactsphone) {
        this.contactsphone = contactsphone;
    }

    public String getContactsposition() {
        return Contactsposition;
    }

    public void setContactsposition(String Contactsposition) {
        this.Contactsposition = Contactsposition;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCooperativeproduct() {
        return Cooperativeproduct;
    }

    public void setCooperativeproduct(String Cooperativeproduct) {
        this.Cooperativeproduct = Cooperativeproduct;
    }

    public String getCooperativedate() {
        return Cooperativedate;
    }

    public void setCooperativedate(String Cooperativedate) {
        this.Cooperativedate = Cooperativedate;
    }

    public String getCooperativevaliddate() {
        return Cooperativevaliddate;
    }

    public void setCooperativevaliddate(String Cooperativevaliddate) {
        this.Cooperativevaliddate = Cooperativevaliddate;
    }

    public String getCooperativemode() {
        return Cooperativemode;
    }

    public void setCooperativemode(String Cooperativemode) {
        this.Cooperativemode = Cooperativemode;
    }

    public String getParentmodular() {
        return parentmodular;
    }

    public void setParentmodular(String parentmodular) {
        this.parentmodular = parentmodular;
    }

    public String getTeachermodular() {
        return teachermodular;
    }

    public void setTeachermodular(String teachermodular) {
        this.teachermodular = teachermodular;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getVippaynumber() {
        return vippaynumber;
    }

    public void setVippaynumber(String vippaynumber) {
        this.vippaynumber = vippaynumber;
    }

    public Integer getVippaylen1() {
        return vippaylen1;
    }

    public void setVippaylen1(Integer vippaylen1) {
        this.vippaylen1 = vippaylen1;
    }

    public Double getVippaymoney1() {
        return vippaymoney1;
    }

    public void setVippaymoney1(Double vippaymoney1) {
        this.vippaymoney1 = vippaymoney1;
    }

    public Double getVippaymoney12() {
        return vippaymoney12;
    }

    public void setVippaymoney12(Double vippaymoney12) {
        this.vippaymoney12 = vippaymoney12;
    }

    public Double getVippaymoney13() {
        return vippaymoney13;
    }

    public void setVippaymoney13(Double vippaymoney13) {
        this.vippaymoney13 = vippaymoney13;
    }

    public Double getVippaymoney14() {
        return vippaymoney14;
    }

    public void setVippaymoney14(Double vippaymoney14) {
        this.vippaymoney14 = vippaymoney14;
    }

    public Integer getVippaylen2() {
        return vippaylen2;
    }

    public void setVippaylen2(Integer vippaylen2) {
        this.vippaylen2 = vippaylen2;
    }

    public Double getVippaymoney2() {
        return vippaymoney2;
    }

    public void setVippaymoney2(Double vippaymoney2) {
        this.vippaymoney2 = vippaymoney2;
    }

    public Double getVippaymoney22() {
        return vippaymoney22;
    }

    public void setVippaymoney22(Double vippaymoney22) {
        this.vippaymoney22 = vippaymoney22;
    }

    public Double getVippaymoney23() {
        return vippaymoney23;
    }

    public void setVippaymoney23(Double vippaymoney23) {
        this.vippaymoney23 = vippaymoney23;
    }

    public Double getVippaymoney24() {
        return vippaymoney24;
    }

    public void setVippaymoney24(Double vippaymoney24) {
        this.vippaymoney24 = vippaymoney24;
    }

    public String getParentspendpaynumber1() {
        return parentspendpaynumber1;
    }

    public void setParentspendpaynumber1(String parentspendpaynumber1) {
        this.parentspendpaynumber1 = parentspendpaynumber1;
    }

    public String getParentspendpaynumber2() {
        return parentspendpaynumber2;
    }

    public void setParentspendpaynumber2(String parentspendpaynumber2) {
        this.parentspendpaynumber2 = parentspendpaynumber2;
    }

    public String getParentspendpaynumber3() {
        return parentspendpaynumber3;
    }

    public void setParentspendpaynumber3(String parentspendpaynumber3) {
        this.parentspendpaynumber3 = parentspendpaynumber3;
    }

    public String getParentspendpaynumber4() {
        return parentspendpaynumber4;
    }

    public void setParentspendpaynumber4(String parentspendpaynumber4) {
        this.parentspendpaynumber4 = parentspendpaynumber4;
    }

    public String getTeacherspendpaynumber1() {
        return teacherspendpaynumber1;
    }

    public void setTeacherspendpaynumber1(String teacherspendpaynumber1) {
        this.teacherspendpaynumber1 = teacherspendpaynumber1;
    }

    public String getTeacherspendpaynumber2() {
        return teacherspendpaynumber2;
    }

    public void setTeacherspendpaynumber2(String teacherspendpaynumber2) {
        this.teacherspendpaynumber2 = teacherspendpaynumber2;
    }

    public String getTeacherspendpaynumber3() {
        return teacherspendpaynumber3;
    }

    public void setTeacherspendpaynumber3(String teacherspendpaynumber3) {
        this.teacherspendpaynumber3 = teacherspendpaynumber3;
    }

    public String getTeacherspendpaynumber4() {
        return teacherspendpaynumber4;
    }

    public void setTeacherspendpaynumber4(String teacherspendpaynumber4) {
        this.teacherspendpaynumber4 = teacherspendpaynumber4;
    }

    public String getTeachermanager1() {
        return teachermanager1;
    }

    public void setTeachermanager1(String teachermanager1) {
        this.teachermanager1 = teachermanager1;
    }

    public String getTeachermanager2() {
        return teachermanager2;
    }

    public void setTeachermanager2(String teachermanager2) {
        this.teachermanager2 = teachermanager2;
    }

    public Integer getClientcheck() {
        return Clientcheck;
    }

    public void setClientcheck(Integer Clientcheck) {
        this.Clientcheck = Clientcheck;
    }

    public Long getSmsnumber() {
        return smsnumber;
    }

    public void setSmsnumber(Long smsnumber) {
        this.smsnumber = smsnumber;
    }

    public String getMachineservicetel1() {
        return machineservicetel1;
    }

    public void setMachineservicetel1(String machineservicetel1) {
        this.machineservicetel1 = machineservicetel1;
    }

    public String getMachineservicetel2() {
        return machineservicetel2;
    }

    public void setMachineservicetel2(String machineservicetel2) {
        this.machineservicetel2 = machineservicetel2;
    }

    public Double getMoneyrate() {
        return moneyrate;
    }

    public void setMoneyrate(Double moneyrate) {
        this.moneyrate = moneyrate;
    }

    public Double getPhonemoney() {
        return phonemoney;
    }

    public void setPhonemoney(Double phonemoney) {
        this.phonemoney = phonemoney;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getBindmsgid() {
        return bindmsgid;
    }

    public void setBindmsgid(String bindmsgid) {
        this.bindmsgid = bindmsgid;
    }

    public String getBindmsgidteacher() {
        return bindmsgidteacher;
    }

    public void setBindmsgidteacher(String bindmsgidteacher) {
        this.bindmsgidteacher = bindmsgidteacher;
    }

    public String getCheckmsgid() {
        return checkmsgid;
    }

    public void setCheckmsgid(String checkmsgid) {
        this.checkmsgid = checkmsgid;
    }

    public String getNoticemsgid() {
        return noticemsgid;
    }

    public void setNoticemsgid(String noticemsgid) {
        this.noticemsgid = noticemsgid;
    }

    public String getHomeworkmsgid() {
        return homeworkmsgid;
    }

    public void setHomeworkmsgid(String homeworkmsgid) {
        this.homeworkmsgid = homeworkmsgid;
    }

    public String getPaysuccessmsgid() {
        return paysuccessmsgid;
    }

    public void setPaysuccessmsgid(String paysuccessmsgid) {
        this.paysuccessmsgid = paysuccessmsgid;
    }

    public String getPayfailmsgid() {
        return payfailmsgid;
    }

    public void setPayfailmsgid(String payfailmsgid) {
        this.payfailmsgid = payfailmsgid;
    }

    public String getFeedbackmsgid() {
        return feedbackmsgid;
    }

    public void setFeedbackmsgid(String feedbackmsgid) {
        this.feedbackmsgid = feedbackmsgid;
    }

    public String getLeavemsgid() {
        return leavemsgid;
    }

    public void setLeavemsgid(String leavemsgid) {
        this.leavemsgid = leavemsgid;
    }

    public String getPraisemsgid() {
        return praisemsgid;
    }

    public void setPraisemsgid(String praisemsgid) {
        this.praisemsgid = praisemsgid;
    }

    public String getNoticestmsgid() {
        return noticestmsgid;
    }

    public void setNoticestmsgid(String noticestmsgid) {
        this.noticestmsgid = noticestmsgid;
    }

    public String getMachinemsgid() {
        return machinemsgid;
    }

    public void setMachinemsgid(String machinemsgid) {
        this.machinemsgid = machinemsgid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getMchkey() {
        return mchkey;
    }

    public void setMchkey(String mchkey) {
        this.mchkey = mchkey;
    }

    public String getVipmchid() {
        return vipmchid;
    }

    public void setVipmchid(String vipmchid) {
        this.vipmchid = vipmchid;
    }

    public String getVipmchidkey() {
        return vipmchidkey;
    }

    public void setVipmchidkey(String vipmchidkey) {
        this.vipmchidkey = vipmchidkey;
    }

    public String getLoginmsgid() {
        return loginmsgid;
    }

    public void setLoginmsgid(String loginmsgid) {
        this.loginmsgid = loginmsgid;
    }

    @Override
    public String toString() {
        return "ClientDossier{" +
        ", clientid=" + clientid +
        ", clientname=" + clientname +
        ", nature=" + nature +
        ", address=" + address +
        ", contacts=" + contacts +
        ", contactsphone=" + contactsphone +
        ", Contactsposition=" + Contactsposition +
        ", message=" + message +
        ", homepage=" + homepage +
        ", company=" + company +
        ", Cooperativeproduct=" + Cooperativeproduct +
        ", Cooperativedate=" + Cooperativedate +
        ", Cooperativevaliddate=" + Cooperativevaliddate +
        ", Cooperativemode=" + Cooperativemode +
        ", parentmodular=" + parentmodular +
        ", teachermodular=" + teachermodular +
        ", paymode=" + paymode +
        ", vippaynumber=" + vippaynumber +
        ", vippaylen1=" + vippaylen1 +
        ", vippaymoney1=" + vippaymoney1 +
        ", vippaymoney12=" + vippaymoney12 +
        ", vippaymoney13=" + vippaymoney13 +
        ", vippaymoney14=" + vippaymoney14 +
        ", vippaylen2=" + vippaylen2 +
        ", vippaymoney2=" + vippaymoney2 +
        ", vippaymoney22=" + vippaymoney22 +
        ", vippaymoney23=" + vippaymoney23 +
        ", vippaymoney24=" + vippaymoney24 +
        ", parentspendpaynumber1=" + parentspendpaynumber1 +
        ", parentspendpaynumber2=" + parentspendpaynumber2 +
        ", parentspendpaynumber3=" + parentspendpaynumber3 +
        ", parentspendpaynumber4=" + parentspendpaynumber4 +
        ", teacherspendpaynumber1=" + teacherspendpaynumber1 +
        ", teacherspendpaynumber2=" + teacherspendpaynumber2 +
        ", teacherspendpaynumber3=" + teacherspendpaynumber3 +
        ", teacherspendpaynumber4=" + teacherspendpaynumber4 +
        ", teachermanager1=" + teachermanager1 +
        ", teachermanager2=" + teachermanager2 +
        ", Clientcheck=" + Clientcheck +
        ", smsnumber=" + smsnumber +
        ", machineservicetel1=" + machineservicetel1 +
        ", machineservicetel2=" + machineservicetel2 +
        ", moneyrate=" + moneyrate +
        ", phonemoney=" + phonemoney +
        ", appid=" + appid +
        ", appsecret=" + appsecret +
        ", bindmsgid=" + bindmsgid +
        ", bindmsgidteacher=" + bindmsgidteacher +
        ", checkmsgid=" + checkmsgid +
        ", noticemsgid=" + noticemsgid +
        ", homeworkmsgid=" + homeworkmsgid +
        ", paysuccessmsgid=" + paysuccessmsgid +
        ", payfailmsgid=" + payfailmsgid +
        ", feedbackmsgid=" + feedbackmsgid +
        ", leavemsgid=" + leavemsgid +
        ", praisemsgid=" + praisemsgid +
        ", noticestmsgid=" + noticestmsgid +
        ", machinemsgid=" + machinemsgid +
        ", mchid=" + mchid +
        ", mchkey=" + mchkey +
        ", vipmchid=" + vipmchid +
        ", vipmchidkey=" + vipmchidkey +
        ", loginmsgid=" + loginmsgid +
        "}";
    }
}
