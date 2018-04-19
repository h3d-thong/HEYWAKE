package com.alarm.vxtlab.heywakealarm.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alarm.vxtlab.heywakealarm.R;
import com.alarm.vxtlab.heywakealarm.services.PlayAlarmService;
import com.alarm.vxtlab.heywakealarm.ui.fragments.AlarmListFragment;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;
    int content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
       // startService(new Intent(getApplicationContext(), PlayAlarmService.class));

        content = R.id.content;
        fragmentManager.beginTransaction().replace(content,new AlarmListFragment()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(content,new AlarmListFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
                case R.id.navigation_settings:

                    return true;
            }
            return false;
        }

    };

}
