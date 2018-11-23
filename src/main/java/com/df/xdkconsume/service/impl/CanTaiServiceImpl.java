package com.df.xdkconsume.service.impl;

import com.df.xdkconsume.entity.CanTai;
import com.df.xdkconsume.mapper.CanTaiMapper;
import com.df.xdkconsume.service.CanTaiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author df
 * @since 2018-11-20
 */
@Service
public class CanTaiServiceImpl extends ServiceImpl<CanTaiMapper, CanTai> implements CanTaiService {
	@Autowired
	CanTaiMapper mapper;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean insertCantai(CanTai cantai) {
		return mapper.insertCantai(cantai);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean updateCantaiStatus(String status,String ordernumber) {
		return mapper.updateCantaiStatus(status,ordernumber);
	}
}
