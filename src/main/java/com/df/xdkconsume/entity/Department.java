package com.df.xdkconsume.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author df
 * @since 2018-08-02
 */
@TableName("Department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String deptId;
    private String deptName;
    private String deptUpid;
    private String changedate;
    private Integer changestate;
    private Integer autoid;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptUpid() {
        return deptUpid;
    }

    public void setDeptUpid(String deptUpid) {
        this.deptUpid = deptUpid;
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
        return "Department{" +
        ", clientid=" + clientid +
        ", deptId=" + deptId +
        ", deptName=" + deptName +
        ", deptUpid=" + deptUpid +
        ", changedate=" + changedate +
        ", changestate=" + changestate +
        ", autoid=" + autoid +
        "}";
    }
}
