package com.alarm.vxtlab.heywakealarm.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alarm.vxtlab.heywakealarm.ui.activities.MainActivity;
import com.alarm.vxtlab.heywakealarm.untils.helpers.AlarmManagerHelper;

/**
 * Created by VXT on 1/27/2018.
 */

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String mode = intent.getExtras().getString(AlarmManagerHelper.MODE);

        Intent alarmIntent = new Intent(context, MainActivity.class);
        alarmIntent.putExtras(intent);
        context.startActivity(alarmIntent);
    }
}
