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
}
