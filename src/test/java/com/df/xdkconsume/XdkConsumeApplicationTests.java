//package com.df.xdkconsume;
//
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.df.xdkconsume.entity.PersonDossier;
//import com.df.xdkconsume.service.impl.PersonDossierServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class XdkConsumeApplicationTests {
////	@Autowired
////	DeviceMapper mapper;
////	@Autowired()
////	WXPay wxPay;
//	@Autowired()
//    PersonDossierServiceImpl service;
//
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
//	    PersonDossier dossier = new PersonDossier();
//	    dossier.setPdName("修改的");
//        EntityWrapper<PersonDossier> wrapper = new EntityWrapper<>();
//        wrapper.where("pd_accountid = {0}","0000010000").and("clientid = {0}","81611");
//	    System.out.println(service.update(dossier,wrapper));
////		AccountidParam param = new AccountidParam();
////		param.setClientid("02701");
////		param.setAccountid("0000000002");
////		param.setCardid("9B32C572");
////		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		String[] datetime = sFormat.format(new Date()).split(" ");
////		param.setTimestamp(System.currentTimeMillis()+"");
////		TreeMap<String, String> qweMap = (TreeMap<String, String>) BeanUtil.ClassToMap(param);
////		String keyStrin = VerifyUtil.createSign(qweMap, "cinzn2055");
////		param.setSign(keyStrin);
////		System.out.println(GsonUtils.getInstance().o2J(param));
//	}
//}