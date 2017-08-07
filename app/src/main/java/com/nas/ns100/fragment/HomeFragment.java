package com.nas.ns100.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nas.ns100.R;
import com.nas.ns100.adapter.HomeRecyclerAdapter;
import com.nas.ns100.bean.HomeBean;
import com.nas.ns100.iface.OnItemClickListener;

import java.util.ArrayList;

/**
 * 主页fragment用于控制音量和设备
 */

public class HomeFragment extends Fragment {

    private View inflateView;
    private TextView mTextView;
    private Button mBtAddVoice, mBtLowVoice;
    private RecyclerView mRvListDevice;
    private ArrayList<HomeBean> list;
    private HomeRecyclerAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflateView = inflater.inflate(R.layout.fragment_home, container, false);
        mBtAddVoice = (Button) inflateView.findViewById(R.id.bt_addVoice);
        mBtLowVoice = (Button) inflateView.findViewById(R.id.bt_lowVoice);
        mRvListDevice = (RecyclerView) inflateView.findViewById(R.id.rv_listDevice);
        mRvListDevice.setLayoutManager(new LinearLayoutManager(getActivity()));//设置为listView
//        mRvListDevice.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));//设置item分割线
        return inflateView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    private void initListener() {
        homeAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "点中了：" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "长按了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {

        list = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            HomeBean homeBean = new HomeBean();
            homeBean.setDeviceName("" + (char) i);
            homeBean.setDeviceId("" + i);
            list.add(homeBean);
        }
        homeAdapter = new HomeRecyclerAdapter(getContext(), list);
        mRvListDevice.setAdapter(homeAdapter);
    }

    public static HomeFragment newInstant(String tv) {
        Bundle bundle = new Bundle();
        bundle.putString("JOHN", tv);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

}
