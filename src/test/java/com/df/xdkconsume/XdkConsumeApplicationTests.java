//package com.df.xdkconsume;
//
//import java.security.MessageDigest;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Set;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
//import org.junit.Test;
//
//import com.df.xdkconsume.entity.BaseParam;
//import com.df.xdkconsume.entity.ComsumeParam;
//import com.df.xdkconsume.utils.BeanUtil;
//import com.df.xdkconsume.utils.GsonUtils;
//import com.df.xdkconsume.utils.VerifyUtil;
//
//public class XdkConsumeApplicationTests {
//
//	@Test
//	public void contextLoads() throws Exception {
////		String cardid = "12345678";
////		cardid = cardid.substring(6,8)+cardid.substring(4,6)+cardid.substring(2,4)+cardid.substring(0,2);
////		System.out.println(cardid);
//				BaseParam param = new BaseParam();
//				param.setClientid("02801");
//				param.setCardid("4A4CEE80");
////				param.setComputer("20");
////				param.setSpendDate("2018-07-24");
////				param.setSpendMoney(0.50);
////				param.setSpendTime("15:51:18");
//				param.setTimestamp(System.currentTimeMillis()+"");
////				param.setWindowNumber("4680");
//				SortedMap<String, String> qweMap = BeanUtil.ClassToMap(param);
//				TreeMap<String, String> parameters = new TreeMap<>();
//				parameters.putAll(qweMap);
//				TreeMap<Object, Object> parameters1 = new TreeMap<>();
//				parameters1.putAll(qweMap);
//				String keyString = generateSignature(parameters, "cinzn2055");
//				String keyStrin = VerifyUtil.createSign(parameters1, "cinzn2055");
//				param.setSign(keyString);
//				//64F4720CEAB4370C95C72197C4B050CF
//		//		System.out.println(keyStrin);
//				System.out.println(keyString);
//				System.out.println(GsonUtils.getInstance().o2J(param));
//	}
//
//	/**
//	 * 生成 MD5
//	 *
//	 * @param data 待处理数据
//	 * @return MD5结果
//	 */
//	public static String MD5(String data) throws Exception {
//		java.security.MessageDigest md = MessageDigest.getInstance("MD5");
//		byte[] array = md.digest(data.getBytes("UTF-8"));
//		StringBuilder sb = new StringBuilder();
//		for (byte item : array) {
//			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
//		}
//		return sb.toString().toUpperCase();
//	}
//	/**
//	 * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
//	 *
//	 * @param data 待签名数据
//	 * @param key API密钥
//	 * @param signType 签名方式
//	 * @return 签名
//	 */
//	public static String generateSignature(TreeMap<String, String> data, String key) throws Exception {
//		Set<String> keySet = data.keySet();
//		String[] keyArray = keySet.toArray(new String[keySet.size()]);
//		Arrays.sort(keyArray);
//		StringBuilder sb = new StringBuilder();
//		for (String k : keyArray) {
//			if (k.equals("sign")) {
//				continue;
//			}
//			if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
//				sb.append(k).append("=").append(data.get(k).trim()).append("&");
//		}
//		sb.append("key=").append(key);
//		return MD5(sb.toString()).toUpperCase();
//	}
//}
