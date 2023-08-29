#### App备案需要的证书公钥获取方式

#### 1.导出签名文件的cer证书
```
keytool -export -alias xxx -keystore xxx.jks -file xxx.cer
例：keytool -export -alias demo -keystore demo.jks -file demo.cer
```

#### 2.电脑双击打开demo.cer，查看证书详细信息，复制公钥信息




参考资料：https://blog.51cto.com/u_15861646/5823235
