package com.example.yang.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.yang.myapplication.ConstantValue;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.data.AlarmData;
import com.example.yang.myapplication.data.RepeatType;
import com.example.yang.myapplication.utils.AlarmUtil;

public class EditActivity extends Activity {

    TimePicker mTimePicker;
    RelativeLayout mRepeat;
    RelativeLayout mTag;
    RelativeLayout mContent;
    RelativeLayout mRing;

    TextView repeatText;
    TextView tagText;
    TextView contentText;
    TextView ringText;

    TextView cancelTv;
    TextView saveTv;

    Button mDelete;

    private RepeatType repeatType = new RepeatType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_edit);
        initViews();
    }

    private void initViews() {
        cancelTv = (TextView) findViewById(R.id.title_left_tv);
        TextView middleTv = (TextView) findViewById(R.id.title_middle_tv);
        saveTv = (TextView) findViewById(R.id.title_right_tv);
        saveTv.setVisibility(View.VISIBLE);
        findViewById(R.id.title_right_iv).setVisibility(View.GONE);

        cancelTv.setText("取消");
        middleTv.setText("添加/编辑");  //TODO 判断
        saveTv.setText("存储");

        mTimePicker = (TimePicker) findViewById(R.id.edit_time_picker);
        mTimePicker.setIs24HourView(true);
        mRepeat = (RelativeLayout) findViewById(R.id.rl_repeat);
        mTag = (RelativeLayout) findViewById(R.id.rl_tag);
        mContent = (RelativeLayout) findViewById(R.id.rl_content);
        mRing = (RelativeLayout) findViewById(R.id.rl_ring);
        mDelete = (Button) findViewById(R.id.edit_delete);

        repeatText = (TextView) findViewById(R.id.tv_repeat);
        tagText = (TextView) findViewById(R.id.tv_tag);
        contentText = (TextView) findViewById(R.id.tv_content);
        ringText = (TextView) findViewById(R.id.tv_ring);


        initClick();

    }

    private void initClick() {

        //TODO delete 判断

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
                String detail = (String) contentText.getText();
                String ring = (String) ringText.getText();


                SharedPreferences sp = EditActivity.this.getSharedPreferences(EditActivity.this.getString(R.string.alarm_info), 0);
                int id = sp.getInt("id", 0);

                int[] ids = new int[]{id};
                if (repeatType.getType() == RepeatType.WEEKDAY) {
                    ids = new int[repeatType.getWeekDays().length];

                    for (int i = 1; i < ids.length; i++) {
                        ids[i] = ++id;
                    }
                }

                SharedPreferences.Editor ed = sp.edit();
                ed.putInt("id", ids[ids.length - 1] + 1);
                ed.commit();

                AlarmData saveAlarm = new AlarmData(name, detail, hou, min
                        , false, ring, repeatType, true, true, ids);
                AlarmUtil.saveAlarm(EditActivity.this, saveAlarm);
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
                startActivityForResult(new Intent(EditActivity.this, CustomUserDefinedActivity.class)
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ConstantValue.repeatRequestCode) {
                if (data.getSerializableExtra(ConstantValue.repeatKeyString) instanceof RepeatType) {
                    repeatType = (RepeatType) data.getSerializableExtra(ConstantValue.repeatKeyString);
                }
            }
        }
    }
}
