package com.example.dhht.serialportutil;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.serialportlibrary.base.SerialPortFinder;
import com.example.serialportlibrary.service.impl.SerialPortBuilder;
import com.example.serialportlibrary.service.impl.SerialPortService;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] devices = new SerialPortFinder().getAllDevices();
        String[] devicesPath = new SerialPortFinder().getAllDevicesPath();

        for (String path : devicesPath) {
            Log.e("MainActivity：", path);
        }
        for (String device : devices) {
            Log.e("MainActivity：", device);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                SerialPortService serialPortService = new SerialPortBuilder()
                        .setTimeOut(100L)
                        .setBaudrate(9600)
                        .setDevicePath("dev/ttyS4")
                        .createService();

                //发送指令
                byte[] receiveData=serialPortService.sendData("55AA0100010001");
                Log.e("MainActivity：", Arrays.toString(receiveData));
            }
        }).start();

    }
}
