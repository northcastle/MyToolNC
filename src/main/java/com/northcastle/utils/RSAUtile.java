package com.northcastle.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-10  14:28
 * @Description: 进行非对称加密的RSA工具方法
 */
public class RSAUtile {

     //private static String publicKey =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHPaAXy9211z9q8FCcvCkyjeRih2ZRWfVuolq5nlIwmL/MXrjEm+gqf9nzNsMortMah0ktvt3JRoruXPEkxRrqj3DZXmMfaWoNqB8nwbca3Nv8wKii0BkKqZ9CIR1oO7SxDOp71jWBNLIPmd1vz5rmbF9nGTBktKTGJwTV9ffZswIDAQAB";
     //private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIc9oBfL3bXXP2rwUJy8KTKN5GKHZlFZ9W6iWrmeUjCYv8xeuMSb6Cp/2fM2wyiu0xqHSS2+3clGiu5c8STFGuqPcNleYx9pag2oHyfBtxrc2/zAqKLQGQqpn0IhHWg7tLEM6nvWNYE0sg+Z3W/PmuZsX2cZMGS0pMYnBNX199mzAgMBAAECgYALypG0wW+ZNSUwW7eLktQ8tiZdeNSw7xj5BmrGmEkuN4MxfGi7q6Q4YGFOVU36tBNv0q/Mr0GDFQVz9uKHhC/udugzq1CCCYEqozm9Ysrc26F+hUM0luP4uXsOmcUVvgkdS9oU7EC7RDU29O7S34BlA0cMbZjRWXEuBMeZGiwVgQJBAM5pOdRRQbddxmJvalYb/OyWFjJT3P2FSD7EZStNwjJMFptwzEcyAQ3K39Gs2CBJTdHrZ2OdEe6koghok6JnA2ECQQCnu0EeLZU0dUnaabs++MTwb70247LSdmLsbsvl9xiuJyHhxxHACvWU04LWty/ohOqbKs4hC4e30cAIfsjVQomTAkEAwhMGIK9WDsbJJPyFCvm90eU8cY2Ql36d8wZN6W1vTkNkbjqcl8RYYVODJm5Rt+m0lc+0omCo361D/Iv3ZBdfgQJAL7/mJNIzNLDjKnOJI78sSYp14kdmI/Yh6U8NatUySHnJcn36dqJoEUs5ZyjVUattImlILy4aH+Fter2lLNj5UwJAXURyLk2gAatB0bSdONDf58BcvsB47GaM4IP5FyQQjUmAbJWluh5dBYtVVe0pywOKFaB5qKUEFiRjGFLDIZhjuw==";


    private static String publicKey =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDCOwusNqay66xZLHIq/5aLcj72N15nAtCU3jUzjUQ6cIlgFPNwCvPjYq8Sr7ZBV+BswRMwqsvug1DbzBL/oaNmFWMPWe+pHMzV9nA8EgLgt7jF+TqfPxqLG66s+OkTdiompzJTGJL0bXitWDibPc1cKL/uqGKKKgtbZ/H2op0mwIDAQAB";
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIMI7C6w2prLrrFkscir/lotyPvY3XmcC0JTeNTONRDpwiWAU83AK8+NirxKvtkFX4GzBEzCqy+6DUNvMEv+ho2YVYw9Z76kczNX2cDwSAuC3uMX5Op8/Gosbrqz46RN2KianMlMYkvRteK1YOJs9zVwov+6oYooqC1tn8fainSbAgMBAAECgYAFy1U2U1e3VJ5q9bru1rwunKuwh0xz3jSZQDutnVi3YL7XDa+m/4JwcR/bZ6q8ygIJKDNeeZU8TQvdGYOXOLAWbp0bOKRCEjDWz+nmuYW2jSDmsXx+yiy67BmhEhPxTZF3M9Y7lenua8txgGLz6MS6NREBSRALYZTvpfIPC59NQQJBALkh+sLBFDsjaHUBzU1R+ZznUFk2cudbK0s283ZRd/DB1MA+FX7t8upeKGMf8rpY9OrC6/vf4ZCPu0RfIs3asZECQQC1MaOy4RTn5/8db6hsrwpnVytbSTVzC1RcT4aalqUTK/XWAMNWPhQzrXoG81m5+3MR3s8wl+ylfqGbw0uuT+1rAkAXV4BtY7iwSCOYdRS0hfxp2wRXazULlIQd5JzHszfjJB0169379fZltF91OAb7ldGzc8YbKLwg5O5gjXou6D7xAkAUBQUKOZY+vUJRtUmNoJVsr5PpxR/zsxfCD0NXBzf780p5N6JdgARpZUxrQi3yv5aKLe0EeHS8IE6o0B4nh0s7AkEAiZDMI8t5ntkIz6I23KT3CdlsNzCJ8001DaAfSagGii+QNdL6nYe/jF6EsH8Q1qH0Xoex2yfMGMEOW6mk82dvog==";


    /**
     * 初始化密钥对
     * @throws NoSuchAlgorithmException
     */
    private void initKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024,new SecureRandom("1*sdfD".getBytes()));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        System.out.println("public key is : " + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
        System.out.println("private key is : " + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));

    }

    /**
     * 字符串转公钥
     * @param key
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 字符串转私钥
     * @param key
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */
    public static String  privateKeyEncrypt(String data,String privateKey) throws Exception {

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(getPrivateKey(privateKey).getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKeyUse = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKeyUse);

        // 执行漂亮的分段加密
        byte[] inputArray = data.getBytes();
        int inputLength = inputArray.length;
        System.out.println("需要加密的数据的长度是 ："+inputLength);
        // 这个最大长度是 1024/8 - 11 ： （密钥长度/8 -11）
        int MAX_ENCRYPT_BLOCK = 117;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }

        return Base64.encodeBase64String(resultBytes);

        // 执行加密操作
        //byte[] result = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        //return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String privateKeyDecrypt(String data,String privateKey) throws Exception{
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(getPrivateKey(privateKey).getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKeyUse = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKeyUse);

        // 下面进行漂亮的分段解密
        byte[] inputArray = Base64.decodeBase64(data.getBytes("UTF-8"));
        int inputLength = inputArray.length;
        System.out.println("目标解密字符串的长度是 : "+inputLength);
        // 最大解密字节数，超出最大字节数需要分组加密 1024 / 8 (密钥长度 / 8)
        int MAX_ENCRYPT_BLOCK = 128;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }
        return new String(resultBytes);

        //byte[] result = cipher.doFinal(Base64.decodeBase64(data));
        //return new String(result);
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String publicKeyEncrypt(String data,String publicKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(getPublicKey(publicKey).getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKeyUse = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyUse);
        // 执行漂亮的分段加密
        byte[] inputArray = data.getBytes();
        int inputLength = inputArray.length;
        System.out.println("需要加密的数据的长度是 ："+inputLength);
        // 这个最大长度是 1024/8 - 11 ： （密钥长度/8 -11）
        int MAX_ENCRYPT_BLOCK = 117;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }

        return Base64.encodeBase64String(resultBytes);


    }


    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicKeyDecrypt(String data,String publicKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(getPublicKey(publicKey).getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKeyUse = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKeyUse);

        // 下面进行漂亮的分段解密
        byte[] inputArray = Base64.decodeBase64(data.getBytes("UTF-8"));
        int inputLength = inputArray.length;
        System.out.println("目标解密字符串的长度是 : "+inputLength);
        // 最大解密字节数，超出最大字节数需要分组加密 1024 / 8 (密钥长度 / 8)
        int MAX_ENCRYPT_BLOCK = 128;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }
        return new String(resultBytes);

        // 执行解密操作
        //byte[] result = cipher.doFinal(Base64.decodeBase64(data));
        //return new String(result);
    }



    public static void main(String[] args) {

        try {
            //new RSAUtile().initKeys();

            String dataaa = "7b0a202020202268656164223a207b0a20202020202020202274696d657374616d70223a202231363736353333373337343335222c0a2020202020202020226e6f6e6365737472223a2022313233222c0a2020202020202020227369676e6174757265223a2022313233220a202020207d2c0a2020202022636f6e74656e74223a207b0a2020202020202020226c6f636174696f6e223a2022220a202020207d0a7d";
            // 情景一 ： 服务端加密；客户端解密 OK
            // 私钥加密；公钥解密
            String encryptData = privateKeyEncrypt(dataaa, privateKey);
            System.out.println("encryptData = " + encryptData);
            String decryptData = publicKeyDecrypt(encryptData, publicKey);
            System.out.println("decryptData = " + decryptData);
            System.out.println("oldddddData = " + dataaa);

            String aa = "I84SYyHyr1iFgi/hxeJdIZ9MIunCc4ZCSE40OtMz8U0Ygjmdv0nZpAYvn07ca/wv1q0u4KO5iWl4RvA4ZGHUkoAGprwp12CsxRN4+V18NlzAB9rMWjtcUYMPYcjKM8ABx0sToUj8iGkNag8W4RKOUm1QCH5bMibzJfzRLjAkeNojcr2ZG7nbNQI1l8Cya//4W1yelTPYoyEO/2Xn2r8w9ItQb4TGdo8DOQ5scJwvY6DnRIoxDwhtWHDY1lon00Q8HF2P1fcP9MoDfjSJT5OPnZ9a9Wn2rFkzy1xVn3UpXIPgsZJDLxj9pCyCLsmU8ZSWn2smJOIjYVLZRfVhUzZbTW7KON0ggtc2RSqlpnsIv86OS0ovaWk/fN0ETtFO9+G6rZVWi4srrk6/8Ob5FgwgAdQwCIcuhKULk48qY+2QlcYvx414itXm1nYhd5OR0eAO/3HKcMEGNsrOjfR6ZIFIxmaCBsiMyVZk68zWCYGSDx8KdluL3d28XqJrfX74/6uocyrCveUo6jBy0Vj6BPf1KNVDnEO/DWDzZuQM80Vf7Xi1S9Kg3HGj2UH+Cv/hS+3KK/8RLlmaT8rl3BugyCQlc0KuBgI+Jps2dXX0SKH+siMEI43VGGq+0I4FDk2HmvE53PTKVRty5NLyXKRqsmCWZ3nxNf2IA51P9gxXGEWxr4Vjv0xLGvoH2bhJL7tboF1o1BJXwm9p7J4Y1IvOSzBD0enSFIs9E9haLM4wtFTJx+xQ+CNz/assg3TB6xygiAkDwlzlYKJAP/KqtYWb/JekCqVd789jJzTj4FnPFojCBHgqZPgyWDsYfW+QdLvtc76A0nAQCQFSdKKIhDMDIU8dnQ==";
            String s = publicKeyDecrypt(aa, publicKey);
            System.out.println("s = " + s);


            System.out.println();

            // 情景二 ：客户端加密；服务端解密
            // 公钥加密；私钥解密
            String  databb = "7b0a202020202268656164223a207b0a20202020202020202274696d657374616d70223a202231363736383738383637333831222c0a2020202020202020226e6f6e6365737472223a2022313233222c0a2020202020202020227369676e6174757265223a202262633732376539383663303662333833613832653063613531356261353335666464336333626664633664376464323635383366393332636363353861653062220a202020207d2c0a2020202022636f6e74656e74223a207b0a202020202020202022787a71685f6964223a202230303430222c0a2020202020202020226c61737474696d65223a202231363736373836393739303030220a202020207d0a7d";
            String s1 = publicKeyEncrypt(databb, publicKey);
            System.out.println("s1 = " + s1);
            String s2 = privateKeyDecrypt(s1, privateKey);
            System.out.println("s2 = " + s2);
            System.out.println("dd = "+databb);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
