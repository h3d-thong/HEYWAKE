package com.alarm.vxtlab.heywakealarm.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alarm.vxtlab.heywakealarm.R;
import com.alarm.vxtlab.heywakealarm.models.Alarm;
import com.alarm.vxtlab.heywakealarm.ui.adapters.ArrayAdapterWithIcon;
import com.alarm.vxtlab.heywakealarm.untils.helpers.AlarmManagerHelper;
import com.alarm.vxtlab.heywakealarm.untils.helpers.DBHelper;

import java.util.ArrayList;

public class AddAlarmActivity extends AppCompatActivity {

    private Alarm alarmDetails;

    private ConstraintLayout constraintLayoutAlarmMode;
    private ConstraintLayout constraintLayoutSelectTune;
    private ConstraintLayout constraintLayoutRepeate;
    private ConstraintLayout constraintLayoutTitle;
    private TextView textViewTuntTitle;
    private TimePicker timePickerAddAlarm;
    private TextView textViewDate;
    private String mTitle = "";


    private ImageView imageViewAlarmMode;
    private TextView textViewAlarmMode;
    private Button buttonSave;
    private Button buttonCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        //Trong trường hợp chỉ tạo mới
        alarmDetails = new Alarm();
        alarmDetails.setAlarmMode(0);
        bindView();
    }

    private void bindView() {

        constraintLayoutAlarmMode = (ConstraintLayout) findViewById(R.id.constraintLayoutAlarmMode);
        constraintLayoutSelectTune = (ConstraintLayout) findViewById(R.id.constraintLayoutSelectTune);
        constraintLayoutRepeate = (ConstraintLayout) findViewById(R.id.constraintLayoutRepeate);
        constraintLayoutTitle = (ConstraintLayout) findViewById(R.id.constraintLayoutTitle);

        timePickerAddAlarm = (TimePicker)findViewById(R.id.timePickerAddAlarm);

        textViewDate = (TextView)findViewById(R.id.textViewDate);
        textViewTuntTitle = (TextView)findViewById(R.id.textViewTuneTitle);

        textViewAlarmMode = (TextView)findViewById(R.id.textViewAlarmMode);
        imageViewAlarmMode = (ImageView)findViewById(R.id.imageViewAlarmMode);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonCancle = (Button)findViewById(R.id.buttonCancle);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAlarm();
                DBHelper.copyOrUpdateAlarm(alarmDetails);
                AlarmManagerHelper.setAlarm(AddAlarmActivity.this);

                onBackPressed();
            }
        });

        constraintLayoutAlarmMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createDialogChooseStyle();
            }
        });

        constraintLayoutRepeate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
        constraintLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTitleDialog();
            }
        });
        constraintLayoutSelectTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.chooseTune));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, 5);
            }
        });
    }

    private void createDialog(){
        final CharSequence[] items = {" Thứ 2 "," Thứ 3 "," Thứ 4 "," Thứ 5 ", " Thứ 6 ", " Thứ 7 ", " Chủ nhật "};

        int selected [] = {1,3};
        final ArrayList seletedItems=new ArrayList();
        String title = getString(R.string.repeate_title);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(indexSelected);

                        } else if (seletedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String s = "";
                        int n = seletedItems.size();
                        for (int i = 0; i < n ; i++){
                            s = s+ "Th"+ seletedItems.get(i)+" ";
                            textViewDate.setText(s);
                        }

                    }
                }).setNegativeButton(getString(R.string.cancle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }
    private void createDialogChooseStyle(){
        final String [] items = new String[] {getString(R.string.default_ring_tone),getString(R.string.shake),
        getString(R.string.task_memory),getString(R.string.count_number)};
        final Integer[] icons = new Integer[] {R.drawable.ic_alarm_black_24dp, R.drawable.ic_vibration_black_24dp,
        R.drawable.ic_videogame_asset_black_24dp,R.drawable.ic_videogame_asset_black_24dp};

        ListAdapter adapter = new ArrayAdapterWithIcon(AddAlarmActivity.this, items, icons);
        new AlertDialog.Builder(AddAlarmActivity.this).setTitle("Select Image")
                .setTitle(getString(R.string.choose_mode))
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item ) {
                        if (items[item].equals(getString(R.string.shake))){
                            createChooseNumberDialog();

                        }
                        alarmDetails.setAlarmMode(item);
                        imageViewAlarmMode.setImageDrawable((getResources().getDrawable(icons[item])));
                        textViewAlarmMode.setText(items[item]);
                    }
                }).show();


    }
    private void createChooseNumberDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_shake_number,null);

        final String[] displayedValues = {"30","35","40","45","50","25","20"};
        final NumberPicker numberPickerCount = (NumberPicker)dialogView.findViewById(R.id.numberPickerCount);
        Button buttonOk = (Button)dialogView.findViewById(R.id.buttonOk);
        numberPickerCount.setMinValue(0);
        numberPickerCount.setMaxValue(displayedValues.length-1);
        numberPickerCount.setDisplayedValues(displayedValues);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(dialogView);
        final AlertDialog chooseModeDialog = alert.create();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alarmDetails.setNumOfRepeate(Integer.parseInt(displayedValues[numberPickerCount.getValue()]));
                chooseModeDialog.dismiss();

            }
        });
        chooseModeDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        chooseModeDialog.show();
    }
    private void createTitleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhãn thông báo");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTitle = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateAlarm(){
        alarmDetails.setTimeMinute(timePickerAddAlarm.getCurrentMinute().intValue());
        alarmDetails.setTimeHour( timePickerAddAlarm.getCurrentHour().intValue());
        alarmDetails.setAlarmName(mTitle);
        alarmDetails.setEnabled(true);
    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null)
            {
                alarmDetails.setAlarmTone(uri.toString());

                Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
                String title = ringtone.getTitle(this);
                textViewTuntTitle.setText(title);
            }
            else
            {
                Toast.makeText(this,"Không nhận được âm thanh",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
