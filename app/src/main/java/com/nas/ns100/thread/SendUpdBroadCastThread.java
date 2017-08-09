package com.nas.ns100.thread;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.nas.ns100.R;
import com.nas.ns100.constant.Content;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * UDP多广播，没有经过路由器，无法用局域网连接另外一台机器
 */

public class SendUpdBroadCastThread implements Runnable {

    private static final String TAG = SendUpdBroadCastThread.class.getName();
    private MulticastSocket socket;
    private WifiManager wifiManager;

    public SendUpdBroadCastThread(WifiManager wifiManager, MulticastSocket socket) {
        this.socket = socket;
        this.wifiManager = wifiManager;
    }

    @Override
    public void run() {
        try {


            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = intToIp(ipAddress);

//            //获取本地的ip地址
//            InetAddress localHost = InetAddress.getLocalHost();
//            String hostAddress = localHost.getHostAddress();
//            String hostName = localHost.getHostName();
//            byte[] adds = localHost.getAddress();
//            Log.d(TAG, "本地的ip地址：" + hostAddress + "本地名称：" + hostName + ":getAddress：" + adds.toString());
//            byte[] bytes = hostAddress.getBytes();

            byte[] bytes = ip.getBytes();

            //发送的广播地址
            InetAddress address = InetAddress.getByName(Content.UDP_IP);

            DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, address, Content.UDP_PORT);
            socket.send(packet);
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
