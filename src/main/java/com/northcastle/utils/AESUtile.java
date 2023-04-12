package com.northcastle.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-23  09:35
 * @Description: AES 加解密
 */
public class AESUtile {


    /**
     * 加密算法 AES
     * 加密模式 CBC
     * 填充模式 PKCS5Padding
     *
     * 编码格式 UTF-8
     */

    private final static String MODE = "AES/CBC/PKCS5Padding";

    private final static String AES = "AES";
    private final static String ENCODING = "UTF-8";


    /**
     * 密钥长度 ： 必须是 16位
     */
    private static final String KEY = "MH*DiqdcR36lRFSv";
    /**
     * 向量长度 ： 必须是 16位
     */
    private static final String IV = "nY5*qSRdca2GjuDA";

    /**
     * 加密
     * @param data 要加密的数据
     * @param key 加密密钥
     * @param iv 偏移量
     * @return
     * @throws Exception
     */
    public static String encrypt(String data,String key,String iv) throws Exception{
        byte[] dataByte = data.getBytes(ENCODING);
        byte[] keyByte = key.getBytes();
        //初始化一个密钥对象
        SecretKeySpec keySpec = new SecretKeySpec(keyByte ,AES);
        //初始化一个初始向量,不传入的话，则默认用全0的初始向量
        byte[] ivParam = iv.getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(ivParam);
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec,ivSpec);
        byte[] encryptedBytes = cipher.doFinal(dataByte);
        String encodedString = Base64.encodeBase64String(encryptedBytes);
        return encodedString;
    }

    /**
     * 解密
     * @param data 密文
     * @param key 密钥
     * @param iv 偏移向量
     * @return
     */
    public static String decrypt(String data,String key,String iv) throws Exception, NoSuchAlgorithmException {
        byte[] dataByte = Base64.decodeBase64(data);
        byte[] keyByte = key.getBytes();
        //初始化一个密钥对象
        SecretKeySpec keySpec = new SecretKeySpec(keyByte ,AES);
        //初始化一个初始向量,不传入的话，则默认用全0的初始向量
        byte[] ivParam = iv.getBytes();
        IvParameterSpec ivSpec = new IvParameterSpec(ivParam);
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE, keySpec,ivSpec);
        byte[] result  = cipher.doFinal(dataByte);
        return new String(result,ENCODING);
    }


    /**
     * 使用内部的静态变量 直接进行加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception{
       return encrypt(data,KEY,IV);
    }

    public static String decrypt(String data) throws Exception{
        return decrypt(data,KEY,IV);
    }








    public static void main(String[] args) throws Exception {
        String data = "sf;asdjowingewgkasdfjdjfoignadkajdoj大家好大鸡味噶是德国人头i家哦i为i及的疯狂攻击二哈几哈快递放假哈就收到货发课时费是短发iu噶看没看了东方公i日啊就卡号大法iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机iu为合法大法文件共i公司的麦克风几位哦i哈工大空间收到佛为i饿会给我区域i给个机会，弄i耳机";


        String encrypt = encrypt(data);
        System.out.println("encrypt = " + encrypt);

        String bb = "JZxZufgOoG71iOedcbRZfEb06k7Dx1E6WMZAYWgFuHjyrgIsdr/IQNWiQXXHLqw7sQGoI0x3G0Qi0Td/Z5Uh3uDNSI8USstFsoAIwC9Eg0o=";
        String decrypt = decrypt(bb);
        System.out.println("data = " + data);
        System.out.println("decr = " + decrypt);
    }




}
