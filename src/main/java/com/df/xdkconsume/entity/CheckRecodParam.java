package com.df.xdkconsume.entity;

import javax.validation.constraints.NotBlank;

public class CheckRecodParam extends BaseParam{
    private String date,time,mactype,checkmac,checktype,style;

    @NotBlank(message = "日期不能为空")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @NotBlank(message = "时间不能为空")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NotBlank(message = "mactype不能为空")
    public String getMactype() {
        return mactype;
    }

    public void setMactype(String mactype) {
        this.mactype = mactype;
    }
    @NotBlank(message = "checkmac不能为空")
    public String getCheckmac() {
        return checkmac;
    }

    public void setCheckmac(String checkmac) {
        this.checkmac = checkmac;
    }
    @NotBlank(message = "checktype不能为空")
    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }
    @NotBlank(message = "style不能为空")
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
