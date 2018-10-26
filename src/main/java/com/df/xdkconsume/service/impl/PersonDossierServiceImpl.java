package com.df.xdkconsume.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.df.xdkconsume.entity.PersonDossier;
import com.df.xdkconsume.mapper.PersonDossierMapper;
import com.df.xdkconsume.mapper.UserStatusMapper;
import com.df.xdkconsume.service.PersonDossierService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author df
 * @since 2018-07-12
 */
@Service
public class PersonDossierServiceImpl extends ServiceImpl<PersonDossierMapper, PersonDossier> implements PersonDossierService {
    @Autowired
    UserStatusMapper userStatusMapper;

    public PersonDossier getUserByCardId(String clientid,String cardid){
        EntityWrapper<PersonDossier> wrapper = new EntityWrapper<>();
        wrapper.where("pd_cardid = {0}",cardid).and("clientid = {0}",clientid);
        return selectOne(wrapper);
    }

    public boolean updateUserSchool(PersonDossier user,String state,String time){
        EntityWrapper<PersonDossier> wrapper = new EntityWrapper<>();
        wrapper.where("pd_accountid = {0}",user.getPdAccountid()).and("clientid = {0}",user.getClientid());
        PersonDossier dossier = new PersonDossier();
        dossier.setPdSchoolstatus(state);
        dossier.setPdSchooldate(time);
        return  update(dossier,wrapper);
    }

    public boolean updateUserApartment(PersonDossier user,String state,String time){
        EntityWrapper<PersonDossier> wrapper = new EntityWrapper<>();
        wrapper.where("pd_accountid = {0}",user.getPdAccountid()).and("clientid = {0}",user.getClientid());
        PersonDossier dossier = new PersonDossier();
        dossier.setPdApartmentstatus(state);
        dossier.setPdApartmentdate(time);
        return  update(dossier,wrapper);
    }

    public Map<String,String> getUserStatus(String clientid,String psid){
        return userStatusMapper.getUserStatus(clientid,psid);
    }
}
