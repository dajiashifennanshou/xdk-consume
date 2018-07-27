package com.df.xdkconsume.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.df.xdkconsume.entity.ComsumeParam;
import com.df.xdkconsume.entity.ResultData;
import com.df.xdkconsume.entity.SpendDetail;
import com.df.xdkconsume.service.SpendDetailService;
import com.df.xdkconsume.utils.Constant;

/**
 * <p>
 *  前端控制器 消费表
 * </p>
 *
 * @author df
 * @since 2018-07-12
 */
@RestController
@RequestMapping("/spend")
public class SpendDetailController {
	@Autowired
	private SpendDetailService spendService;

	/**
	 * 微信消费后插入消费明细
	 * @param clientid
	 * @param cardid
	 * @param spendMoney
	 * @param computer
	 * @param spendDate
	 * @param spendTime
	 * @param windowNumber
	 * @return
	 */
	@RequestMapping(value = "/insert",method = RequestMethod.POST)
	public ResultData inertComsumeRecord(@RequestBody ComsumeParam param){
		String clientid = param.getClientid();
		String computer = param.getComputer();
		String spendDate = param.getSpendDate();
		String spendTime = param.getSpendTime();
		String windowNumber = param.getWindowNumber();
		if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(clientid)||StringUtils.isEmpty(computer)||StringUtils.isEmpty(spendDate)||StringUtils.isEmpty(spendTime)||StringUtils.isEmpty(windowNumber)){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		double spendMoney = param.getSpendMoney();
		SpendDetail detail = new SpendDetail();
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] datetime = sFormat.format(new Date()).split(" ");
		detail.setClientid(clientid);
		detail.setSdCldate(datetime[0]);
		detail.setSdCltime(datetime[1]);
		detail.setSdComputer(computer);
		detail.setSdDatatype("在线");
		detail.setSdDepartment("");
		String meal = "早";
		if(spendDate.length() >= 2){
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
		detail.setSdMoneyaccount("微信消费");
		detail.setSdNewmoney(0.0);
		detail.setSdOldmoney(0.0);
		detail.setSdOperator("");
		detail.setSdPdaccountid("");
		detail.setSdPdid("");
		detail.setSdPdname("");
		detail.setSdSpenddate(spendDate);
		detail.setSdSpendtime(spendTime);
		detail.setSdDatatype("餐台");
		detail.setSdSpendtype("消费");
		detail.setSdWindowno(windowNumber);
		detail.setSdComputer(computer);
		ResultData data = new ResultData();
		if(spendService.insertAllColumn(detail)){
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
		}else {
			data.setCode(Constant.CODE_COMSUME_INSERT_WRONG);
			data.setMsg(Constant.MSG_COMSUME_INSERT_WRONG);
		}
		return data;
	}
}
