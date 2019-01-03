package com.serialportlibrary.service;

/**
 * @author HanPei
 * @date 2018/12/27  下午4:18
 */
public interface SendDataCallback {
    /**
     * 接收到数据
     * @param data 数据
     */
    void receiveData(byte[] data);

    /**
     * 接收数据失败
     */
    void recevieFail();
}
