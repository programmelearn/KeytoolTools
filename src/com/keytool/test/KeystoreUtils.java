package com.keytool.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeystoreUtils {


    //App备案需要的证书公钥获取方式
    //keytool -export -alias xxx -keystore xxx.jks -file xxx.cer
    //例：keytool -export -alias demo -keystore demo.jks -file demo.cer
    //电脑双击打开demo.cer，查看证书详细信息，复制公钥信息





    /**
     * 获取jks公钥，注意不是App备案的证书公钥
     * @param args
     */
//    public static void main(String[] args) {
//        try {
//            PublicKey publicKey = getPublicKeyFromKeystore("src/demo.jks", "test01", "demo");
//            System.out.println(publicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static PublicKey getPublicKeyFromKeystore(String keystorePath, String keystorePassword, String alias) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
//        FileInputStream fis = null;
//        try {
//            KeyStore keyStore = KeyStore.getInstance("JKS");
//            fis = new FileInputStream(keystorePath);
//            keyStore.load(fis, keystorePassword.toCharArray());
//
//            Certificate certificate = keyStore.getCertificate(alias);
//            PublicKey publicKey = certificate.getPublicKey();
//
//            return publicKey;
//        } finally {
//            if (fis != null) {
//                fis.close();
//            }
//        }
//    }
}
