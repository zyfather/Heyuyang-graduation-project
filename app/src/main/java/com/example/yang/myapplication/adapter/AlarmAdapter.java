package com.example.yang.myapplication.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.activity.EditActivity;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.utils.AlarmUtil;
import com.example.yang.myapplication.utils.ScreenUtil;

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
            View mItem = ((ViewHolder) holder).itemView;
            final TextView mTime = ((ViewHolder) holder).mTime;
            final TextView mInfo = ((ViewHolder) holder).mInfo; //detail, 频率 (String)
            final Switch mSwitch = ((ViewHolder) holder).mSwitch;
            mInfo.setSelected(true);

            if (switchMap.get(position) != null && switchMap.get(position)) {
                mItem.setAlpha(0.5f);
                mSwitch.setChecked(false);
            } else {
                mItem.setAlpha(1.0f);
                mSwitch.setChecked(true);
            }
            final AlarmData data = mDatas.get(position);
            if (data != null) {
                mTime.setText(data.getTimeStr());
                String displayStr = data.getName();
                if (data.isRepeat()) {
                    displayStr += ", " + data.getTypeStr();
                }
                mInfo.setText(displayStr);
                if (data.isOn()) {
                    mSwitch.setChecked(true);
                } else {
                    mItem.setAlpha(0.5f);
                    mSwitch.setChecked(false);
                }
                mSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        AlarmData alarmData = mDatas.get(position);
                        data.setSwitch();
                        if (data.isOn()) {
                            ((ViewHolder) holder).itemView.setAlpha(1.0f);
                            switchMap.put(position, false);
                            AlarmUtil.windUp(mContext, data);
                        } else {
                            ((ViewHolder) holder).itemView.setAlpha(0.5f);
                            switchMap.put(position, true);
                            AlarmUtil.windDown(mContext, data);
                        }
                        AlarmUtil.updateAlarm(mContext, data);
//                        AlarmUtil.replaceAlarm(mContext, mDatas.toArray(new AlarmData[mDatas.size()]));
                    }
                });
                mItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 跳转编辑
                        Intent toEdit = new Intent(mContext, EditActivity.class);
                        toEdit.putExtra("editAlarm", data);
                        mContext.startActivity(toEdit);
                    }
                });
                mItem.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        int w = ScreenUtil.getScreenWidth(mContext);

                        int leave_switch = mSwitch.getMeasuredWidth() + mSwitch.getPaddingRight();
                        int leave_time = mTime.getMeasuredWidth() + mTime.getPaddingLeft();
                        int leave_info = mInfo.getMeasuredWidth() + mInfo.getPaddingLeft();

                        ObjectAnimator.ofFloat(mSwitch, "translationX", leave_switch).setDuration(400).start();
                        ObjectAnimator.ofFloat(mTime, "translationX", -leave_time).setDuration(400).start();
                        ObjectAnimator.ofFloat(mInfo, "translationX", -leave_info).setDuration(400).start();

                        View contentView = LayoutInflater.from(mContext).inflate(
                                R.layout.delete_bubble, null);
                        final PopupWindow popupWindow = new PopupWindow(contentView,
                                -2, -2, true);
//                        popupWindow.setAnimationStyle(R.style.bubble_expand);
                        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(
                                R.drawable.crystal));
                        popupWindow.getContentView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlarmUtil.deleteAlarm(mContext, data);
                                mDatas.remove(position);
                                notifyDataSetChanged();
                                popupWindow.dismiss();
                            }
                        });
                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                if (mTime != null && mSwitch != null && mInfo != null) {
                                    ObjectAnimator.ofFloat(mSwitch, "translationX", -1).setDuration(400).start();
                                    ObjectAnimator.ofFloat(mTime, "translationX", 1).setDuration(400).start();
                                    ObjectAnimator.ofFloat(mInfo, "translationX", 1).setDuration(400).start();
                                }
                            }
                        });
                        popupWindow.showAsDropDown(v, w / 2, -ScreenUtil.dp2px(mContext, 100), Gravity.FILL);
                        popupWindow.update();

                        return true;
                    }
                });
            }


        }

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
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
