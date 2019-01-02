# SerialPortUtil
Android串口通信简单封装，可以用于和连接串口的硬件通信或者进行硬件调试

### 集成方法：
Step 1. Add the JitPack repository to your build file
```
//Add it in your root build.gradle at the end of repositories:
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
//Add the dependency
dependencies {
	       implementation 'com.github.tyhjh:SerialPortUtil:1.0.0' 
	}
```

### 调用方法
读取文件权限应该是需要的
```java
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

获取所有串口地址
```
String[] devicesPath = new SerialPortFinder().getDevicesPaths();
```

打开串口，设置读取返回数据超时时间
```java
SerialPortService serialPortService = new SerialPortBuilder()
                        .setTimeOut(100L)
                        .setBaudrate(9600)
                        .setDevicePath("dev/ttyS4")
                        .createService();
```

发送指令
```java
//发送byte数组数据
byte[] receiveData = serialPortService.sendData(new byte[2]);

//发送16进制的字符串
byte[] receiveData = serialPortService.sendData("55AA0101010002");
Log.e("MainActivity：", ByteStringUtil.byteArrayToHexStr(receiveData));
```

打开或者关闭日志,默认关闭
```java
serialPortService.isOutputLog(true);
```
//关闭串口
```java
serialPortService.close();
```

> 项目源码：https://github.com/tyhjh/SerialPortUtil
