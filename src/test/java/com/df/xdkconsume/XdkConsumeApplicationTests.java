//package com.df.xdkconsume;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.df.xdkconsume.pay.WXPay;
//import com.google.gson.Gson;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
////@Slf4j
//public class XdkConsumeApplicationTests {
//	//	@Autowired
//	//	DeviceMapper mapper;
//		@Autowired()
//		WXPay wxPay;
//
//	//	
//	//	@Autowired
//	//	CanTaiServiceImpl canTaiService;
//		@Test
//		public void testPay() throws Exception{
//			HashMap<String, String> map1 = new HashMap<>();
//			map1.put("out_trade_no", "32");
//			map1.put("service", "unified.trade.query");
//			Map<String, String> order_result = wxPay.orderQuery(map1);
//			System.out.println(new Gson().toJson(order_result));
////			HashMap<String, String> map = new HashMap<>();
////			map.put("out_trade_no", WXPayUtil.createOrderNumber());
////			map.put("body", "测试");
////			map.put("sign_type", "MD5");
////	//		map.put("is_raw", "1");
////			map.put("notify_url", "www.baidu.com");
////			map.put("service", "pay.weixin.native");
////			map.put("total_fee", "1");
////			map.put("auth_code", "134707120995259529");
////			map.put("mch_create_ip", "127.0.0.1");
////			System.out.println(GsonUtils.getInstance().o2J(wxPay.microPay(map)));
//		}
//    
//	@Test
//	public void testSign() throws Exception{
////		InputStream inputStream = null;
////		byte[] data = null;
////		try {
////			inputStream = new FileInputStream("D:/headimg/028010014010002.jpg");
////			data = new byte[inputStream.available()];
////			inputStream.read(data);
////			inputStream.close();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		// 加密
////		Base64.Encoder encoder = Base64.getEncoder();
////		if(data == null){
////			System.out.println("没找到");
////		}
////		String dataimg = encoder.encodeToString(data);
////		System.out.println(dataimg);
////		String paramString  = null;
////		System.out.println("C114,4|C112,6|C118,6|C105,10|dbf2d,2.00|dbf2d,2.00|".length());
////		paramString = "pass=123456&personId=0000000001&faceId=123456&imgBase64="+dataimg;
////		System.out.println(HttpUtil.post("http://192.168.8.110:8090/face/create",paramString));
//		//		CheckRecodParam param = new CheckRecodParam();
//		//		param.setClientid("02801");
//		//		param.setCardid("CA7C643A");
//		//		param.setCheckmac("考勤机1");
//		//		param.setChecktype("0");
//		//		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		//		String[] datetime = sFormat.format(new Date()).split(" ");
//		//		param.setDate(datetime[0]);
//		//		param.setTime(datetime[1]);
//		//		param.setStyle("1");
//		//		param.setMactype("0");
//		//		param.setTimestamp(System.currentTimeMillis()+"");
//		//		TreeMap<String, String> qweMap = (TreeMap<String, String>) BeanUtil.ClassToMap(param);
//		//		String keyStrin = VerifyUtil.createSign(qweMap, "cinzn2055");
//		//		param.setSign(keyStrin);
//		//		System.out.println(GsonUtils.getInstance().o2J(param));
//	}
//}