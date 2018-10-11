//package com.df.xdkconsume;
//import static org.hamcrest.CoreMatchers.nullValue;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.TreeMap;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.df.xdkconsume.entity.WxpayParam;
//import com.df.xdkconsume.mapper.DeviceMapper;
//import com.df.xdkconsume.pay.WXPay;
//import com.df.xdkconsume.pay.WXPayUtil;
//import com.df.xdkconsume.utils.BeanUtil;
//import com.df.xdkconsume.utils.GsonUtils;
//import com.df.xdkconsume.utils.VerifyUtil;
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class XdkConsumeApplicationTests {
//	@Autowired
//	DeviceMapper mapper;
//	@Autowired()
//	WXPay wxPay;

//	@Test
//	public void testPay() throws Exception{
//		HashMap<String, String> map = new HashMap<>();
//		map.put("out_trade_no", WXPayUtil.createOrderNumber());
//		map.put("body", "测试");
//		map.put("sign_type", "MD5");
////		map.put("is_raw", "1");
//		map.put("notify_url", "www.baidu.com");
//		map.put("service", "pay.weixin.native");
//		map.put("total_fee", "1");
//		map.put("auth_code", "134707120995259529");
//		map.put("mch_create_ip", "127.0.0.1");
//		System.out.println(GsonUtils.getInstance().o2J(wxPay.microPay(map)));
//	}
//	@Test
//	public void testSign() throws Exception{
//		WxpayParam param = new WxpayParam();
//		param.setClientid("02801");
//		param.setAuthCode("134603259991021228");
//		param.setCashList("{Z303,4|}");
//		param.setCardid("14F975C2");
//		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String[] datetime = sFormat.format(new Date()).split(" ");
//		param.setSpendDate(datetime[0]);
//		param.setSpendTime(datetime[1]);
//		param.setTimestamp(System.currentTimeMillis()+"");
//		TreeMap<String, String> qweMap = (TreeMap<String, String>) BeanUtil.ClassToMap(param);
//		String keyStrin = VerifyUtil.createSign(qweMap, "cinzn2055");
//		param.setSign(keyStrin);
//		System.out.println(GsonUtils.getInstance().o2J(param));
//	}
//	@Test
//	public void testshuzhu() throws Exception{
//		String[] arrs = new String[1];
//		arrs[0] = "0.0";
//		for (String string : arrs) {
//			if(Double.valueOf(string) == 0){
//				System.out.println("asdasd");
//				continue;
//			}
//			System.out.println(string);
//		}
//	}
//}