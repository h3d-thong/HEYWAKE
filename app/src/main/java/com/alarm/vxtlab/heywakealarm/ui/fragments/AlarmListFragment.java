package com.alarm.vxtlab.heywakealarm.ui.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alarm.vxtlab.heywakealarm.R;
import com.alarm.vxtlab.heywakealarm.models.Alarm;
import com.alarm.vxtlab.heywakealarm.ui.activities.AddAlarmActivity;
import com.alarm.vxtlab.heywakealarm.ui.adapters.AdapterAlarms;
import com.alarm.vxtlab.heywakealarm.untils.helpers.DBHelper;

import java.util.List;


/**
 * Created by VXT on 1/27/2018.
 */

public class AlarmListFragment extends Fragment {

    private FloatingActionButton floatButton;
    private Context context;


    private RecyclerView recyclerViewAlarms;
    private AdapterAlarms adapterAlarms;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_alarm_list,container, false);
        context = fragment.getContext();
        bindviews(fragment);
        return fragment;
    }

    private void bindviews(View view) {

        List<Alarm> alarms = DBHelper.getAllAlarms();
        for (Alarm alarm : alarms){
            Log.d("AlarmMode ", alarm.getAlarmMode()+"");
            Log.d("AlarmTime", alarm.getTimeHour() +"");
            Log.d("Alarm..", alarm.isEnabled() +"");
        }

        recyclerViewAlarms = (RecyclerView)view.findViewById(R.id.recyclerViewAlarms);
        adapterAlarms = new AdapterAlarms(getContext(), DBHelper.getAllAlarms());
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAlarms.setAdapter(adapterAlarms);

        floatButton = view.findViewById(R.id.fab);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAlarmActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(context,
                        R.anim.slide_in_up,R.anim.stay).toBundle();
                context.startActivity(intent,bndlanimation);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        adapterAlarms = new AdapterAlarms(getContext(), DBHelper.getAllAlarms());
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAlarms.setAdapter(adapterAlarms);
        /*if (DBHelper.getAllAlarms() != null){
            linearLayoutRemind.setVisibility(View.GONE);
            linearLayoutQoutes.setVisibility(View.VISIBLE);
        }*/
    }
}
