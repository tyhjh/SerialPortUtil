package com.serialportlibrary.service.impl;

import java.io.IOException;

/**
 * @author HanPei
 * @date 2018/12/26  下午3:39
 */
public class SerialPortBuilder {

    /**
     * 读取返回结果超时时间
     */
    private Long mTimeOut = 100L;
    /**
     * 串口地址
     */
    private String mDevicePath;

    /**
     * 波特率
     */
    private int mBaudrate;

    public SerialPortBuilder setBaudrate(int baudrate) {
        mBaudrate = baudrate;
        return this;
    }


    public SerialPortBuilder setDevicePath(String devicePath) {
        mDevicePath = devicePath;
        return this;
    }


    public SerialPortBuilder setTimeOut(Long timeOut) {
        mTimeOut = timeOut;
        return this;
    }

    public SerialPortService createService() {
        SerialPortService serialPortService = null;
        try {
            serialPortService = new SerialPortService(mDevicePath, mBaudrate, mTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serialPortService;
    }

}
