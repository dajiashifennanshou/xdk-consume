package com.df.xdkconsume.mapper;

import com.df.xdkconsume.entity.CanTai;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author df
 * @since 2018-11-20
 */
@Mapper
public interface CanTaiMapper extends BaseMapper<CanTai> {
	
	@Insert("insert into CanTai values(#{cantai.clientid},#{cantai.outTradeNo},#{cantai.totalFee},#{cantai.authCode},#{cantai.mchCreateIp},#{cantai.spenddate},#{cantai.spendtime},#{cantai.cashliststring},#{cantai.one},#{cantai.two},#{cantai.onename},#{cantai.twoname})")
	boolean insertCantai(@Param("cantai")CanTai caTai);
	
	@Update("update CanTai set mch_create_ip = #{status} where out_trade_no = #{ordernumber}")
	boolean updateCantaiStatus(@Param("status")String status, @Param("ordernumber")String ordernumber);
}
