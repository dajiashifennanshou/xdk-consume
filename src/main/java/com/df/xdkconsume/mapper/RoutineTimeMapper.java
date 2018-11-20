package com.df.xdkconsume.mapper;

import com.df.xdkconsume.entity.RoutineTime;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface RoutineTimeMapper extends BaseMapper<RoutineTime> {
	@Select("select * from Routine_Time where rt_startdate <= #{sdate} and  rt_enddate >= #{sdate} and rt_starttime <= #{stime} and  rt_endtime  >= #{stime}"
			+ " and rt_pdstatus = #{status} and rt_school = 1 and rt_week${week} = 1 and clientid = #{clientid}")
	RoutineTime findRoutineTimes(@Param("clientid")String clientid, @Param("status")String status, @Param("sdate")String sdate,@Param("stime") String stime,@Param("week") String week);
	@Select("select * from Routine_Time where rt_startdate <= #{sdate} and  rt_enddate >= #{sdate} and rt_starttime <= #{stime} and  rt_endtime  >= #{stime}"
			+ " and rt_pdstatus = #{status} and rt_apartment = 1 and rt_week${week} = 1 and clientid = #{clientid}")
	RoutineTime findRoutineTimea(@Param("clientid")String clientid, @Param("status")String status, @Param("sdate")String sdate,@Param("stime") String stime,@Param("week") String week);
	@Select("select * from Routine_Time where rt_startdate <= #{sdate} and  rt_enddate >= #{sdate} and rt_starttime <= #{stime} and  rt_endtime  >= #{stime}"
			+ " and rt_pdstatus = #{status} and rt_class = 1 and rt_week${week} = 1 and clientid = #{clientid}")
	RoutineTime findRoutineTimec(@Param("clientid")String clientid, @Param("status")String status, @Param("sdate")String sdate,@Param("stime") String stime,@Param("week") String week);
}
