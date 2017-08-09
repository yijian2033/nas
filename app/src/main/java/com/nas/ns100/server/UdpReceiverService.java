package com.nas.ns100.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.nas.ns100.constant.Content;
import com.nas.ns100.thread.UdpReceiverThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UdpReceiverService extends Service {
    private static final String TAG = UdpReceiverService.class.getName();
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;


    @Override
    public void onCreate() {
        try {

            datagramSocket = new DatagramSocket(Content.UDP_PORT);
//            datagramSocket.setSoTimeout(4);
            new WorkThread().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class WorkThread extends Thread {

        @Override
        public void run() {
            byte[] bytes = new byte[1024];
            datagramPacket = new DatagramPacket(bytes, 1024);
            while (true) {
                try {
                    datagramSocket.receive(datagramPacket);
                    final String result = new String(bytes, 0, datagramPacket.getLength());
                    Log.d(TAG, "返回的结果：" + result);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "接收到的结果：" + result, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
