package com.serialportlibrary.service;

/**
 * @author HanPei
 * @date 2018/12/26  下午2:55
 */
public interface ISerialPortService {

    /**
     * 阻塞发送byteArray数据
     *
     * @param data
     * @return
     */
    byte[] sendData(byte[] data);


    /**
     * 非阻塞发送byteArray数据
     *
     * @param data
     * @param callback
     */
    void sendData(byte[] data, SendDataCallback callback);

    /**
     * 阻塞发送十六进制的字符串数据
     *
     * @param data
     * @return
     */
    byte[] sendData(String data);


    /**
     * 非阻塞发送十六进制的字符串数据
     *
     * @param data
     * @param callback
     */
    void sendData(String data, SendDataCallback callback);


    /**
     * 关闭串口
     */
    void close();
}
