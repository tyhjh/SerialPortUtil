package com.serialportlibrary.service.impl;

import android.os.SystemClock;

import android.serialport.SerialPort;

import com.serialportlibrary.service.ISerialPortService;
import com.serialportlibrary.service.SendDataCallback;
import com.serialportlibrary.util.ByteStringUtil;
import com.serialportlibrary.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


/**
 * @author HanPei
 * @date 2018/12/26  下午2:42
 */
public class SerialPortServiceImpl implements ISerialPortService {

    /**
     * 尝试读取数据间隔时间
     */
    private Long mReadWaiteTime=30L;

    /**
     * 读取返回结果超时时间
     */
    private Long mTimeOut;
    /**
     * 串口地址
     */
    private String mDevicePath;

    /**
     * 波特率
     */
    private int mBaudrate;

    SerialPort mSerialPort;


    /**
     * 确保读取一条数据完整的数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private byte[] readFullData(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        byte[] returnData;
        //暂存每次返回数据长度，不变的时候为读取完整条数据
        int receiveLeanth = 0;
        int available;
        while (true) {
            available = inputStream.available();
            if (available == receiveLeanth) {
                returnData = new byte[available];
                inputStream.read(returnData);
                return returnData;
            } else {
                LogUtil.e("available长度为："+available);
                receiveLeanth = available;
            }
            SystemClock.sleep(mReadWaiteTime);
        }
    }

    @Override
    public byte[] sendData(byte[] data) {
        synchronized (SerialPortServiceImpl.this) {
            try {
                InputStream inputStream = mSerialPort.getInputStream();
                OutputStream outputStream = mSerialPort.getOutputStream();
                //清除未读取的数据
                readFullData(inputStream);
                //发送数据
                outputStream.write(data);
                outputStream.flush();
                LogUtil.e("发送数据-------" + Arrays.toString(data));
                Long time = System.currentTimeMillis();
                while (System.currentTimeMillis() - time < mTimeOut) {
                    //判断是否开始返回数据
                    if (inputStream.available() > 0) {
                        byte[] returnData = readFullData(inputStream);
                        LogUtil.e("接收--数据-------" + Arrays.toString(returnData));
                        return returnData;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new byte[0];
        }
    }


    @Override
    public byte[] sendData(String date) {
        try {
            return sendData(ByteStringUtil.hexStrToByteArray(date));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public byte[] receiveData() {
        try {
            InputStream inputStream = mSerialPort.getInputStream();
            //清除未读取的数据
            readFullData(inputStream);
            Long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < mTimeOut) {
                //判断是否开始返回数据
                if (inputStream.available() > 0) {
                    byte[] returnData = readFullData(inputStream);
                    LogUtil.e("接收--数据-------" + Arrays.toString(returnData));
                    return returnData;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    @Override
    public void close() {
        if (mSerialPort != null) {
            mSerialPort.closePort();
        }
    }


    public void isOutputLog(boolean debug) {
        LogUtil.isDebug = debug;
    }

    public void setReadWaiteTime(Long readWaiteTime) {
        mReadWaiteTime = readWaiteTime;
    }

    public void setTimeOut(Long timeOut) {
        mTimeOut = timeOut;
    }

    public void setDevicePath(String devicePath) {
        mDevicePath = devicePath;
    }

    public void setBaudrate(int baudrate) {
        mBaudrate = baudrate;
    }

    public void setSerialPort(SerialPort serialPort) {
        mSerialPort = serialPort;
    }


    public Long getReadWaiteTime() {
        return mReadWaiteTime;
    }

    public Long getTimeOut() {
        return mTimeOut;
    }

    public String getDevicePath() {
        return mDevicePath;
    }

    public int getBaudrate() {
        return mBaudrate;
    }

    public SerialPort getSerialPort() {
        return mSerialPort;
    }
}
