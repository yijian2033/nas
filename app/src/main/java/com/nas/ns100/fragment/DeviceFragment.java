package com.nas.ns100.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nas.ns100.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2017/8/7.
 */

public class DeviceFragment extends Fragment {

    private View view;
    private TextView mTvShowMsg;
    private EditText mEtEditMsg;
    private Button mBtSendMsg;

    private static final String HOST = "192.168.1.55";
    private static final int PORT = 9999;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_device, container, false);
        mTvShowMsg = (TextView) view.findViewById(R.id.tv_showMsg);
        mEtEditMsg = (EditText) view.findViewById(R.id.et_editMsg);
        mBtSendMsg = (Button) view.findViewById(R.id.bt_sendMsg);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intData();
    }

    private void intData() {

        mBtSendMsg.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String msg = mEtEditMsg.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (socket.isConnected()) {
                            if (!socket.isOutputShutdown()) {
                                out.println(msg);
                            }
                        }
                    }
                }).start();

            }
        });
        new Thread(new SocketThread()).start();
    }

    public void ShowDialog(String msg) {
        new AlertDialog.Builder(getActivity()).setTitle("notification").setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                }).show();
    }

    class SocketThread implements Runnable {

        @Override
        public void run() {
            try {


                socket = new Socket(HOST, PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                while (true) {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            if ((content = in.readLine()) != null) {
                                content += "\n";
                                mHandler.sendMessage(mHandler.obtainMessage());
                            } else {

                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTvShowMsg.setText(content);
        }
    };

}
