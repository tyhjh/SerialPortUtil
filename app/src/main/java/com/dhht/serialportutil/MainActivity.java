package com.dhht.serialportutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.serialport.SerialPortFinder;
import android.view.View;
import android.widget.TextView;

import com.serialportlibrary.service.impl.SerialPortBuilder;
import com.serialportlibrary.service.impl.SerialPortService;
import com.serialportlibrary.util.ByteStringUtil;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView tv_hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_hello = (TextView) findViewById(R.id.tv_hello);

        String[] devices = new SerialPortFinder().getDevices();
        String[] devicesPath = new SerialPortFinder().getDevicesPaths();

        for (String path : devicesPath) {
            //Log.e("MainActivity：", path);
        }
        for (String device : devices) {
            //Log.e("MainActivity：", device);
        }
        tv_hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //开门指令
                        SerialPortService serialPortService = new SerialPortBuilder()
                                .setTimeOut(100L)
                                .setBaudrate(9600)
                                .setDevicePath("dev/ttyS4")
                                .createService();
                        //发送指令
                        byte[] receiveData = serialPortService.sendData("55AA0101010002");
                        Log.e("MainActivity：", ByteStringUtil.byteArrayToHexStr(receiveData));
                    }
                }).start();

                //酒精测试指令
                SerialPortService serialPortService = new SerialPortBuilder()
                        .setTimeOut(100L)
                        .setBaudrate(19200)
                        .setDevicePath("/dev/ttyS1")
                        .createService();
                //发送指令
                byte[] receiveData = serialPortService.sendData("55AA0500010005");
                if (receiveData != null) {
                    Log.e("MainActivity：", ByteStringUtil.byteArrayToHexStr(receiveData));
                }


            }
        });

    }
}
