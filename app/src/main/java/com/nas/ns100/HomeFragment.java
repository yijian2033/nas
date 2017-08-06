package com.nas.ns100;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yijian2033 on 2017/8/6.
 */

public class HomeFragment extends Fragment {

    private View inflate;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_layout, container, false);
        mTextView = (TextView) inflate.findViewById(R.id.tv);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        View viewById = getActivity().findViewById(R.id.tv);
        mTextView.setText(getArguments().getString("JOHN"));
    }

    public static HomeFragment newInstant(String tv) {
        Bundle bundle = new Bundle();
        bundle.putString("JOHN", tv);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

}