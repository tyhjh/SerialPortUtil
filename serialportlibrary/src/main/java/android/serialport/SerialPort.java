package android.serialport;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 串口通信，打开串口，读写数据
 */
public class SerialPort {

    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    static {
        System.loadLibrary("serial_port");
    }

    /**
     * 打开串口
     *
     * @param device
     * @param baudrate
     */
    public SerialPort(File device, int baudrate) throws IOException {
        if (!device.canRead() || !device.canWrite()) {
            try {
                Process su;
                su = Runtime.getRuntime().exec("su");
                String cmd = "chmod 777 " + device.getAbsolutePath();
                su.getOutputStream().write(cmd.getBytes());
                su.getOutputStream().flush();
                int waitFor = su.waitFor();
                boolean canRead = device.canRead();
                boolean canWrite = device.canWrite();
                if (waitFor != 0 || !canRead || !canWrite) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mFd = open(device.getAbsolutePath(), baudrate);

        if (mFd == null) {
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }

    /**
     * 关闭串口
     */
    public void closePort() {
        if (this.mFd != null) {
            try {
                this.close();
                this.mFd = null;
                this.mFileInputStream = null;
                this.mFileOutputStream = null;
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }
    }

    public InputStream getInputStream() {
        return mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }

    /**
     * JNI，设备地址和波特率
     */
    private native static FileDescriptor open(String path, int baudrate);

    private native void close();


}
