package com.df.xdkconsume.service.impl;

import com.df.xdkconsume.entity.CanTai;
import com.df.xdkconsume.mapper.CanTaiMapper;
import com.df.xdkconsume.service.CanTaiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class CanTaiServiceImpl extends ServiceImpl<CanTaiMapper, CanTai> implements CanTaiService {

}
