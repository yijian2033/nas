package com.nas.ns100.thread;

import android.util.Log;

import com.nas.ns100.constant.Content;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * UDP多广播接
 * 接受线程
 */

public class UdpReceiverThread implements Runnable {

    private MulticastSocket multicastSocket;
    private static final String TAG = UdpReceiverThread.class.getName();

    @Override
    public void run() {
        try {
            Log.d(TAG, "接受线程启动");
            multicastSocket = new MulticastSocket(Content.UDP_PORT);
            multicastSocket.setTimeToLive(Content.UDP_TTL_TIME);
            InetAddress byAddress = InetAddress.getByName(Content.UDP_IP);
            multicastSocket.joinGroup(byAddress);

            byte[] bytes = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, 1024);

            while (true) {
                multicastSocket.receive(datagramPacket);
                String result = new String(bytes, 0, datagramPacket.getLength());
                Log.d(TAG, "--收到发送的ip---：" + result);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
