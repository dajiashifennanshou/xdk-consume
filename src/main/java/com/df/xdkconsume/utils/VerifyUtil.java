package com.df.xdkconsume.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.util.StringUtils;

import com.df.xdkconsume.entity.ResultData;


public class VerifyUtil {
	/**
	 * 签名验证
	 * @param data
	 * @return
	 */
	public static ResultData verifySign(Map<String, String> data){
		ResultData result = new ResultData();
		if(data == null){
			result.setCode(210);
			result.setMsg("no parameter");
			return result;
		}
		String timestamp = data.get("timestamp");
		String sign = data.get("sign");
		if(StringUtils.isEmpty(timestamp)||StringUtils.isEmpty(sign)){
			result.setCode(211);
			result.setMsg("no sign or timestamp parameter");
			return result;
		}
		if(System.currentTimeMillis() - Long.valueOf(timestamp) > 1000*60*60){
			result.setCode(212);
			result.setMsg("timestamp timeout");
			return result;
		}
		TreeMap<String, String> parameters = new TreeMap<>();
		parameters.putAll(data);
		String paySign = createSign(parameters,Constant.SIGN_KEY);
		if(!paySign.equals(sign)){
			result.setCode(213);
			result.setMsg("sign invalid");
			return result;
		}
		return null;
	}
	/**
	 * @author Mark
	 * @Description：sign签名
	 * @param characterEncoding 编码格式
	 * @param parameters 请求参数
	 * @return
	 */
	public static String createSign(SortedMap<String,String> parameters,String key){
		StringBuffer sb = new StringBuffer();
		Set<?> es = parameters.entrySet();
		Iterator<?> it = es.iterator();
		while(it.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
		return sign;
	}
}
