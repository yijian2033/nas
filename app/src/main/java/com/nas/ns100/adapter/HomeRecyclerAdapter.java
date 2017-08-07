package com.nas.ns100.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nas.ns100.R;
import com.nas.ns100.bean.HomeBean;
import com.nas.ns100.iface.OnItemClickListener;

import java.util.ArrayList;

/**
 * 关于home的adapter
 * 自定义实现 item 点击和 长按监听
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeHolder> {

    private ArrayList<HomeBean> listDevice;
    private Context mContext;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public HomeRecyclerAdapter(Context context, ArrayList<HomeBean> arrayList) {
        this.mContext = context;
        this.listDevice = arrayList;
    }


    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeHolder holder = new HomeHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final HomeHolder holder, final int position) {
        HomeBean homeBean = listDevice.get(position);
        holder.tvName.setText(homeBean.getDeviceName() + ":");
        holder.tvID.setText(homeBean.getDeviceId());
        //点击事件的监听
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    listener.onItemClick(holder.itemView, layoutPosition);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listDevice.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvID;

        public HomeHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_home_deviceName);
            tvID = (TextView) view.findViewById(R.id.tv_home_deviceID);
        }
    }

}
