package com.alarm.vxtlab.heywakealarm.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.alarm.vxtlab.heywakealarm.R;

/**
 * Created by VXT on 2/16/2018.
 */

public class PlayAlarmService extends Service {
    private WindowManager windowManager;
    private RelativeLayout linearLayout;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;

        params.x = 0;
        params.y = 0;
        linearLayout = (RelativeLayout) inflater.inflate(R.layout.screen_alarm_play,null);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(),PlayAlarmService.class));
            }
        });
       /* windowManager.addView(linearLayout,params);*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if (chatheadView != null) windowManager.removeView(chatheadView);*/
    }
}
