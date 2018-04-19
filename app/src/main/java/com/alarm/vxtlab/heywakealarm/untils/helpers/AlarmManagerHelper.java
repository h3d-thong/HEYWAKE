package com.alarm.vxtlab.heywakealarm.untils.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alarm.vxtlab.heywakealarm.models.Alarm;
import com.alarm.vxtlab.heywakealarm.recievers.AlarmReciever;

import java.util.Calendar;
import java.util.List;

/**
 * Created by VXT on 1/27/2018.
 */

public class AlarmManagerHelper {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TIME_HOUR = "timeHour";
    public static final String TIME_MINUTE = "timeMinute";
    public static final String TONE = "alarmTone";
    public static final String MODE = "mode";
    public static final String NUM_REPEATE = "numrepeate";

    String TAG = this.getClass().getSimpleName();

    public  static  void setAlarm(Context context){

        List<Alarm> alarmList = DBHelper.getAllAlarms();
        cancelAlarms(context,alarmList);
        Log.d("SetAlarm",alarmList.size()+"");
        for (Alarm alarm:alarmList){
            if (alarm.isEnabled() == true){
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarm.getTimeHour());
                calendar.set(Calendar.MINUTE, alarm.getTimeMinute());
                calendar.set(Calendar.SECOND, 00);


                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                boolean alarmSet = false;

                String[] repeateDay = alarm.getRepeatingDays().split(" ");

                if ((alarm.getTimeHour() > nowHour) | ((alarm.getTimeHour() == nowHour) && (alarm.getTimeMinute() > nowMinute))){
                    calendar.set(Calendar.DAY_OF_WEEK, nowDay);
                    Log.d("Alarm",alarm.getTimeHour()+":"+alarm.getTimeMinute());
                    setOneAlarm(context,calendar,alarm);
                    alarmSet = true;
                    Log.d("Alarm","Tao 1 alarm");
                }else {
                    Log.d("time", "vao day");
                    for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++ dayOfWeek) {

                        if ( repeateDay[dayOfWeek -1 ].equals("1") && dayOfWeek >= nowDay && !(dayOfWeek == nowDay && alarm.getTimeHour() < nowHour)
                                && !(dayOfWeek == nowDay && alarm.getTimeHour() == nowHour && alarm.getTimeMinute() <= nowMinute)) {
                            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                            setOneAlarm(context, calendar,alarm);
                            alarmSet = true;
                            break;
                        }
                    }
                }

                if (!alarmSet) {
                    for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++ dayOfWeek) {
                        if (repeateDay[dayOfWeek -1 ].equals("1") && dayOfWeek <= nowDay && alarm.isRepeatWeekly()) {
                            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                            calendar.add(Calendar.WEEK_OF_YEAR, 1);
                            setOneAlarm(context, calendar,alarm);
                            break;
                        }
                    }
                }
            }
        }

    }

    public static void setOneAlarm(Context context, Calendar calendar,Alarm alarm){
        Intent mIntent = new Intent(context,AlarmReciever.class);
        mIntent.putExtra(ID, alarm.getId());
        mIntent.putExtra(NAME, alarm.getAlarmMode());
        mIntent.putExtra(TIME_HOUR, alarm.getTimeHour());
        mIntent.putExtra(TIME_MINUTE, alarm.getTimeMinute());
        mIntent.putExtra(TONE, alarm.getAlarmTone());
        mIntent.putExtra(NUM_REPEATE, alarm.getNumOfRepeate());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,(int)alarm.getId(),mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
    }
    public static void cancelAlarms(Context context, List<Alarm> alarms){
        if (alarms != null)
        {
            for (Alarm alarm:alarms) {
                if (alarm.isEnabled()) {

                    PendingIntent pendingIntent = createPendingIntent(context,alarm);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
    }
    private static PendingIntent createPendingIntent(Context context, Alarm model) {
        Intent intent = new Intent(context, AlarmReciever.class);

        intent.putExtra(ID, model.getId());
        intent.putExtra(NAME, model.getAlarmMode());
        intent.putExtra(TIME_HOUR, model.getTimeHour());
        intent.putExtra(TIME_MINUTE, model.getTimeMinute());
        intent.putExtra(TONE, model.getAlarmTone());
        return PendingIntent.getService(context, (int) model.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
