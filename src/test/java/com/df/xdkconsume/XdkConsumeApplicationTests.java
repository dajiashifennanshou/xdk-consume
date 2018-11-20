//package com.df.xdkconsume;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.df.xdkconsume.entity.CanTai;
//import com.df.xdkconsume.entity.SpendDetail;
//import com.df.xdkconsume.pay.WXPayUtil;
//import com.df.xdkconsume.service.impl.CanTaiServiceImpl;
//import com.df.xdkconsume.service.impl.SpendDetailServiceImpl;
//import com.df.xdkconsume.utils.Constant;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class XdkConsumeApplicationTests {
////	@Autowired
////	DeviceMapper mapper;
////	@Autowired()
////	WXPay wxPay;
//	
//	@Autowired
//	private SpendDetailServiceImpl spendService;
////	@Test
////	public void testPay() throws Exception{
////		HashMap<String, String> map = new HashMap<>();
////		map.put("out_trade_no", WXPayUtil.createOrderNumber());
////		map.put("body", "测试");
////		map.put("sign_type", "MD5");
//////		map.put("is_raw", "1");
////		map.put("notify_url", "www.baidu.com");
////		map.put("service", "pay.weixin.native");
////		map.put("total_fee", "1");
////		map.put("auth_code", "134707120995259529");
////		map.put("mch_create_ip", "127.0.0.1");
////		System.out.println(GsonUtils.getInstance().o2J(wxPay.microPay(map)));
////	}
//	@Test
//	public void testSign() throws Exception{
////		CheckRecodParam param = new CheckRecodParam();
////		param.setClientid("02801");
////		param.setCardid("1A41633A");
////		param.setCheckmac("0");
////		param.setChecktype("0");
////		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		String[] datetime = sFormat.format(new Date()).split(" ");
////		param.setDate(datetime[0]);
////		param.setTime(datetime[1]);
////		param.setStyle("0");
////		param.setMactype("0");
////		param.setTimestamp(System.currentTimeMillis()+"");
////		TreeMap<String, String> qweMap = (TreeMap<String, String>) BeanUtil.ClassToMap(param);
////		String keyStrin = VerifyUtil.createSign(qweMap, "cinzn2055");
////		param.setSign(keyStrin);
////		System.out.println(GsonUtils.getInstance().o2J(param));
//	}
//}