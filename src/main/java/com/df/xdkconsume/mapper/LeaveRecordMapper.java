package com.df.xdkconsume.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.df.xdkconsume.entity.LeaveRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author df
 * @since 2018-10-23
 */
@Mapper
public interface LeaveRecordMapper extends BaseMapper<LeaveRecord> {
    @Select("select lr_leavetype,lr_startdate,lr_starttime,lr_enddate,lr_endtime from Leave_Record " +
            "where changestate <> 99 and clientid = #{clientid} and lr_pdaccountid = #{accountid} and ((lr_startdate < #{sdate} and lr_enddate > #{sdate}) or" +
            "(lr_startdate =  #{sdate} and lr_enddate > #{sdate} and lr_starttime <= #{stime}) or " +
            "(lr_startdate <  #{sdate} and lr_enddate = #{sdate} and lr_endtime  >= #{stime}) or " +
            "(lr_startdate = #{sdate} and lr_enddate = #{sdate} and lr_starttime <= #{stime} and lr_endtime >= #{stime}))")
    LeaveRecord getLeaveRecodeIntime(@Param("clientid")String clientid, @Param("accountid")String accountid, @Param("sdate")String sdate,@Param("stime") String stime);

    @Select("select lt_name from Leave_Type where clientid = #{clientid} and lt_id = #{lrtype}")
    String getLeaveType(@Param("clientid")String clientid,@Param("lrtype")String lrtype);
}
