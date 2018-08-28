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
@TableName("Food_Menu")
public class FoodMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientid;
    private String fmId;
    private String fmCanteen;
    private String fmName;
    private String fmUnit;
    private Double fmUnitprice;
    private Double fmPackmoney;
    private String fmTaste;
    private String fmType;
    private String fmNote;
    private byte[] fmNotepicture;
    private Integer fmEnabled;
    private String fmPicturefile;
    private String fmPlateid;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    public String getFmCanteen() {
        return fmCanteen;
    }

    public void setFmCanteen(String fmCanteen) {
        this.fmCanteen = fmCanteen;
    }

    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    public String getFmUnit() {
        return fmUnit;
    }

    public void setFmUnit(String fmUnit) {
        this.fmUnit = fmUnit;
    }

    public Double getFmUnitprice() {
        return fmUnitprice;
    }

    public void setFmUnitprice(Double fmUnitprice) {
        this.fmUnitprice = fmUnitprice;
    }

    public Double getFmPackmoney() {
        return fmPackmoney;
    }

    public void setFmPackmoney(Double fmPackmoney) {
        this.fmPackmoney = fmPackmoney;
    }

    public String getFmTaste() {
        return fmTaste;
    }

    public void setFmTaste(String fmTaste) {
        this.fmTaste = fmTaste;
    }

    public String getFmType() {
        return fmType;
    }

    public void setFmType(String fmType) {
        this.fmType = fmType;
    }

    public String getFmNote() {
        return fmNote;
    }

    public void setFmNote(String fmNote) {
        this.fmNote = fmNote;
    }

    public byte[] getFmNotepicture() {
        return fmNotepicture;
    }

    public void setFmNotepicture(byte[] fmNotepicture) {
        this.fmNotepicture = fmNotepicture;
    }

    public Integer getFmEnabled() {
        return fmEnabled;
    }

    public void setFmEnabled(Integer fmEnabled) {
        this.fmEnabled = fmEnabled;
    }

    public String getFmPicturefile() {
        return fmPicturefile;
    }

    public void setFmPicturefile(String fmPicturefile) {
        this.fmPicturefile = fmPicturefile;
    }

    public String getFmPlateid() {
        return fmPlateid;
    }

    public void setFmPlateid(String fmPlateid) {
        this.fmPlateid = fmPlateid;
    }

    @Override
    public String toString() {
        return "FoodMenu{" +
        ", clientid=" + clientid +
        ", fmId=" + fmId +
        ", fmCanteen=" + fmCanteen +
        ", fmName=" + fmName +
        ", fmUnit=" + fmUnit +
        ", fmUnitprice=" + fmUnitprice +
        ", fmPackmoney=" + fmPackmoney +
        ", fmTaste=" + fmTaste +
        ", fmType=" + fmType +
        ", fmNote=" + fmNote +
        ", fmNotepicture=" + fmNotepicture +
        ", fmEnabled=" + fmEnabled +
        ", fmPicturefile=" + fmPicturefile +
        ", fmPlateid=" + fmPlateid +
        "}";
    }
}
