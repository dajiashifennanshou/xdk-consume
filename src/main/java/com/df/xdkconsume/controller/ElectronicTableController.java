package com.df.xdkconsume.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.df.xdkconsume.entity.*;
import com.df.xdkconsume.pay.WXPay;
import com.df.xdkconsume.pay.WXPayUtil;
import com.df.xdkconsume.service.impl.CtCanteenServiceImpl;
import com.df.xdkconsume.service.impl.FoodMenuServiceImpl;
import com.df.xdkconsume.service.impl.PersonDossierServiceImpl;
import com.df.xdkconsume.service.impl.SpendDetailServiceImpl;
import com.df.xdkconsume.utils.Constant;
import com.df.xdkconsume.utils.VerifyUtil;
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
 *
 * @author df
 * @since 2018-07-12
 * 电子餐台接口
 */
@RestController
@RequestMapping("/user")
public class ElectronicTableController {
	@Autowired
	private PersonDossierServiceImpl personService;

	@Autowired
	private SpendDetailServiceImpl spendService;

	@Autowired
	private CtCanteenServiceImpl canteenService;

	@Autowired
	private FoodMenuServiceImpl foodMenuService;

	@Autowired()
	WXPay wxPay;

	@Value("${pay-config.mch_id}")
	String mchid;
	/**
	 * 餐台消费一卡通
	 * @param param
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(path = "/comsume",method = RequestMethod.POST)
	public synchronized ResultData userConsume(@RequestBody WxpayParam param) {
		String clientid = param.getClientid();
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		String cashlistString = param.getCashList();
		String cardid = param.getCardid();
		if(StringUtils.isEmpty(cardid)||cashlistString == null||StringUtils.isEmpty(clientid)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)){
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
		cashlistString = cashlistString.replace("{", "").replace("}", "");
		String[] cashidAndMoney = cashlistString.split("\\|");
		String[] cashlist = new String[cashidAndMoney.length];
		for (int i= 0;i<cashidAndMoney.length;i++) {
			if(Double.valueOf(cashidAndMoney[i].split(",")[1]) == 0){
				continue;
			}
			cashlist[i] = cashidAndMoney[i].split(",")[0]; 
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
		if(pDossier.getPdLoss() == 1){
			data.setCode(Constant.CODE_PERSON_LOST);
			data.setMsg(Constant.MSG_PERSON_LOST);
			return data;
		}
		double totalfee = 0;
		double one = 0;
		double two = 0;
		HashSet<String> typeset = new HashSet<>();//商家类型
		ArrayList<FoodMenu> menus = new ArrayList<>();
		for (String id : cashlist) {
			if(id == null){
				continue;
			}
			EntityWrapper<FoodMenu> wrapper =  new EntityWrapper<>();
			wrapper.where("clientid = {0} and fm_plateid = {1}", clientid,id);
			FoodMenu fMenu = foodMenuService.selectOne(wrapper);
			//找不到对应餐盘
			if(fMenu == null){
				data.setCode(Constant.CODE_PARAM_NULL);
				data.setMsg("找不到餐盘");
				return data;
			}
			typeset.add(fMenu.getFmCanteen());
			menus.add(fMenu);
			totalfee += fMenu.getFmUnitprice();
		}
		if(menus.size() == 0){
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg("没有餐盘");
			return data;
		}
		String firstName = null;
		String secondName = null;
		CtCanteen canteen1 = null;
		CtCanteen canteen2 = null;
		Iterator<String> it = typeset.iterator();
		while (it.hasNext()) {
			if(firstName != null){
				secondName = it.next();
				EntityWrapper<CtCanteen> cWrapper =  new EntityWrapper<>();
				cWrapper.where("clientid = {0} and can_id = {1}", clientid,secondName);
				canteen2 = canteenService.selectOne(cWrapper);
			}else{
				firstName = it.next();
				EntityWrapper<CtCanteen> cWrapper =  new EntityWrapper<>();
				cWrapper.where("clientid = {0} and can_id = {1}", clientid,firstName);
				canteen1 = canteenService.selectOne(cWrapper);
			}
		}
		//计算两个食堂分别的价格，插入记录
		for (FoodMenu foodMenu : menus) {
			if(foodMenu.getFmCanteen().equals(firstName)){
				one += foodMenu.getFmUnitprice();
			}else{
				two += foodMenu.getFmUnitprice();
			}
		}
		double beforeMoney = pDossier.getPdCashmoney();
		double extroMoney = pDossier.getPdSubsidymoney();
		if(beforeMoney < 0 ||extroMoney < 0){
			data.setCode(Constant.CODE_PERSON_MOENY_FU);
			data.setMsg(Constant.MSG_PERSON_MOENY_FU);
			return data;
		}
		double allmoney = extroMoney + beforeMoney;
		if(allmoney < totalfee){
			data.setCode(Constant.CODE_PERSON_NO_MOENY);
			data.setMsg(Constant.MSG_PERSON_NO_MONEY);
			return data;
		}
		if(one > 0){
			ResultData result = countDowmMoeny(spendDate,spendTime,one,clientid,cardid,canteen1.getCanWindowno(),canteen1.getCanComputer());
			if(result.getCode() != Constant.CODE_SUCCESS){
				return result;
			}
		}
		if(two > 0){
			ResultData result = countDowmMoeny(spendDate,spendTime,two,clientid,cardid,canteen2.getCanWindowno(),canteen2.getCanComputer());
			if(result.getCode() != Constant.CODE_SUCCESS){
				return result;
			}
		}
		EntityWrapper<PersonDossier> qurymy = new EntityWrapper<>();
		qurymy.where("pd_cardid = {0}",cardid).and("clientid = {0}",clientid);
		PersonDossier qurymyuser =  (PersonDossier) personService.selectOne(pWrapper);
		UserMoney userMoney = new UserMoney();
		userMoney.setCashmoney(qurymyuser.getPdCashmoney());
		userMoney.setSubsidymoney(qurymyuser.getPdSubsidymoney());
		data.setCode(Constant.CODE_SUCCESS);
		data.setMsg(Constant.MSG_SUCCESS);
		data.setData(userMoney);
		return data;
	}

	/**
	 * 插入记录操作余额
	 * @param spendDate
	 * @param spendTime
	 * @param spendMoney
	 * @param clientid
	 * @param cardid
	 * @param windowNumber
	 * @param computer
	 * @return
	 */
	private ResultData countDowmMoeny(String spendDate,String spendTime,Double spendMoney,String clientid, String cardid, String windowNumber, String computer) {
		EntityWrapper<PersonDossier> pWrapper = new EntityWrapper<>();
		pWrapper.where("pd_cardid = {0}",cardid).and("clientid = {0}",clientid);
		PersonDossier pDossier =  (PersonDossier) personService.selectOne(pWrapper);
		ResultData data = new ResultData();
		double beforeMoney = pDossier.getPdCashmoney();
		double extroMoney = pDossier.getPdSubsidymoney();
		//没有补助的直接消费一卡通
		if(extroMoney == 0){
			DecimalFormat df = new DecimalFormat("#.00");
			String afterMoney  = df.format(beforeMoney - spendMoney);
			pDossier.setPdCashmoney(Double.valueOf(afterMoney));
			if(!personService.updateForSet("pd_cashmoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_cashmoney = {0}",beforeMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
			if(!personService.updateForSet("pd_subsidymoney = "+afterMoney,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
			if(!personService.updateForSet("pd_subsidymoney = "+0+" ,pd_cashmoney = "+after_moeny,new EntityWrapper<PersonDossier>().where("pd_subsidymoney = {0}",extroMoney).and("pd_cashmoney = {0}",beforeMoney).and("pd_cardid = {0}",cardid).and("clientid = {0}",clientid))){
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
	 * 根据卡号拿到用户信息
	 * @param param
	 * @return
	 * @throws IOException
	 */
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
			data.setMsg("8位卡号");
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
			Map<String,String> statusmap = personService.getUserStatus(pDossier.getClientid(),pDossier.getPdStatus());
			if(!StringUtils.isEmpty(statusmap.get("ps_name"))){
				pDossier.setPdStatus(statusmap.get("ps_name"));
			}else{
				pDossier.setPdStatus("未知人员身份");
			}
//			statusmap.get("ps_student");
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
			data.setData(pDossier);
		}
		return data;
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
	 * 餐台微信扣款消费
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	@RequestMapping(value = "/comsume/wx",method = RequestMethod.POST)
	public ResultData inertWxComsumeRecord(@RequestBody WxpayParam param,HttpServletRequest request) throws Exception{
		String clientid = param.getClientid();
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		String cashlistString = param.getCashList();
		String ip = getIp2(request);
		if(StringUtils.isEmpty(cashlistString)||StringUtils.isEmpty(param.getAuthCode())||StringUtils.isEmpty(clientid)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		if(param.getAuthCode().startsWith("cardCode-")){
			return doCardCodeComsume(param);
		}
		cashlistString = cashlistString.replace("{", "").replace("}", "");
		String[] cashidAndMoney = cashlistString.split("\\|");
		String[] cashlist = new String[cashidAndMoney.length];
		for (int i= 0;i<cashidAndMoney.length;i++) {
			if(Double.valueOf(cashidAndMoney[i].split(",")[1]) == 0){
				continue;
			}
			cashlist[i] =cashidAndMoney[i].split(",")[0]; 
		}
		double totalfee = 0;
		double one = 0;
		double two = 0;
		HashSet<String> typeset = new HashSet<>();//商家类型
		ArrayList<FoodMenu> menus = new ArrayList<>();
		for (String id : cashlist) {
			if(id == null){
				continue;
			}
			EntityWrapper<FoodMenu> wrapper =  new EntityWrapper<>();
			wrapper.where("clientid = {0} and fm_plateid = {1}", clientid,id);
			FoodMenu fMenu = foodMenuService.selectOne(wrapper);
			if(fMenu == null){
				ResultData data = new ResultData();
				data.setCode(Constant.CODE_PARAM_NULL);
				data.setMsg("找不到餐盘");
				return data;
			}
			typeset.add(fMenu.getFmCanteen());
			menus.add(fMenu);
			totalfee += fMenu.getFmUnitprice();
		}
		//找不到对应餐盘
		if(menus.size() == 0){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg("没有餐盘");
			return data;
		}
		String firstName = null;
		String secondName = null;
		CtCanteen canteen1 = null;
		CtCanteen canteen2 = null;
		Iterator<String> it = typeset.iterator();
		while (it.hasNext()) {
			if(firstName != null){
				secondName = it.next();
				EntityWrapper<CtCanteen> cWrapper =  new EntityWrapper<>();
				cWrapper.where("clientid = {0} and can_id = {1}", clientid,secondName);
				canteen2 = canteenService.selectOne(cWrapper);
			}else{
				firstName = it.next();
				EntityWrapper<CtCanteen> cWrapper =  new EntityWrapper<>();
				cWrapper.where("clientid = {0} and can_id = {1}", clientid,firstName);
				canteen1 = canteenService.selectOne(cWrapper);
			}
		}
		//计算两个食堂分别的价格，插入记录
		for (FoodMenu foodMenu : menus) {
			if(foodMenu.getFmCanteen().equals(firstName)){
				one += foodMenu.getFmUnitprice();
			}else{
				two += foodMenu.getFmUnitprice();
			}
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("out_trade_no", WXPayUtil.createOrderNumber());
		map.put("body", "电子餐台消费");
		map.put("service", "unified.trade.micropay");
		String money = (int)(totalfee*100)+"";
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
		//生成记录
		if(one > 0){
			//第一商家
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
			detail.setSdMoney(one);
			detail.setSdMoneyaccount("现金");
			detail.setSdNewmoney(0.0);
			detail.setSdOldmoney(0.0);
			detail.setSdOperator("");
			detail.setSdPdaccountid("");
			detail.setSdPdid("");
			detail.setSdPdname("");
			detail.setSdDatatype("餐台");
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("微信消费");
			detail.setSdWindowno(canteen1.getCanWindowno());
			detail.setSdComputer(canteen1.getCanComputer());
			if(!spendService.insertAllColumn(detail)){
				refundMoney(result);
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
			}
		}
		if (two > 0) {
			//第二商家
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
			detail.setSdMoney(two);
			detail.setSdMoneyaccount("现金");
			detail.setSdNewmoney(0.0);
			detail.setSdOldmoney(0.0);
			detail.setSdOperator("");
			detail.setSdPdaccountid("");
			detail.setSdPdid("");
			detail.setSdPdname("");
			detail.setSdDatatype("餐台");
			detail.setSdSpenddate(spendDate);
			detail.setSdSpendtime(spendTime);
			detail.setSdSpendtype("微信消费");
			detail.setSdWindowno(canteen2.getCanWindowno());
			detail.setSdComputer(canteen2.getCanComputer());
			if(!spendService.insertAllColumn(detail)){
				refundMoney(result);
				throw new RuntimeException(Constant.MSG_COMSUME_INSERT_WRONG);
			}
		}
		ResultData data = new ResultData();
		data.setCode(Constant.CODE_SUCCESS);
		data.setMsg(Constant.MSG_SUCCESS);
		return data;
	}
	/**
	 * 餐台扫描二维码扣款一卡通
	 * @param param
	 * @return
	 */
	public ResultData doCardCodeComsume(WxpayParam param) {
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		String cashlistString = param.getCashList();
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
		if(StringUtils.isEmpty(accountid)||cashlistString == null||StringUtils.isEmpty(clientid)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		cashlistString = cashlistString.replace("{", "").replace("}", "");
		String[] cashidAndMoney = cashlistString.split("\\|");
		String[] cashlist = new String[cashidAndMoney.length];
		for (int i= 0;i<cashidAndMoney.length;i++) {
			if(Double.valueOf(cashidAndMoney[i].split(",")[1]) == 0){
				continue;
			}
			cashlist[i] =cashidAndMoney[i].split(",")[0];
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
		double totalfee = 0;
		double one = 0;
		double two = 0;
		HashSet<String> typeset = new HashSet<>();//商家类型
		ArrayList<FoodMenu> menus = new ArrayList<>();
		for (String id : cashlist) {
			if(id == null){
				continue;
			}
			EntityWrapper<FoodMenu> wrapper =  new EntityWrapper<>();
			wrapper.where("clientid = {0} and fm_plateid = {1}", clientid,id);
			FoodMenu fMenu = foodMenuService.selectOne(wrapper);
			if(fMenu == null){
				data.setCode(Constant.CODE_PARAM_NULL);
				data.setMsg("找不到餐盘");
				return data;
			}
			typeset.add(fMenu.getFmCanteen());
			menus.add(fMenu);
			totalfee += fMenu.getFmUnitprice();
		}
		//找不到对应餐盘
		if(menus.size() == 0){
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg("没有餐盘");
			return data;
		}
		String firstName = null;
		String secondName = null;
		CtCanteen canteen1 = null;
		CtCanteen canteen2 = null;
		Iterator<String> it = typeset.iterator();
		while (it.hasNext()) {
			if(firstName != null){
				secondName = it.next();
				EntityWrapper<CtCanteen> cWrapper =  new EntityWrapper<>();
				cWrapper.where("clientid = {0} and can_id = {1}", clientid,secondName);
				canteen2 = canteenService.selectOne(cWrapper);
			}else{
				firstName = it.next();
				EntityWrapper<CtCanteen> cWrapper =  new EntityWrapper<>();
				cWrapper.where("clientid = {0} and can_id = {1}", clientid,firstName);
				canteen1 = canteenService.selectOne(cWrapper);
			}
		}
		//计算两个食堂分别的价格，插入记录
		for (FoodMenu foodMenu : menus) {
			if(foodMenu.getFmCanteen().equals(firstName)){
				one += foodMenu.getFmUnitprice();
			}else{
				two += foodMenu.getFmUnitprice();
			}
		}
		double beforeMoney = pDossier.getPdCashmoney();
		double extroMoney = pDossier.getPdSubsidymoney();
		if(beforeMoney < 0 ||extroMoney < 0){
			data.setCode(Constant.CODE_PERSON_MOENY_FU);
			data.setMsg(Constant.MSG_PERSON_MOENY_FU);
			return data;
		}
		double allmoney = extroMoney + beforeMoney;
		if(allmoney < totalfee){
			data.setCode(Constant.CODE_PERSON_NO_MOENY);
			data.setMsg(Constant.MSG_PERSON_NO_MONEY);
			return data;
		}
		if(one > 0){
			ResultData result = countDowmMoenyByAccountid(spendDate,spendTime,one,clientid,accountid,canteen1.getCanWindowno(),canteen1.getCanComputer());
			if(result.getCode() != Constant.CODE_SUCCESS){
				return result;
			}
		}
		if(two > 0){
			ResultData result = countDowmMoenyByAccountid(spendDate,spendTime,two,clientid,accountid,canteen2.getCanWindowno(),canteen2.getCanComputer());
			if(result.getCode() != Constant.CODE_SUCCESS){
				return result;
			}
		}
		EntityWrapper<PersonDossier> qurymy = new EntityWrapper<>();
		qurymy.where("pd_accountid = {0}",accountid).and("clientid = {0}",clientid);
		PersonDossier qurymyuser =  (PersonDossier) personService.selectOne(pWrapper);
		UserMoney userMoney = new UserMoney();
		userMoney.setCashmoney(qurymyuser.getPdCashmoney());
		userMoney.setSubsidymoney(qurymyuser.getPdSubsidymoney());
		data.setCode(Constant.CODE_SUCCESS);
		data.setMsg(Constant.MSG_SUCCESS);
		data.setData(userMoney);
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
}

