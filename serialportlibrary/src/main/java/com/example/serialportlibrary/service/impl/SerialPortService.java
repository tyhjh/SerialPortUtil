package com.example.serialportlibrary.service.impl;

import android.os.SystemClock;
import android.util.Log;

import com.example.serialportlibrary.base.SerialPort;
import com.example.serialportlibrary.service.ISerialPortService;
import com.example.serialportlibrary.util.ByteStringUtil;

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

    private static int RE_READ_WAITE_TIME = 5;

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
        mSerialPort = new SerialPort(new File(devicePath), baudrate);
        mTimeOut = timeOut;
    }


    @Override
    public byte[] sendData(byte[] data) {
        synchronized (SerialPortService.this) {
            //隔一段时间发送，给设备响应时间
            try {
                InputStream inputStream = mSerialPort.getInputStream();
                OutputStream outputStream = mSerialPort.getOutputStream();
                int available = inputStream.available();
                if (available > 0) {
                    byte[] returnData = new byte[available];
                    inputStream.read(returnData);
                    outputStream.write(data);
                    outputStream.flush();
                    Log.d("sendData", "发送数据-------" + Arrays.toString(data));
                    Long time = System.currentTimeMillis();
                    while (System.currentTimeMillis() - time < mTimeOut) {
                        available = inputStream.available();
                        if (available >= 1) {
                            returnData = new byte[available];
                            inputStream.read(returnData);
                            Log.d("sendData", "接收--数据-------" + Arrays.toString(returnData));
                            return returnData;
                        }
                        SystemClock.sleep(RE_READ_WAITE_TIME);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public byte[] sendData(String date) {
        return sendData(ByteStringUtil.hexStrToByteArray(date));
    }

    @Override
    public void close() {
        if (mSerialPort != null) {
            mSerialPort.closePort();
        }
    }


}
