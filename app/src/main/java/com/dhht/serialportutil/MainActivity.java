package com.dhht.serialportutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.serialport.SerialPortFinder;
import android.view.View;
import android.widget.TextView;

import com.serialportlibrary.service.impl.SerialPortBuilder;
import com.serialportlibrary.service.impl.SerialPortServiceImpl;
import com.serialportlibrary.util.ByteStringUtil;

/**
 * @author HanPei
 * @date 2018/12/26  下午3:39
 */
public class MainActivity extends AppCompatActivity {

    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHello = (TextView) findViewById(R.id.tv_hello);

        //获取所有串口名字
        String[] devices = new SerialPortFinder().getDevices();
        //获取所有串口地址
        String[] devicesPath = new SerialPortFinder().getDevicesPaths();

        for (String path : devicesPath) {
            Log.e("MainActivity：", path);
        }
        for (String device : devices) {
            Log.e("MainActivity：", device);
        }


        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SerialPortServiceImpl serialPortServiceImpl = new SerialPortBuilder()
                        .setTimeOut(100L)
                        .setBaudrate(9600)
                        .setDevicePath("dev/ttyS4")
                        .createService();
                serialPortServiceImpl.isOutputLog(true);
                //发送开门指令
                byte[] receiveData = serialPortServiceImpl.sendData("55AA0101010002");
                Log.e("MainActivity：", ByteStringUtil.byteArrayToHexStr(receiveData));
                //关闭串口
                serialPortServiceImpl.close();

            }
        });

    }
}
