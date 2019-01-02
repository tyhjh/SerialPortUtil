package com.serialportlibrary.util;

import android.util.Log;

/**
 * @author HanPei
 * @date 2018/12/27  上午10:00
 */
public class LogUtil {
    public static boolean isDebug = false;


    public static void e(String msg) {
        if (isDebug) {
            Log.e("SerialPort", msg);
        }
    }

    public static void main(String[] args) {
        byte[] bytes=intToByte4(1280);
        String hexStr=Integer.toHexString(1280);
        System.out.println(hexStr);
    }

    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

}
