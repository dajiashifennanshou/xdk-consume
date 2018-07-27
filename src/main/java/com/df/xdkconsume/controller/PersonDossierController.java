package com.df.xdkconsume.controller;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.df.xdkconsume.entity.BaseParam;
import com.df.xdkconsume.entity.ComsumeParam;
import com.df.xdkconsume.entity.PersonDossier;
import com.df.xdkconsume.entity.ResultData;
import com.df.xdkconsume.entity.SpendDetail;
import com.df.xdkconsume.entity.UserMoney;
import com.df.xdkconsume.service.PersonDossierService;
import com.df.xdkconsume.service.SpendDetailService;
import com.df.xdkconsume.utils.Constant;

/**
 * <p>
 *  前端控制器 用户表
 * </p>
 *
 * @author df
 * @since 2018-07-12
 */
@RestController
@RequestMapping("/user")
public class PersonDossierController {
	@Autowired
	private PersonDossierService personService;

	@Autowired
	private SpendDetailService spendService;

	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(path = "/comsume",method = RequestMethod.POST)
	public ResultData userConsume(@RequestBody ComsumeParam param) {
		String clientid = param.getClientid();
		String cardid = param.getCardid();
		String computer = param.getComputer();
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		String windowNumber = param.getWindowNumber();
		double spendMoney = param.getSpendMoney();
		if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(cardid)||StringUtils.isEmpty(computer)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)||StringUtils.isEmpty(windowNumber)||spendMoney == 0){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		if(cardid.length() != 8){
			ResultData data = new ResultData();
			data.setCode(300);
			data.setMsg("cardid wrong");
			return data;
		}
		cardid = cardid.substring(6,8)+cardid.substring(4,6)+cardid.substring(2,4)+cardid.substring(0,2);
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("pd_cardid = {0}",cardid).and("clientid = {0}",clientid);
		PersonDossier pDossier =  (PersonDossier) personService.selectOne(pWrapper);
		ResultData data = new ResultData();
		if(pDossier == null||pDossier.getPdAccountid() == null){
			data.setCode(Constant.CODE_PERSON_NO_USER);
			data.setMsg(Constant.MSG_PERSON_NO_USER);
			return data;
		}
		double beforeMoney = pDossier.getPdCashmoney();
		if(pDossier.getPdLoss() == 1){
			data.setCode(Constant.CODE_PERSON_LOST);
			data.setMsg(Constant.MSG_PERSON_LOST);
			return data;
		}
		double extroMoney = pDossier.getPdSubsidymoney();
		if(beforeMoney < 0 ||extroMoney < 0){
			data.setCode(Constant.CODE_PERSON_MOENY_FU);
			data.setMsg(Constant.MSG_PERSON_MOENY_FU);
			return data;
		}
		double allmoney = extroMoney + beforeMoney;
		if(allmoney < spendMoney){
			data.setCode(Constant.CODE_PERSON_NO_MOENY);
			data.setMsg(Constant.MSG_PERSON_NO_MONEY);
			return data;
		}
		//没有补助的直接消费一卡通
		if(extroMoney == 0){
			DecimalFormat df = new DecimalFormat("#.00");
			String afterMoney  = df.format(beforeMoney - spendMoney);
			pDossier.setPdCashmoney(Double.valueOf(afterMoney));
			if(!personService.updateForSet("pd_cashmoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_cashmoney = {0}",beforeMoney))){
				data.setCode(Constant.CODE_PERSON_UPDATE_WRONG);
				data.setMsg(Constant.MSG_PERSON_UPDATE_WRONG);
				return data;
			}
			SpendDetail detail = new SpendDetail();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] datetime = sFormat.format(new Date()).split(" ");
			detail.setClientid(pDossier.getClientid());
			detail.setSdCldate(datetime[0]);
			detail.setSdCltime(datetime[1]);
			detail.setSdComputer(computer);
			detail.setSdDatatype("餐台");
			detail.setSdDepartment(pDossier.getPdDepartment());
			String meal = "早";
			if(spendTime.length() >= 2){
				String time = spendTime.substring(0,2);
				int time_spend = Integer.valueOf(time);
				if(time_spend < 15 && time_spend > 10){
					meal = "午";
				}else if(time_spend < 20 && time_spend > 14){
					meal = "晚";
				}else if(time_spend < 24 && time_spend > 19){
					meal = "夜";
				}
			}
			//早中晚
			detail.setSdMeal(meal);
			detail.setSdMoney(spendMoney);
			detail.setSdMoneyaccount("现金");
			detail.setSdNewmoney(Double.valueOf(afterMoney));
			detail.setSdOldmoney(beforeMoney);
			detail.setSdOperator("");
			detail.setSdPdaccountid(pDossier.getPdAccountid());
			detail.setSdPdid(pDossier.getPdId());
			detail.setSdPdname(pDossier.getPdName());
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("消费");
			detail.setSdWindowno(windowNumber);
			detail.setSdComputer(computer);
			if(!spendService.insert(detail)){
				throw new RuntimeException("插入消费记录失败！");
			}
			UserMoney userMoney = new UserMoney();
			userMoney.setCashmoney(pDossier.getPdCashmoney());
			userMoney.setSubsidymoney(pDossier.getPdSubsidymoney());
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(userMoney);
			return data;
		}
		//完全消费补助金额
		if(extroMoney >= spendMoney){
			DecimalFormat df = new DecimalFormat("#.00");
			String afterMoney  = df.format(extroMoney - spendMoney);
			pDossier.setPdSubsidymoney(Double.valueOf(afterMoney));
			if(!personService.updateForSet("pd_subsidymoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney))){
				data.setCode(Constant.CODE_PERSON_UPDATE_WRONG);
				data.setMsg(Constant.MSG_PERSON_UPDATE_WRONG);
				return data;
			}
			SpendDetail detail = new SpendDetail();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] datetime = sFormat.format(new Date()).split(" ");
			detail.setClientid(pDossier.getClientid());
			detail.setSdCldate(datetime[0]);
			detail.setSdCltime(datetime[1]);
			detail.setSdComputer(computer);
			detail.setSdDatatype("餐台");
			detail.setSdDepartment(pDossier.getPdDepartment());
			String meal = "早";
			if(spendTime.length() >= 2){
				String time = spendTime.substring(0,2);
				int time_spend = Integer.valueOf(time);
				if(time_spend < 15 && time_spend > 10){
					meal = "午";
				}else if(time_spend < 20 && time_spend > 14){
					meal = "晚";
				}else if(time_spend < 24 && time_spend > 19){
					meal = "夜";
				}
			}
			//早中晚
			detail.setSdMeal(meal);
			detail.setSdMoney(spendMoney);
			detail.setSdMoneyaccount("补助");
			detail.setSdNewmoney(beforeMoney);
			detail.setSdOldmoney(beforeMoney);
			detail.setSdOperator("");
			detail.setSdPdaccountid(pDossier.getPdAccountid());
			detail.setSdPdid(pDossier.getPdId());
			detail.setSdPdname(pDossier.getPdName());
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("消费");
			detail.setSdWindowno(windowNumber);
			detail.setSdComputer(computer);
			if(!spendService.insert(detail)){
				throw new RuntimeException("插入消费记录失败！");
			}
			UserMoney userMoney = new UserMoney();
			userMoney.setCashmoney(pDossier.getPdCashmoney());
			userMoney.setSubsidymoney(pDossier.getPdSubsidymoney());
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(userMoney);
			return data;
		}else{
			//消费现金与补助金额，生成两条记录
			DecimalFormat df = new DecimalFormat("#.00");
			//消费完补助后需要现金消费的金额
			String should_pay  = df.format(spendMoney - extroMoney);
			Double after_moeny = Double.valueOf(df.format(beforeMoney - Double.valueOf(should_pay)));
			pDossier.setPdSubsidymoney(0.0);
			pDossier.setPdCashmoney(after_moeny);
			if(!personService.updateForSet("pd_subsidymoney = "+0+" ,pd_cashmoney = "+after_moeny,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cashmoney = {0}",beforeMoney))){
				data.setCode(Constant.CODE_PERSON_UPDATE_WRONG);
				data.setMsg(Constant.MSG_PERSON_UPDATE_WRONG);
				return data;
			}
			String meal = "早";
			if(spendTime.length() >= 2){
				String time = spendTime.substring(0,2);
				int time_spend = Integer.valueOf(time);
				if(time_spend < 15 && time_spend > 10){
					meal = "午";
				}else if(time_spend < 20 && time_spend > 14){
					meal = "晚";
				}else if(time_spend < 24 && time_spend > 19){
					meal = "夜";
				}
			}
			//补助明细插入
			SpendDetail detail = new SpendDetail();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] datetime = sFormat.format(new Date()).split(" ");
			detail.setClientid(pDossier.getClientid());
			detail.setSdCldate(datetime[0]);
			detail.setSdCltime(datetime[1]);
			detail.setSdComputer(computer);
			detail.setSdDatatype("餐台");
			detail.setSdDepartment(pDossier.getPdDepartment());
			detail.setSdMeal(meal);
			detail.setSdMoney(extroMoney);
			detail.setSdMoneyaccount("补助");
			detail.setSdNewmoney(after_moeny);
			detail.setSdOldmoney(beforeMoney);
			detail.setSdOperator("");
			detail.setSdPdaccountid(pDossier.getPdAccountid());
			detail.setSdPdid(pDossier.getPdId());
			detail.setSdPdname(pDossier.getPdName());
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("消费");
			detail.setSdWindowno(windowNumber);
			detail.setSdComputer(computer);
			if(!spendService.insert(detail)){
				throw new RuntimeException("插入消费记录失败！");
			}
			//现金明细插入
			SpendDetail detail1 = new SpendDetail();
			detail1.setClientid(pDossier.getClientid());
			detail1.setSdCldate(datetime[0]);
			detail1.setSdCltime(datetime[1]);
			detail1.setSdComputer(computer);
			detail1.setSdDatatype("餐台");
			detail1.setSdDepartment(pDossier.getPdDepartment());
			detail1.setSdMeal(meal);
			detail1.setSdMoney(Double.valueOf(should_pay));
			detail1.setSdMoneyaccount("现金");
			detail1.setSdNewmoney(beforeMoney);
			detail1.setSdOldmoney(beforeMoney);
			detail1.setSdOperator("");
			detail1.setSdPdaccountid(pDossier.getPdAccountid());
			detail1.setSdPdid(pDossier.getPdId());
			detail1.setSdPdname(pDossier.getPdName());
			detail1.setSdSpenddate(spendDate);
			detail1.setSdSpendtime(spendTime);
			detail1.setSdSpendtype("消费");
			detail1.setSdWindowno(windowNumber);
			detail1.setSdComputer(computer);
			if(!spendService.insert(detail1)){
				throw new RuntimeException("插入消费记录失败！");
			}
			UserMoney userMoney = new UserMoney();
			userMoney.setCashmoney(pDossier.getPdCashmoney());
			userMoney.setSubsidymoney(pDossier.getPdSubsidymoney());
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(userMoney);
			return data;
		}
	}

	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(path = "/comsume/nfc",method = RequestMethod.POST)
	public ResultData userConsumeNfc(@RequestBody ComsumeParam param) {
		String clientid = param.getClientid();
		String cardid = param.getCardid();
		String computer = param.getComputer();
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		String windowNumber = param.getWindowNumber();
		double spendMoney = param.getSpendMoney();
		if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(clientid)||StringUtils.isEmpty(cardid)||StringUtils.isEmpty(computer)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)||StringUtils.isEmpty(windowNumber)||spendMoney == 0){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("pd_cardid = {0}",cardid).and("clientid = {0}",clientid);
		PersonDossier pDossier =  (PersonDossier) personService.selectMap(pWrapper);
		ResultData data = new ResultData();
		if(pDossier == null||pDossier.getPdAccountid() == null){
			data.setCode(Constant.CODE_PERSON_NO_USER);
			data.setMsg(Constant.MSG_PERSON_NO_USER);
			return data;
		}
		double beforeMoney = pDossier.getPdCashmoney();
		if(pDossier.getPdLoss() == 1){
			data.setCode(Constant.CODE_PERSON_LOST);
			data.setMsg(Constant.MSG_PERSON_LOST);
			return data;
		}
		double extroMoney = pDossier.getPdSubsidymoney();
		if(beforeMoney < 0 ||extroMoney < 0){
			data.setCode(Constant.CODE_PERSON_MOENY_FU);
			data.setMsg(Constant.MSG_PERSON_MOENY_FU);
			return data;
		}
		double allmoney = extroMoney + beforeMoney;
		if(allmoney < spendMoney){
			data.setCode(Constant.CODE_PERSON_NO_MOENY);
			data.setMsg(Constant.MSG_PERSON_NO_MONEY);
			return data;
		}
		//没有补助的直接消费一卡通
		if(extroMoney == 0){
			DecimalFormat df = new DecimalFormat("#.00");
			String afterMoney  = df.format(beforeMoney - spendMoney);
			pDossier.setPdCashmoney(Double.valueOf(afterMoney));
			if(!personService.updateForSet("pd_cashmoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_cashmoney = {0}",beforeMoney))){
				data.setCode(Constant.CODE_PERSON_UPDATE_WRONG);
				data.setMsg(Constant.MSG_PERSON_UPDATE_WRONG);
				return data;
			}
			SpendDetail detail = new SpendDetail();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] datetime = sFormat.format(new Date()).split(" ");
			detail.setClientid(pDossier.getClientid());
			detail.setSdCldate(datetime[0]);
			detail.setSdCltime(datetime[1]);
			detail.setSdComputer(computer);
			detail.setSdDatatype("手持");
			detail.setSdDepartment(pDossier.getPdDepartment());
			detail.setSdDatatype("在线");
			detail.setSdDepartment(pDossier.getPdDepartment());
			String meal = "早";
			if(spendTime.length() >= 2){
				String time = spendTime.substring(0,2);
				int time_spend = Integer.valueOf(time);
				if(time_spend < 15 && time_spend > 10){
					meal = "午";
				}else if(time_spend < 20 && time_spend > 14){
					meal = "晚";
				}else if(time_spend < 24 && time_spend > 19){
					meal = "夜";
				}
			}
			//早中晚
			detail.setSdMeal(meal);
			detail.setSdMoney(spendMoney);
			detail.setSdMoneyaccount("现金");
			detail.setSdNewmoney(Double.valueOf(afterMoney));
			detail.setSdOldmoney(beforeMoney);
			detail.setSdOperator("");
			detail.setSdPdaccountid(pDossier.getPdAccountid());
			detail.setSdPdid(pDossier.getPdId());
			detail.setSdPdname(pDossier.getPdName());
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("消费");
			detail.setSdWindowno(windowNumber);
			detail.setSdComputer(computer);
			if(!spendService.insert(detail)){
				throw new RuntimeException("插入消费记录失败！");
			}
			UserMoney userMoney = new UserMoney();
			userMoney.setCashmoney(pDossier.getPdCashmoney());
			userMoney.setSubsidymoney(pDossier.getPdSubsidymoney());
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(userMoney);
			return data;
		}
		//完全消费补助金额
		if(extroMoney >= spendMoney){
			DecimalFormat df = new DecimalFormat("#.00");
			String afterMoney  = df.format(extroMoney - spendMoney);
			pDossier.setPdSubsidymoney(Double.valueOf(afterMoney));
			if(!personService.updateForSet("pd_subsidymoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney))){
				data.setCode(Constant.CODE_PERSON_UPDATE_WRONG);
				data.setMsg(Constant.MSG_PERSON_UPDATE_WRONG);
				return data;
			}
			SpendDetail detail = new SpendDetail();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] datetime = sFormat.format(new Date()).split(" ");
			detail.setClientid(pDossier.getClientid());
			detail.setSdCldate(datetime[0]);
			detail.setSdCltime(datetime[1]);
			detail.setSdComputer(computer);
			detail.setSdDatatype("手持");
			detail.setSdDepartment(pDossier.getPdDepartment());
			detail.setSdDatatype("在线");
			detail.setSdDepartment(pDossier.getPdDepartment());
			String meal = "早";
			if(spendTime.length() >= 2){
				String time = spendTime.substring(0,2);
				int time_spend = Integer.valueOf(time);
				if(time_spend < 15 && time_spend > 10){
					meal = "午";
				}else if(time_spend < 20 && time_spend > 14){
					meal = "晚";
				}else if(time_spend < 24 && time_spend > 19){
					meal = "夜";
				}
			}
			//早中晚
			detail.setSdMeal(meal);
			detail.setSdMoney(spendMoney);
			detail.setSdMoneyaccount("补助");
			detail.setSdNewmoney(beforeMoney);
			detail.setSdOldmoney(beforeMoney);
			detail.setSdOperator("");
			detail.setSdPdaccountid(pDossier.getPdAccountid());
			detail.setSdPdid(pDossier.getPdId());
			detail.setSdPdname(pDossier.getPdName());
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("消费");
			detail.setSdWindowno(windowNumber);
			detail.setSdComputer(computer);
			if(!spendService.insert(detail)){
				throw new RuntimeException("插入消费记录失败！");
			}
			UserMoney userMoney = new UserMoney();
			userMoney.setCashmoney(pDossier.getPdCashmoney());
			userMoney.setSubsidymoney(pDossier.getPdSubsidymoney());
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(userMoney);
			return data;
		}else{
			//消费现金与补助金额，生成两条记录
			DecimalFormat df = new DecimalFormat("#.00");
			//消费完补助后需要现金消费的金额
			String should_pay  = df.format(spendMoney - extroMoney);
			Double after_moeny = Double.valueOf(df.format(beforeMoney - Double.valueOf(should_pay)));
			pDossier.setPdSubsidymoney(0.0);
			pDossier.setPdCashmoney(after_moeny);
			if(!personService.updateForSet("pd_subsidymoney = "+0+" ,pd_cashmoney = "+after_moeny,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cashmoney = {0}",beforeMoney))){
				data.setCode(Constant.CODE_PERSON_UPDATE_WRONG);
				data.setMsg(Constant.MSG_PERSON_UPDATE_WRONG);
				return data;
			}
			String meal = "早";
			if(spendTime.length() >= 2){
				String time = spendTime.substring(0,2);
				int time_spend = Integer.valueOf(time);
				if(time_spend < 15 && time_spend > 10){
					meal = "午";
				}else if(time_spend < 20 && time_spend > 14){
					meal = "晚";
				}else if(time_spend < 24 && time_spend > 19){
					meal = "夜";
				}
			}
			//补助明细插入
			SpendDetail detail = new SpendDetail();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String[] datetime = sFormat.format(new Date()).split(" ");
			detail.setClientid(pDossier.getClientid());
			detail.setSdCldate(datetime[0]);
			detail.setSdCltime(datetime[1]);
			detail.setSdComputer(computer);
			detail.setSdDatatype("手持");
			detail.setSdDatatype("在线");
			detail.setSdDepartment(pDossier.getPdDepartment());
			detail.setSdMeal(meal);
			detail.setSdMoney(extroMoney);
			detail.setSdMoneyaccount("补助");
			detail.setSdNewmoney(after_moeny);
			detail.setSdOldmoney(beforeMoney);
			detail.setSdOperator("");
			detail.setSdPdaccountid(pDossier.getPdAccountid());
			detail.setSdPdid(pDossier.getPdId());
			detail.setSdPdname(pDossier.getPdName());
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("消费");
			detail.setSdWindowno(windowNumber);
			detail.setSdComputer(computer);
			if(!spendService.insert(detail)){
				throw new RuntimeException("插入消费记录失败！");
			}
			//现金明细插入
			SpendDetail detail1 = new SpendDetail();
			detail1.setClientid(pDossier.getClientid());
			detail1.setSdCldate(datetime[0]);
			detail1.setSdCltime(datetime[1]);
			detail1.setSdComputer(computer);
			detail1.setSdDatatype("手持");
			detail1.setSdDatatype("在线");
			detail1.setSdDepartment(pDossier.getPdDepartment());
			detail1.setSdMeal(meal);
			detail1.setSdMoney(Double.valueOf(should_pay));
			detail1.setSdMoneyaccount("现金");
			detail1.setSdNewmoney(beforeMoney);
			detail1.setSdOldmoney(beforeMoney);
			detail1.setSdOperator("");
			detail1.setSdPdaccountid(pDossier.getPdAccountid());
			detail1.setSdPdid(pDossier.getPdId());
			detail1.setSdPdname(pDossier.getPdName());
			detail1.setSdSpenddate(spendDate);
			detail1.setSdSpendtime(spendTime);
			detail1.setSdSpendtype("消费");
			detail1.setSdWindowno(windowNumber);
			detail1.setSdComputer(computer);
			if(!spendService.insert(detail1)){
				throw new RuntimeException("插入消费记录失败！");
			}
			UserMoney userMoney = new UserMoney();
			userMoney.setCashmoney(pDossier.getPdCashmoney());
			userMoney.setSubsidymoney(pDossier.getPdSubsidymoney());
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(userMoney);
			return data;
		}
	}
	
	@RequestMapping(path = "/info",method = RequestMethod.POST)
	public ResultData getUserinfo(@RequestBody BaseParam param) throws IOException {
		String clientid = param.getClientid();
		String cardid = param.getCardid();
		ResultData data = new ResultData();
		if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(cardid)){
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		if(cardid.length() != 8){
			data.setCode(300);
			data.setMsg("cardid wrong");
			return data;
		}
		cardid = cardid.substring(6,8)+cardid.substring(4,6)+cardid.substring(2,4)+cardid.substring(0,2);
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("pd_cardid = {0}",cardid).and("clientid = {0}",clientid);
		PersonDossier pDossier =  (PersonDossier) personService.selectOne(pWrapper);
		if(pDossier == null||pDossier.getPdAccountid() == null){
			data.setCode(Constant.CODE_PERSON_NO_USER);
			data.setMsg(Constant.MSG_PERSON_NO_USER);
		}else{
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(pDossier);
		}
		return data;
	}
}

