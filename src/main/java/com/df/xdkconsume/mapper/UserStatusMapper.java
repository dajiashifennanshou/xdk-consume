package com.df.xdkconsume.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserStatusMapper {
    /**
     * 拿到用户身份角色
     * @param clientid
     * @param psid
     * @return
     */
    @Select("select ps_name,ps_student from Person_Status where clientid = #{clientid} and ps_id = #{psid}")
    Map<String,String> getUserStatus(@Param("clientid")String clientid, @Param("psid")String psid);
}
