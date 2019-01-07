package com.serialportlibrary.service.impl;

import android.serialport.SerialPort;

import java.io.File;
import java.io.IOException;

/**
 * @author HanPei
 * @date 2018/12/26  下午3:39
 */
public class SerialPortBuilder {

    SerialPortServiceImpl mSerialPortServiceImpl;

    public SerialPortBuilder() {
        mSerialPortServiceImpl = new SerialPortServiceImpl();
    }

    public SerialPortBuilder setTimeOut(Long timeOut) {
        mSerialPortServiceImpl.setTimeOut(timeOut);
        return this;
    }

    public SerialPortBuilder setReadWaiteTime(Long readWaiteTime) {
        mSerialPortServiceImpl.setReadWaiteTime(readWaiteTime);
        return this;
    }

    public SerialPortBuilder setSerialPort(String devicePath, int baudrate) {
        SerialPort serialPort = null;
        try {
            serialPort = new SerialPort(new File(devicePath), baudrate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSerialPortServiceImpl.setSerialPort(serialPort);
        return this;
    }

    public SerialPortBuilder isOutputLog(boolean isOutputLog) {
        mSerialPortServiceImpl.isOutputLog(isOutputLog);
        return this;
    }

    public SerialPortServiceImpl createService() {
        return mSerialPortServiceImpl;
    }

}
