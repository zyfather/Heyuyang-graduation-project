package com.example.yang.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.utils.AlarmUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang on 16/5/15.
 * 首页闹钟列表adapter
 */
public class AlarmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<AlarmData> mDatas;
    Context mContext;
    Map<Integer, Boolean> switchMap;

    public AlarmAdapter(Context mContext) {
        this.mContext = mContext;
        switchMap = new HashMap<>();
    }

    public void setmDatas(List<AlarmData> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (mDatas.size() > position) {
            if (switchMap.get(position) != null && switchMap.get(position)) {
                ((ViewHolder) holder).itemView.setAlpha(0.5f);
                ((ViewHolder) holder).mSwitch.setChecked(false);
            } else {
                ((ViewHolder) holder).itemView.setAlpha(1.0f);
                ((ViewHolder) holder).mSwitch.setChecked(true);
            }
            AlarmData data = mDatas.get(position);
            if (data != null) {
                ((ViewHolder) holder).mTime.setText(data.getTimeStr());
                ((ViewHolder) holder).mInfo.setText(data.getName() + ", " + data.getTypeStr());
                if (data.isOn()) {
                    ((ViewHolder) holder).mSwitch.setChecked(true);
                } else {
                    ((ViewHolder) holder).itemView.setAlpha(0.5f);
                    ((ViewHolder) holder).mSwitch.setChecked(false);
                }
                ((ViewHolder) holder).mSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatas.get(position).setSwitch();
                        if (mDatas.get(position).isOn()) {
                            ((ViewHolder) holder).itemView.setAlpha(1.0f);
                            switchMap.put(position, false);

                        } else {
                            ((ViewHolder) holder).itemView.setAlpha(0.5f);
                            switchMap.put(position, true);
                        }
                        AlarmUtil.replaceAlarm(mContext, mDatas);
                    }
                });
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 跳转编辑
                        Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                    }
                });
                ((ViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mDatas.remove(position);
                        notifyDataSetChanged();
                        return true;
                    }
                });
            }


        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTime;
        public TextView mInfo; //detail, 频率 (String)
        public Switch mSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.alarm_time);
            mInfo = (TextView) itemView.findViewById(R.id.alarm_freq);
            mSwitch = (Switch) itemView.findViewById(R.id.alarm_switch);
        }

    }


}
