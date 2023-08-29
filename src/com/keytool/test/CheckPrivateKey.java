package com.keytool.test;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class CheckPrivateKey {
    private File keystoreFile;
    private String keyStoreType;
    private char[] password;
    private String alias;
    private File exportedFile;

    /**
     * 校验公私钥
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        CheckPrivateKey export = new CheckPrivateKey();
        export.keystoreFile = new File("src/demo.jks");
        export.keyStoreType = "JKS";
        export.password = "test01".toCharArray();
        export.alias = "demo";
        export.exportedFile = new File("src/demo.txt");
        PrivateKey privateKey = export.export();
        // 验证加密解密
        PublicKey pKey = gePublic();
        System.out.println("加密串:我用这串字符进行加密");
        byte[] plainText = "我用这串字符进行加密".getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pKey);
        // 用公钥进行加密，返回一个字节流
        byte[] cipherText = cipher.doFinal(plainText);
        System.out.println("====================================");
        System.out.println(new String(cipherText, "Utf-8"));
        System.out.println("====================================");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 用私钥进行解密，返回一个字节流
        byte[] newPlainText = cipher.doFinal(cipherText);
        System.out.println("解密串:"+new String(newPlainText, "UTF-8"));
    }
    private static PublicKey gePublic() {
        // 生成一个证书对象并使用从输入流 inStream 中读取的数据对它进行初始化。
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream in = new FileInputStream(
                    "src/demo.cer");
            Certificate c = cf.generateCertificate(in);
            PublicKey publicKey = c.getPublicKey();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public PrivateKey export() throws Exception {
        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        BASE64Encoder encoder = new BASE64Encoder();
        keystore.load(new FileInputStream(keystoreFile), password);
        KeyPair keyPair = getPrivateKey(keystore, alias, password);
        PrivateKey privateKey = keyPair.getPrivate();
        String encoded = encoder.encode(privateKey.getEncoded());
        FileWriter fw = new FileWriter(exportedFile);
        fw.write("—–BEGIN PRIVATE KEY—–/n");
        System.out.println("—–BEGIN PRIVATE KEY—–/n");
        fw.write(encoded);
        System.out.println(encoded + "/n");
        fw.write("/n");
        fw.write("—–END PRIVATE KEY—–");
        System.out.println("—–END PRIVATE KEY—–");
        fw.close();
        return privateKey;

    }
    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try{
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
