package com.df.xdkconsume;

import org.junit.Test;
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class XdkConsumeApplicationTests {

//	@Autowired()
//	WXPay wxPay;

	@Test
	public void testPay() throws Exception{
//		HashMap<String, String> map = new HashMap<>();
//		map.put("out_trade_no", WXPayUtil.createOrderNumber());
//		map.put("body", "测试");
//		map.put("service", "unified.trade.micropay");
//		map.put("total_fee", "1");
//		map.put("auth_code", "134683402862784266");
//		map.put("mch_create_ip", "127.0.0.1");
//		System.out.println(GsonUtils.getInstance().o2J(wxPay.microPay(map)));
	}
//	@Test
//	public void testSign() throws Exception{
//		WxpayParam param = new WxpayParam();
//		param.setClientid("02801");
//		param.setAuthCode("134603259991021228");
//		param.setCashList("1,2,4");
//		param.setCardid("1");
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
}