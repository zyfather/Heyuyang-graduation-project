package com.example.yang.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.model.AlarmItem;

import java.util.List;

/**
 * Created by yang on 16/5/15.
 */
public class AlarmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<AlarmItem> mDatas;
    Context mContext;

    public AlarmAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmDatas(List<AlarmItem> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTime;
        TextView mFreq;
        Switch mSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.alarm_time);
            mFreq = (TextView) itemView.findViewById(R.id.alarm_freq);
            mSwitch = (Switch) itemView.findViewById(R.id.alarm_switch);
        }

    }


}
