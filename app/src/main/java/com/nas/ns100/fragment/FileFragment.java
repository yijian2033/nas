package com.nas.ns100.fragment;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nas.ns100.R;
import com.nas.ns100.constant.Content;
import com.nas.ns100.thread.SendUpdBroadCastThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * 文件管理的
 */

public class FileFragment extends Fragment {

    private static final String TAG = FileFragment.class.getName();

    private View view;
    private Button sendUdp;
    private WifiManager wifiManager;
    private DatagramSocket socket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_file, container, false);
        sendUdp = (Button) view.findViewById(R.id.bt_send);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取wifi服务
        wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        try {
            socket = new DatagramSocket(Content.UDP_PORT);
            socket.setSoTimeout(4);

        } catch (IOException e) {
            e.printStackTrace();
        }


        sendUdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendThread().start();
            }
        });
    }


    private class SendThread extends Thread {
        @Override
        public void run() {
            try {
                WifiInfo info = wifiManager.getConnectionInfo();
                int ipAddress = info.getIpAddress();
                String ip = intToIp(ipAddress);
                Log.d(TAG, "获取的本地连接ip:" + ip);
                byte[] data = ip.getBytes();
                InetAddress address = InetAddress.getByName(Content.UDP_IP);
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, Content.UDP_PORT);
                socket.send(datagramPacket);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

}
