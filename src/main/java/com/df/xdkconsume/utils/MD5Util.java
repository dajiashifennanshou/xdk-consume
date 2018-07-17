package com.df.xdkconsume.utils;

import java.security.MessageDigest;

public class MD5Util {  
    private static final String encryModel="MD5";  
    /** 
     * 32λmd5. 
     * 32位小写md5加密 
     * @param str 
     * @return 
     */  
    public  static String md5(String str) {  
        return encrypt(encryModel, str);  
    }  
    public static String encrypt(String algorithm, String str) {  
        try {  
            MessageDigest md = MessageDigest.getInstance(algorithm);  
            md.update(str.getBytes());  
            StringBuffer sb = new StringBuffer();  
            byte[] bytes = md.digest();  
            for (int i = 0; i < bytes.length; i++) {  
                int b = bytes[i] & 0xFF;  
                if (b < 0x10) {  
                    sb.append('0');  
                }  
                sb.append(Integer.toHexString(b));  
            }  
            return sb.toString();  
        } catch (Exception e) {  
            return "";  
        }  
    }  
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}  