package com.serialportlibrary.service;

/**
 * @author HanPei
 * @date 2018/12/26  下午2:55
 */
public interface ISerialPortService {

    /**
     * 发送byteArray数据
     *
     * @param data
     * @return
     */
    byte[] sendData(byte[] data);

    /**
     * 发送十六进制的字符串数据
     *
     * @param data
     * @return
     */
    byte[] sendData(String data);

    /**
     * 关闭串口
     */
    void close();
}
