package com.df.xdkconsume.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author df
* @version 创建时间：2018年9月12日 上午10:24:46
* @Description 查找设备信息是否注册
*/
@Mapper
public interface DeviceMapper {
	@Select("select card_code from card_code where card_code = #{deviceinfo}")
	String isSignDevice(String deviceinfo);
	
	@Insert("insert into Card_Code values('0000',#{time},#{deviceinfo})")
	boolean signDevice(@Param("deviceinfo")String deviceinfo,@Param("time")String time);
}
