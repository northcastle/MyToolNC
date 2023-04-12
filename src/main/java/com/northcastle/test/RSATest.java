package com.northcastle.test;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-10  13:59
 * @Description: 测试一下 RSA 的加解密操作
 *
 */
public class RSATest {


    private static final String SRC = "I'm RSA encryption algorithm ~!@#$%^&*()_+=-";

    public static void main(String[] args) throws Exception {
        rsaUse();
    }

    /**
     * 测试使用 RSA 加解密
     */
    private static void rsaUse() throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        System.out.println("public key is : " + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
        System.out.println("private key is : " + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
        System.out.println();

        // 情景一 ： 服务端：私钥加密；客户端 ：公钥解密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        // 执行加密操作
        byte[] result = cipher.doFinal(SRC.getBytes(StandardCharsets.UTF_8));
        System.out.println("情景一 ：私钥加密，公钥解密 -- 加密 : " + Base64.encodeBase64String(result));

        // 私钥加密，公钥解密 -- 解密
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        // 执行解密操作
        result = cipher.doFinal(result);
        System.out.println("情景一 ：私钥加密，公钥解密 -- 解密 : " + new String(result));

        System.out.println();




        // 情景二 ： 客户端：公钥加密；服务端 ：私钥解密
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] res = cipher.doFinal(SRC.getBytes(StandardCharsets.UTF_8));
        System.out.println("情景二 ：公钥加密，私钥解密 -- 加密 : " + Base64.encodeBase64String(res));

        // 公钥加密，私钥解密 -- 解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        res = cipher.doFinal(res);
        System.out.println("情景二 ：公钥加密，私钥解密 -- 解密 : " + new String(res));









    }

}
