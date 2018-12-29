package com.lazy.tcc.common.utils;

import java.security.MessageDigest;
import java.security.SignatureException;

/**
 * @author laizhiyuan
 * @date 2017/12/26.
 */
public abstract class MD5Utils {

    /**
     * 公盐
     */
    private static final String PUBLIC_SALT = "lazy_tcc" ;
    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * MD5 签名
     * @param str 需要签名字符串
     * @param salt 盐
     * @return
     * @throws SignatureException
     */
    public static String md5Signature(String str, String salt) throws SignatureException {
        if (salt == null){
            salt = "lazy_tcc_sign";
        }
        return encodeByMD5(PUBLIC_SALT + str + salt);
    }

    /**
     * md5 加密算法
     * @param  clientString 字符串
     * @return
     */
    private static String encodeByMD5(String clientString) throws SignatureException {
        if (clientString != null){
            try{
                //创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(clientString.getBytes());
                //将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch(Exception ex){
                throw new SignatureException("MD5错误!", ex);
            }
        }
        return null;
    }

    /**
     * 转换字节数组为十六进制字符串
     * @param b    字节数组
     * @return    十六进制字符串
     * @throws Exception
     */
    private static String byteArrayToHexString(byte[] b) throws Exception {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     * @param b
     * @return
     * @throws Exception
     */
    private static String byteToHexString(byte b) throws Exception {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        try {
            System.out.println(md5Signature("7e165ba51703472fa0dc03e5dde76d41", "TCL@O2O#L2017Z09Y26ARGF"));
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }
}
