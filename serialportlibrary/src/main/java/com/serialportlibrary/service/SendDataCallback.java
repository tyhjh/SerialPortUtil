package com.serialportlibrary.service;

/**
 * @author HanPei
 * @date 2018/12/27  下午4:18
 */
public interface SendDataCallback {
    void receiveData(byte[] data);
    void recevieFail();
}
