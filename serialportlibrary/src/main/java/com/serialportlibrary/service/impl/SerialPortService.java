package com.serialportlibrary.service.impl;

import android.os.SystemClock;

import android.serialport.SerialPort;

import com.serialportlibrary.service.ISerialPortService;
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
public class SerialPortService implements ISerialPortService {

    /**
     * 尝试读取数据间隔时间
     */
    private static int RE_READ_WAITE_TIME = 10;

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

    SerialPort mSerialPort;

    /**
     * 初始化串口
     *
     * @param devicePath 串口地址
     * @param baudrate   波特率
     * @param timeOut    数据返回超时时间
     * @throws IOException 打开串口出错
     */
    public SerialPortService(String devicePath, int baudrate, Long timeOut) throws IOException {
        mTimeOut = timeOut;
        mDevicePath = devicePath;
        mBaudrate = baudrate;
        mSerialPort = new SerialPort(new File(mDevicePath), mBaudrate);
    }

    @Override
    public byte[] sendData(byte[] data) {
        synchronized (SerialPortService.this) {
            try {
                InputStream inputStream = mSerialPort.getInputStream();
                OutputStream outputStream = mSerialPort.getOutputStream();
                int available = inputStream.available();
                byte[] returnData;
                if (available > 0) {
                    returnData = new byte[available];
                    inputStream.read(returnData);
                }
                outputStream.write(data);
                outputStream.flush();
                LogUtil.e("发送数据-------" + Arrays.toString(data));
                Long time = System.currentTimeMillis();
                //暂存每次返回数据长度，不变的时候为读取完数据
                int receiveLeanth = 0;
                while (System.currentTimeMillis() - time < mTimeOut) {
                    available = inputStream.available();
                    if (available > 0 && available == receiveLeanth) {
                        returnData = new byte[available];
                        inputStream.read(returnData);
                        LogUtil.e("接收--数据-------" + Arrays.toString(returnData));
                        return returnData;
                    } else {
                        receiveLeanth = available;
                    }
                    SystemClock.sleep(RE_READ_WAITE_TIME);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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
    public void close() {
        if (mSerialPort != null) {
            mSerialPort.closePort();
        }
    }


    public void isOutputLog(boolean debug) {
        LogUtil.isDebug = debug;
    }


}
