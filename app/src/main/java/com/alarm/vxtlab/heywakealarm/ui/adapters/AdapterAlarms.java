package com.alarm.vxtlab.heywakealarm.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.alarm.vxtlab.heywakealarm.models.Alarm;
import com.alarm.vxtlab.heywakealarm.untils.Constants;
import com.alarm.vxtlab.heywakealarm.R;

import java.util.List;

/**
 * Created by VXT on 10/30/2017.
 */

public class AdapterAlarms extends RecyclerView.Adapter<AdapterAlarms.ViewHolder> {

    private Context context;
    private List<Alarm> alarmModels;

    public AdapterAlarms(Context context, List<Alarm> alarmModels) {
        this.context = context;
        this.alarmModels = alarmModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm,parent,false);

        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Alarm alarm = alarmModels.get(position);

        holder.textViewTime.setText(String.format("%02d : %02d", alarm.getTimeHour(), alarm.getTimeMinute()));
        holder.switchStatus.setChecked(alarm.isEnabled());
        if (alarm.getAlarmMode()== (Constants.DEFAULT)){
            holder.imageViewMode.setImageDrawable((context.getResources().getDrawable(R.drawable.icons8_alarm_clock_64)));
        }
        if (alarm.getAlarmMode() == (Constants.SHAKE)){
            holder.imageViewMode.setImageDrawable((context.getResources().getDrawable(R.drawable.icons8_play_64)));
        }
        if (alarm.getAlarmMode()==(Constants.COUNT )){
            holder.imageViewMode.setImageDrawable((context.getResources().getDrawable(R.drawable.icons8_game_controller_64)));
        }
        if (alarm.getAlarmMode() == (Constants.MEMORY )){
            holder.imageViewMode.setImageDrawable((context.getResources().getDrawable(R.drawable.icons8_game_controller_64)));
        }

        holder.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    holder.textViewRepeat.setTextColor(Color.BLACK);
                }else {


                    holder.textViewRepeat.setTextColor(Color.GRAY);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (alarmModels == null){
            return 0;
        }
        return alarmModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTime;
        public TextView textViewRepeat;
        public Switch switchStatus;
        public ImageView imageViewMode;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewTime = (TextView)itemView.findViewById(R.id.textViewTime);
            textViewRepeat = (TextView)itemView.findViewById(R.id.textViewRepeat);
            imageViewMode = (ImageView)itemView.findViewById(R.id.imageViewMode);
            switchStatus = (Switch)itemView.findViewById(R.id.switchStatus);
        }
    }
}
