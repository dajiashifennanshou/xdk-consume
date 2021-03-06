package com.df.xdkconsume.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.df.xdkconsume.entity.*;
import com.df.xdkconsume.pay.WXPay;
import com.df.xdkconsume.pay.WXPayUtil;
import com.df.xdkconsume.service.DepartmentService;
import com.df.xdkconsume.service.ExpiredCardService;
import com.df.xdkconsume.service.PersonDossierService;
import com.df.xdkconsume.service.SpendDetailService;
import com.df.xdkconsume.service.impl.ClientDossierServiceImpl;
import com.df.xdkconsume.utils.Constant;
import com.df.xdkconsume.utils.VerifyUtil;
import com.df.xdkconsume.utils.WxSendmsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author df
 * @version 创建时间：2018年10月15日 上午10:53:29
 * @Description 手持机接口
 */
@RestController
@RequestMapping("/user")
public class HandSetController {
	@Autowired
	private PersonDossierService personService;

	@Autowired
	private SpendDetailService spendService;

	@Autowired
	private ExpiredCardService expiredService;

	@Autowired
	private DepartmentService departmentService;

    @Autowired
    private ClientDossierServiceImpl cdService;

	@Autowired()
	WXPay wxPay;

	@Value("${pay-config.mch_id}")
	String mchid;
	/**
	 * 根据账号拿到用户信息
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/info/acccountid",method = RequestMethod.POST)
	public ResultData getUserinfoByAccountid(@RequestBody AccountidParam param) throws IOException {
		String clientid = param.getClientid();
		String accountid = param.getAccountid();
		ResultData data = new ResultData();
		if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(accountid)){
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		if(accountid.length() != 10){
			data.setCode(300);
			data.setMsg("账号必须为十位");
			return data;
		}
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("pd_accountid = {0}",accountid).and("clientid = {0}",clientid);
		PersonDossier pDossier =  (PersonDossier) personService.selectOne(pWrapper);
		if(pDossier == null||pDossier.getPdAccountid() == null){
			data.setCode(Constant.CODE_PERSON_NO_USER);
			data.setMsg(Constant.MSG_PERSON_NO_USER);
		}else{
			EntityWrapper<Department> dewrapper = new EntityWrapper<>();
			dewrapper.where("dept_id = {0} and clientid = {1}", pDossier.getPdDepartment(),pDossier.getClientid());
			Department department =  (Department) departmentService.selectOne(dewrapper);
			pDossier.setPdDepartment(department.getDeptName());
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(pDossier);
		}
		return data;
	}
	/**
	 * 挂失卡
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(path = "/lostcard",method = RequestMethod.POST)
	public ResultData userLostCard(@RequestBody AccountidParam param) throws IOException {
		String clientid = param.getClientid();
		String accountid = param.getAccountid();
		ResultData data = new ResultData();
		if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(accountid)){
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		if(accountid.length() != 10){
			data.setCode(300);
			data.setMsg("账号必须为十位");
			return data;
		}
		ExpiredCard cardRecord = expiredService.selectOne(new EntityWrapper<ExpiredCard>().where("clientid = {0}", clientid).and("ec_pdaccountid = {0}",accountid));
		if(cardRecord == null||cardRecord.getEcPdaccountid() == null){
			PersonDossier dossier = personService.selectOne(new EntityWrapper<PersonDossier>().where("clientid = {0}",clientid).and("pd_accountid = {0}",accountid));
			ExpiredCard caExpiredCard = new ExpiredCard();
			caExpiredCard.setChangedate(dossier.getChangedate());
			caExpiredCard.setChangestate(dossier.getChangestate());
			caExpiredCard.setClientid(dossier.getClientid());
			caExpiredCard.setEcCardid(dossier.getPdCardid());
			caExpiredCard.setEcCardxf(dossier.getPdCardxf());
			caExpiredCard.setEcComputer("01");
			caExpiredCard.setEcDepartment(dossier.getPdDepartment());
			caExpiredCard.setEcLongcardid(dossier.getPdLongcardid());
			caExpiredCard.setEcOperator("001");
			caExpiredCard.setEcPdaccountid(dossier.getPdAccountid());
			caExpiredCard.setEcPdid(dossier.getPdId());
			caExpiredCard.setEcPdname(dossier.getPdName());
			String[] datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).split(" ");
			caExpiredCard.setEcDate(datetime[0]);
			caExpiredCard.setEcTime(datetime[1]);
			if(!expiredService.insert(caExpiredCard)){
				data.setCode(Constant.CODE_LOST_CARD);
				data.setMsg(Constant.MSG_LOST_CARD);
				return data;
			}
		}
		if(!personService.updateForSet("pd_loss = 1",new EntityWrapper<PersonDossier>().where("clientid = {0}",clientid).and("pd_accountid = {0}",accountid))){
			data.setCode(Constant.CODE_LOST_CARD);
			data.setMsg(Constant.MSG_LOST_CARD);
		}else{
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
		}
		return data;
	}
	/**
	 * 根据clientid拿到所有人的信息
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/info/all",method = RequestMethod.POST)
	public ResultData getUserinfoByAccountid(@RequestBody BaseParam param) throws IOException {
		String clientid = param.getClientid();
		ResultData data = new ResultData();
		if(StringUtils.isEmpty(clientid)){
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("clientid = {0}",clientid).and("pd_subsidymoney + pd_cashmoney > 0.5");
		List<PersonDossier> pDossiers =  personService.selectList(pWrapper);
		if(pDossiers.size() == 0){
			data.setCode(Constant.CODE_PERSON_NO_USER);
			data.setMsg(Constant.MSG_PERSON_NO_USER);
		}else{
			List<NfcUser> users = new ArrayList<NfcUser>();
			for (PersonDossier pDossier : pDossiers) {
				NfcUser user = new NfcUser();
				user.setClientid(pDossier.getClientid());
				user.setPdAccountid(pDossier.getPdAccountid());
				user.setPdCardid(pDossier.getPdCardid());
				user.setPdCashmoney(pDossier.getPdCashmoney());
				user.setPdLoss(pDossier.getPdLoss());
				user.setPdName(pDossier.getPdName());
				user.setPdSubsidymoney(pDossier.getPdSubsidymoney());
				users.add(user);
			}
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(users);
		}
		return data;
	}
	/**
	 * 是否在线
	 * @return
	 */
	@RequestMapping(path = "/online",method = RequestMethod.POST)
	public ResultData getisOnline(){
		ResultData data = new ResultData();
		data.setCode(Constant.CODE_SUCCESS);
		data.setMsg(Constant.MSG_SUCCESS);
		data.setData("ok");
		return data;
	}
	/**
	 * 手持机消费一卡通在线
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(path = "/comsume/nfc",method = RequestMethod.POST)
	public synchronized ResultData userConsumeNfc(@RequestBody ComsumeParam param) {
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
			if(!personService.updateForSet("pd_cashmoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_cashmoney = {0}",beforeMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
			if(!personService.updateForSet("pd_subsidymoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
			detail.setSdNewmoney(Double.valueOf(afterMoney));
			detail.setSdOldmoney(extroMoney);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
			if(!personService.updateForSet("pd_subsidymoney = "+0+" ,pd_cashmoney = "+after_moeny,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cashmoney = {0}",beforeMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
			detail.setSdDepartment(pDossier.getPdDepartment());
			detail.setSdMeal(meal);
			detail.setSdMoney(extroMoney);
			detail.setSdMoneyaccount("补助");
			detail.setSdNewmoney(0.0);
			detail.setSdOldmoney(extroMoney);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
			}
			//现金明细插入
			SpendDetail detail1 = new SpendDetail();
			detail1.setClientid(pDossier.getClientid());
			detail1.setSdCldate(datetime[0]);
			detail1.setSdCltime(datetime[1]);
			detail1.setSdComputer(computer);
			detail1.setSdDatatype("手持");
			detail1.setSdDepartment(pDossier.getPdDepartment());
			detail1.setSdMeal(meal);
			detail1.setSdMoney(Double.valueOf(should_pay));
			detail1.setSdMoneyaccount("现金");
			detail1.setSdNewmoney(after_moeny);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
	/**
	 * 手持机消费一卡通(脱机)
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(path = "/comsume/nfc/outline",method = RequestMethod.POST)
	public synchronized ResultData userConsumeNfcOutLine(@RequestBody ComsumeParam param) {
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
		PersonDossier pDossier =  (PersonDossier) personService.selectOne(pWrapper);
		ResultData data = new ResultData();
		if(pDossier == null||pDossier.getPdAccountid() == null){
			data.setCode(Constant.CODE_PERSON_NO_USER);
			data.setMsg(Constant.MSG_PERSON_NO_USER);
			return data;
		}
		double beforeMoney = pDossier.getPdCashmoney();//现金余额
		double extroMoney = pDossier.getPdSubsidymoney();//补助余额
		//没有补助的直接消费一卡通
		if(extroMoney == 0){
			DecimalFormat df = new DecimalFormat("#.00");
			String afterMoney  = df.format(beforeMoney - spendMoney);
			pDossier.setPdCashmoney(Double.valueOf(afterMoney));
			if(!personService.updateForSet("pd_cashmoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_cashmoney = {0}",beforeMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
			detail.setSdDatatype("手持脱机");
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
			if(!personService.updateForSet("pd_subsidymoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
			detail.setSdDatatype("手持脱机");
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
			detail.setSdNewmoney(Double.valueOf(afterMoney));
			detail.setSdOldmoney(extroMoney);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
			if(!personService.updateForSet("pd_subsidymoney = "+0+" ,pd_cashmoney = "+after_moeny,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cashmoney = {0}",beforeMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
			detail.setSdDatatype("手持脱机");
			detail.setSdDepartment(pDossier.getPdDepartment());
			detail.setSdMeal(meal);
			detail.setSdMoney(extroMoney);
			detail.setSdMoneyaccount("补助");
			detail.setSdNewmoney(0.0);
			detail.setSdOldmoney(extroMoney);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
			}
			//现金明细插入
			SpendDetail detail1 = new SpendDetail();
			detail1.setClientid(pDossier.getClientid());
			detail1.setSdCldate(datetime[0]);
			detail1.setSdCltime(datetime[1]);
			detail1.setSdComputer(computer);
			detail1.setSdDatatype("手持脱机");
			detail1.setSdDepartment(pDossier.getPdDepartment());
			detail1.setSdMeal(meal);
			detail1.setSdMoney(Double.valueOf(should_pay));
			detail1.setSdMoneyaccount("现金");
			detail1.setSdNewmoney(after_moeny);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
	/**
	 * 手持机扫描二维码扣款一卡通
	 * @param param
	 * @return
	 */
	public ResultData doCardCodeComsume(ComsumeCodeParam param) {
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		double spendMoney = param.getSpendMoney();
		String computer = param.getComputer();
		String wimdownumber = param.getWindowNumber();
		String codeParamsString = param.getAuthCode();
		String[] codeParams =codeParamsString.split("-");
		String clientid = codeParams[1];
		String accountid = codeParams[2];
		String timestampsString = codeParams[3];
		String sign = codeParams[4];
		long timestamp = Long.valueOf(timestampsString);
		TreeMap<String, String> parameters = new TreeMap<>();
		parameters.put("clientid", clientid);
		parameters.put("accountid", accountid);
		parameters.put("timestamp", timestampsString);
		String resultdata = VerifyUtil.createSign(parameters,Constant.SIGN_KEY);
		if(!resultdata.equals(sign)){
			ResultData data = new ResultData();
			data.setCode(213);
			data.setMsg("签名无效，无效二维码");
			return data;
		}
		if(System.currentTimeMillis() - Long.valueOf(timestamp) > 1000*70){
			ResultData data = new ResultData();
			data.setCode(212);
			data.setMsg("二维码过期");
			return data;
		}
		if(StringUtils.isEmpty(accountid)||StringUtils.isEmpty(clientid)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("pd_accountid = {0}",accountid).and("clientid = {0}",clientid);
		PersonDossier pDossier =  (PersonDossier) personService.selectOne(pWrapper);
		ResultData data = new ResultData();
		if(pDossier == null||pDossier.getPdAccountid() == null){
			data.setCode(Constant.CODE_PERSON_NO_USER);
			data.setMsg(Constant.MSG_PERSON_NO_USER);
			return data;
		}
		if(pDossier.getPdLoss() == 1){
			data.setCode(Constant.CODE_PERSON_LOST);
			data.setMsg(Constant.MSG_PERSON_LOST);
			return data;
		}
		double beforeMoney = pDossier.getPdCashmoney();
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
		ResultData result = countDowmMoenyByAccountid(spendDate,spendTime,spendMoney,clientid,accountid,wimdownumber,computer);
		if(result.getCode() != Constant.CODE_SUCCESS){
			return result;
		}
		EntityWrapper<PersonDossier> qurymy = new EntityWrapper<>();
		qurymy.where("pd_accountid = {0}",accountid).and("clientid = {0}",clientid);
		PersonDossier qurymyuser =  (PersonDossier) personService.selectOne(pWrapper);
//		UserMoney userMoney = new UserMoney();
//		userMoney.setCashmoney(qurymyuser.getPdCashmoney());
//		userMoney.setSubsidymoney(qurymyuser.getPdSubsidymoney());
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//发送模板消息
					ClientDossier clientDossier = cdService.selectOne(new EntityWrapper<ClientDossier>().where("clientid = #{0}",qurymyuser.getClientid()));
					WxSendmsg.sendWxMsg(qurymyuser,clientDossier,param);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		data.setCode(Constant.CODE_SUCCESS);
		data.setMsg(Constant.MSG_SUCCESS);
		data.setData(qurymyuser);
		return data;
	}
	/**
	 * 插入记录操作余额
	 * @param spendDate
	 * @param spendTime
	 * @param spendMoney
	 * @param clientid
	 * @param accountid
	 * @param windowNumber
	 * @param computer
	 * @return
	 */
	private ResultData countDowmMoenyByAccountid(String spendDate,String spendTime,Double spendMoney,String clientid, String accountid, String windowNumber, String computer) {
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("pd_accountid = {0}",accountid).and("clientid = {0}",clientid);
		PersonDossier pDossier =  (PersonDossier) personService.selectOne(pWrapper);
		ResultData data = new ResultData();
		double beforeMoney = pDossier.getPdCashmoney();
		double extroMoney = pDossier.getPdSubsidymoney();
		//没有补助的直接消费一卡通
		if(extroMoney == 0){
			DecimalFormat df = new DecimalFormat("#.00");
			String afterMoney  = df.format(beforeMoney - spendMoney);
			pDossier.setPdCashmoney(Double.valueOf(afterMoney));
			if(!personService.updateForSet("pd_cashmoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_cashmoney = {0}",beforeMoney).and("pd_accountid = {0}",accountid).and("clientid = {0}",clientid))){
				throw new RuntimeException("余额更新失败");
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
			if(!personService.updateForSet("pd_subsidymoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_accountid = {0}",accountid).and("clientid = {0}",clientid))){
				throw new RuntimeException("余额更新失败");
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
			detail.setSdNewmoney(Double.valueOf(afterMoney));
			detail.setSdOldmoney(extroMoney);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
			if(!personService.updateForSet("pd_subsidymoney = "+0+" ,pd_cashmoney = "+after_moeny,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cashmoney = {0}",beforeMoney).and("pd_accountid = {0}",accountid).and("clientid = {0}",clientid))){
				throw new RuntimeException("余额更新失败");
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
			detail.setSdNewmoney(0.0);
			detail.setSdOldmoney(extroMoney);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
			detail1.setSdNewmoney(after_moeny);
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
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
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
	/**
	 * 退款
	 * @param result
	 * @throws Exception 
	 */
	private void refundMoney(Map<String, String> result) throws Exception {
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("out_trade_no", result.get("out_trade_no"));
		map2.put("service", "unified.trade.query");
		Map<String, String> order_result = wxPay.orderQuery(map2);
		if(order_result.get("status") == null||order_result.get("result_code") == null||!order_result.get("status").equals("0")||!order_result.get("result_code").equals("0")){
			System.out.println("查询失败");
			return;
		}else{
			if(order_result.get("trade_state") == null||!order_result.get("trade_state").equals("SUCCESS")){
				System.out.println("查询失败");
				return;
			}
		}
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("out_trade_no", result.get("out_trade_no"));
		map1.put("service", "unified.trade.refund");
		String out_refund_no = WXPayUtil.createOrderNumber();
		map1.put("out_refund_no",out_refund_no);
		map1.put("total_fee",result.get("total_fee"));
		map1.put("refund_fee",result.get("total_fee"));
		map1.put("op_user_id",mchid);
		Map<String, String> refund_result = wxPay.refund(map1);
		if(refund_result.get("status") == null||refund_result.get("result_code") == null||!refund_result.get("status").equals("0")||!refund_result.get("result_code").equals("0")){
			System.out.println("退款失败");
		}else{
			if(refund_result.get("status") == null||!refund_result.get("status").equals("SUCCESS")){
				System.out.println("退款失败");
			}
		}
	}
	//得到ip地址
	public  String getIp2(HttpServletRequest request) {  
		String ip = request.getHeader("X-Forwarded-For");
		if(ip!=null && !ip.equals("")&& !"unKnown".equalsIgnoreCase(ip)){  
			//多次反向代理后会有多个ip值，第一个ip才是真实ip  
			int index = ip.indexOf(","); 
			if(index != -1){  
				return ip.substring(0,index);  
			}else{  
				return ip;  
			}  
		} 
		ip = request.getHeader("X-Real-IP");
		if(ip!=null && !ip.equals("")&& !"unKnown".equalsIgnoreCase(ip)){  
			return ip;  
		}
		return request.getRemoteAddr();  
	}  
	/**
	 * 手持机扫码扣款消费（一卡通与微信）
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	@RequestMapping(value = "/comsume/nfc/code",method = RequestMethod.POST)
	public ResultData inertWxComsumeRecord(@RequestBody ComsumeCodeParam param,HttpServletRequest request) throws Exception{
		String clientid = param.getClientid();
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		double spendMoney = param.getSpendMoney();
		String computer = param.getComputer();
		String wimdownumber = param.getWindowNumber();
		String ip = getIp2(request);
		if(StringUtils.isEmpty(param.getComputer())||StringUtils.isEmpty(param.getWindowNumber())||spendMoney <= 0||StringUtils.isEmpty(param.getAuthCode())||StringUtils.isEmpty(clientid)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		if(param.getAuthCode().startsWith("cardCode-")){
			return doCardCodeComsume(param);
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("out_trade_no", WXPayUtil.createOrderNumber());
		map.put("body", "手持机微信消费");
		map.put("service", "unified.trade.micropay");
		String money = (int)(spendMoney*100)+"";
		map.put("total_fee", money);
		map.put("auth_code", param.getAuthCode());
		map.put("mch_create_ip", ip);
		//提交支付，判断支付结果
		Map<String, String> result = wxPay.microPay(map);
		if(result == null||result.get("status") == null||result.get("result_code") == null||!result.get("status").equals("0")||!result.get("result_code").equals("0")){
			throw new RuntimeException(result.get("err_msg"));
		}else {
			if(result.get("status").equals("0")&&result.get("result_code").equals("0")){
			}else if(result.get("need_query") != null&&result.get("need_query").equals("N")){
				throw new RuntimeException(result.get("err_msg"));
			}else if(result.get("need_query") == null||result.get("need_query").equals("Y")){
				boolean is_success = false;
				for(int i = 0;i < 4;i++){
					HashMap<String, String> map1 = new HashMap<>();
					map1.put("out_trade_no", result.get("out_trade_no"));
					map1.put("service", "unified.trade.query");
					Map<String, String> order_result = wxPay.orderQuery(map1);
					if(order_result.get("status") != null&&order_result.get("result_code") != null&&order_result.get("status").equals("0")&&order_result.get("result_code").equals("0")){
						if(order_result.get("trade_state") != null&&order_result.get("trade_state").equals("SUCCESS")){
							is_success = true;
							break;
						}
					}
					Thread.sleep(2000);
				}
				if(!is_success){
					HashMap<String, String> map1 = new HashMap<>();
					map1.put("out_trade_no", result.get("out_trade_no"));
					map1.put("service", "unified.micropay.reverse");
					wxPay.reverse(map1);
					throw new RuntimeException(result.get("err_msg"));
				}
			}
		}
		//插入记录
		SpendDetail detail = new SpendDetail();
		detail.setClientid(clientid);
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] datetime = sFormat.format(new Date()).split(" ");
		detail.setSdCldate(datetime[0]);
		detail.setSdCltime(datetime[1]);
		detail.setSdDepartment("");
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
		detail.setSdMeal(meal);
		detail.setSdMoney(spendMoney);
		detail.setSdMoneyaccount("现金");
		detail.setSdNewmoney(0.0);
		detail.setSdOldmoney(0.0);
		detail.setSdOperator("");
		detail.setSdPdaccountid("");
		detail.setSdPdid("");
		detail.setSdPdname("");
		detail.setSdDatatype("手持");
		detail.setSdSpenddate(spendDate);
		detail.setSdSpendtime(spendTime);
		detail.setSdSpendtype("微信消费");
		detail.setSdWindowno(wimdownumber);
		detail.setSdComputer(computer);
		if(!spendService.insertAllColumn(detail)){
			refundMoney(result);
			throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
		}
		ResultData data = new ResultData();
		data.setCode(Constant.CODE_SUCCESS);
		data.setMsg(Constant.MSG_SUCCESS);
		return data;
	}
}
