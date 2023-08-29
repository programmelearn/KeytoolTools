package com.keytool.test;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.*;
import java.security.cert.Certificate;

/**
 * 导出私钥
 */
public class ExportPrivateKey {
    private File keystoreFile;
    private String keyStoreType;
    private char[] password;
    private String alias;
    private File exportedFile;

    public static void main(String args[]) throws Exception {
        ExportPrivateKey export = new ExportPrivateKey();
        export.keystoreFile = new File("src/demo.jks");
        export.keyStoreType = "JKS";
        export.password = "test01".toCharArray();
        export.alias = "demo";
        export.exportedFile = new File("src/demo.txt");
        export.export();
    }
    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
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

    public void export() throws Exception {
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

    }
}
