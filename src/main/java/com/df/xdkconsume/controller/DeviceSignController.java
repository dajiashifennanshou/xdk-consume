package com.df.xdkconsume.controller;

import com.df.xdkconsume.entity.DeviceParam;
import com.df.xdkconsume.entity.ResultData;
import com.df.xdkconsume.mapper.DeviceMapper;
import com.df.xdkconsume.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author df
* @version 创建时间：2018年9月14日 上午10:09:48
* @Description 手持机注册APP接口
*/
@RestController
@RequestMapping("/device")
public class DeviceSignController {
	@Autowired
	private DeviceMapper deMapper;
	
	/**
	 * 注册设备信息
	 */
	@RequestMapping(path = "/sign",method = RequestMethod.POST)
	public ResultData signDevice(@RequestBody DeviceParam param){
		String device_info = param.getDeviceInfo();
		if(StringUtils.isEmpty(device_info)){
			ResultData data = new ResultData();
			data.setCode(Constant.CODE_PARAM_NULL);
			data.setMsg(Constant.MSG_PARAM_NULL);
			return data;
		}
		String ishave= deMapper.isSignDevice(device_info);
		if(!StringUtils.isEmpty(ishave)){
			ResultData result = new ResultData();
			result.setCode(221);
			result.setMsg("设备已注册");
			return result;
		}
		ResultData data = new ResultData();
		String date_time= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if(deMapper.signDevice(device_info, date_time)){
			data.setCode(Constant.CODE_SUCCESS);
			data.setMsg(Constant.MSG_SUCCESS);
		}else{
			data.setCode(Constant.CODE_SIGN_WRONG);
			data.setMsg(Constant.MSG_SIGN_WRONG);
		}
		return data;
	}
}
