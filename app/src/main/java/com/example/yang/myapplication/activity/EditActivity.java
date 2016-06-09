package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.yang.myapplication.ConstantValue;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.data.RepeatType;
import com.example.yang.myapplication.data.WeekDay;
import com.example.yang.myapplication.utils.AlarmUtil;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends Activity {

    TimePicker mTimePicker;
    RelativeLayout mRepeat;
    RelativeLayout mTag;
    RelativeLayout mContent;
    RelativeLayout mRing;

    TextView repeatText;    //重复text
    TextView tagText;
    TextView contentText;
    TextView ringText;
    TextView cancelTv;
    TextView saveTv;

    Button mDelete;

    private RepeatType repeatType = new RepeatType();
    private List<String> sysRings;
    private int currentRing;
    private boolean isRepeat;
    private boolean isVib;

    private boolean isAdd;
    private AlarmData saveAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_edit);

        if ((saveAlarm = (AlarmData) getIntent().getSerializableExtra("editAlarm")) == null) {
            isAdd = true;
        }
        initViews();
    }

    private void initViews() {
        cancelTv = (TextView) findViewById(R.id.title_left_tv);
        TextView middleTv = (TextView) findViewById(R.id.title_middle_tv);
        saveTv = (TextView) findViewById(R.id.title_right_tv);
        saveTv.setVisibility(View.VISIBLE);
        findViewById(R.id.title_right_iv).setVisibility(View.GONE);

        cancelTv.setText("取消");
        if (isAdd) {
            middleTv.setText("添加");
        } else {
            middleTv.setText("编辑");
        }
        saveTv.setText("存储");

        mTimePicker = (TimePicker) findViewById(R.id.edit_time_picker);
        mTimePicker.setIs24HourView(true);
        mRepeat = (RelativeLayout) findViewById(R.id.rl_repeat);
        mTag = (RelativeLayout) findViewById(R.id.rl_tag);
        mContent = (RelativeLayout) findViewById(R.id.rl_content);
        mRing = (RelativeLayout) findViewById(R.id.rl_ring);

        repeatText = (TextView) findViewById(R.id.tv_repeat);
        tagText = (TextView) findViewById(R.id.tv_tag);
        contentText = (TextView) findViewById(R.id.tv_content);
        ringText = (TextView) findViewById(R.id.tv_ring);

        initRing();

        if (!isAdd) {
            fillData();
        }

        initClick();

    }

    private void fillData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(saveAlarm.getHour());
            mTimePicker.setMinute(saveAlarm.getMinute());
        } else {
            mTimePicker.setCurrentHour(saveAlarm.getHour());
            mTimePicker.setCurrentMinute(saveAlarm.getMinute());
        }
        if (saveAlarm.isRepeat()) {
            repeatText.setText(saveAlarm.getTypeStr());
        }
        tagText.setText(saveAlarm.getName());
        contentText.setText(saveAlarm.getDetails());
        ringText.setText(sysRings.get(saveAlarm.getRing()));
        currentRing = saveAlarm.getRing();
        isVib = saveAlarm.isVib();

    }

    private void initRing() {

        sysRings = new ArrayList<>();
        RingtoneManager manager = new RingtoneManager(EditActivity.this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();

        for (int i = 0; i < cursor.getCount(); i++) {
            sysRings.add(manager.getRingtone(i).getTitle(EditActivity.this));
        }
    }

    private void initClick() {

        if (!isAdd) {
            mDelete = (Button) findViewById(R.id.edit_delete);
            mDelete.setVisibility(View.VISIBLE);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmUtil.deleteAlarm(EditActivity.this, saveAlarm);
                    finish();
                }
            });
        }

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hou;
                int min;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hou = mTimePicker.getHour();
                    min = mTimePicker.getMinute();
                } else {
                    hou = mTimePicker.getCurrentHour();
                    min = mTimePicker.getCurrentMinute();
                }

                String name = tagText.getText().toString();
                String detail = contentText.getText().toString();
//                String ring = ringText.getText().toString();
                SharedPreferences sp = EditActivity.this.getSharedPreferences(EditActivity.this.getString(R.string.alarm_info), 0);
                int id = sp.getInt("id", 0);

                int[] ids = new int[]{id};
                if (repeatType != null && repeatType.getType() == RepeatType.WEEKDAY) {
                    ids = new int[repeatType.getWeekDays().length];

                    for (int i = 1; i < ids.length; i++) {
                        ids[i] = ++id;
                    }
                }

                SharedPreferences.Editor ed = sp.edit();
                ed.putInt("id", ids[ids.length - 1] + 1);
                ed.apply();

                //isRepeat = repeatText.getText().length() > 1;

                if (isAdd) {
                    saveAlarm = new AlarmData(name, detail, hou, min
                            , isVib, currentRing, repeatType, true, isRepeat, ids);
                    AlarmUtil.saveAlarm(EditActivity.this, saveAlarm);
                } else {
                    saveAlarm = new AlarmData(name, detail, hou, min
                            , isVib, currentRing, repeatType, saveAlarm.isOn(), isRepeat, saveAlarm.getIds());
                    AlarmUtil.updateAlarm(EditActivity.this, saveAlarm);
                    AlarmUtil.windUp(EditActivity.this, saveAlarm);
                }
                setResult(RESULT_OK);
                finish();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(EditActivity.this, RepeatActivity.class)
                        , ConstantValue.repeatRequestCode);
            }
        });

        mTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(EditActivity.this).inflate(R.layout.dialog_tag, null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(EditActivity.this);
                final AlertDialog dialog = builder.setView(dialogView).create();
                dialog.show();
                TextView mid = (TextView) dialogView.findViewById(R.id.dialog_middle);
                mid.setText("标签");
                final EditText tag = (EditText) dialogView.findViewById(R.id.tag_et);
                tag.setText(tagText.getText());
                dialogView.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagText.setText(tag.getText());
                        dialog.dismiss();
                    }
                });
                dialogView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = LayoutInflater.from(EditActivity.this).inflate(R.layout.dialog_content, null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(EditActivity.this);
                final AlertDialog dialog = builder.setView(dialogView).create();
                dialog.show();
                TextView mid = (TextView) dialogView.findViewById(R.id.dialog_middle);
                mid.setText("内容");
                final EditText content = (EditText) dialogView.findViewById(R.id.content_et);
                content.setText(contentText.getText());
                dialogView.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contentText.setText(content.getText());
                        dialog.dismiss();
                    }
                });
                dialogView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        mRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = LayoutInflater.from(EditActivity.this).inflate(R.layout.dialog_ring, null);
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(EditActivity.this);
                final AlertDialog dialog = builder.setView(dialogView).create();
                dialog.show();
                TextView mid = (TextView) dialogView.findViewById(R.id.dialog_middle);
                mid.setText("铃声");

                final Switch vibSwitch = (Switch) dialog.findViewById(R.id.ring_vib);
                vibSwitch.setChecked(isVib);

                final NumberPicker ringPicker = (NumberPicker) dialogView.findViewById(R.id.ring_np);
                ringPicker.setDisplayedValues(sysRings.toArray(new String[sysRings.size()]));
                ringPicker.setMinValue(0);
                ringPicker.setMaxValue(sysRings.size() - 1);
                ringPicker.setValue(currentRing);
                ringPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        //TODO 切换效果
                    }
                });
                EditText editText = findInput(ringPicker);
                assert editText != null;
                editText.setKeyListener(null);
                ringPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                dialogView.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ringText.setText(sysRings.get(ringPicker.getValue()));
                        currentRing = ringPicker.getValue();
                        isVib = vibSwitch.isChecked();
                        dialog.dismiss();
                    }
                });

                dialogView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

    }

    private EditText findInput(ViewGroup np) {
        int count = np.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = np.getChildAt(i);
            if (child instanceof ViewGroup) {
                findInput((ViewGroup) child);
            } else if (child instanceof EditText) {
                return (EditText) child;
            }
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ConstantValue.repeatRequestCode) {
                if (data.getSerializableExtra(ConstantValue.repeatKeyString) != null) {
                    isRepeat = true;
                    repeatType = (RepeatType) data.getSerializableExtra(ConstantValue.repeatKeyString);
                    StringBuilder builder = new StringBuilder();
                    switch (repeatType.getType()) {
                        case RepeatType.EVERYDAY:
                            builder.append("每天");
                            break;
                        case RepeatType.WEEKDAY:
                            WeekDay[] weekDays = repeatType.getWeekDays();
                            for (int i = 0; i < weekDays.length; i++) {
                                builder.append(weekDays[i].toString());
                                builder.append(",");
                            }
                            break;
                        case RepeatType.MONTHDAY:
                            builder.append("每月" + repeatType.getDay() + "日");
                            break;
                        case RepeatType.YEARDAY:
                            builder.append("纪念日").append(repeatType.getMonth()).append(".").append(repeatType.getDay());
                            break;
                        case RepeatType.ONEDAY:
                            builder.append(repeatType.getYear()).append(".").append(repeatType.getMonth()).append(".").append(repeatType.getDay());
                            break;
                        case RepeatType.INTERVALDAY:
                            if (repeatType.getiDay() == 0){
                                builder.append("每隔").append(repeatType.getdInterval()).append("天");
                            }else{
                                builder.append("从明天起每隔").append(repeatType.getdInterval()).append("天");
                            }
                            break;
                        case RepeatType.INTERVALHOUR:
                            if (repeatType.getiDay() == 0){
                                builder.append("每隔").append(repeatType.gethInterval()).append("小时");
                            }else{
                                builder.append("从明天起每隔").append(repeatType.gethInterval()).append("小时");
                            }
                            break;
                        case RepeatType.INTERVALMIN:
                            if (repeatType.getiDay() == 0){
                                builder.append("每隔").append(repeatType.getmInterval()).append("分钟");
                            }else{
                                builder.append("从明天起每隔").append(repeatType.getmInterval()).append("分钟");
                            }
                            break;
                    }
                    repeatText.setText(builder);
                    repeatText.setSelected(true);
                }
            }
        }
    }
}
