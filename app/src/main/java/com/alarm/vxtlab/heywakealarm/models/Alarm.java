package com.alarm.vxtlab.heywakealarm.models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by VXT on 1/26/2018.
 */

public class Alarm  extends RealmObject implements Serializable{
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRDIAY = 5;
    public static final int SATURDAY = 6;

    @PrimaryKey
    private long id;
    private int timeHour;
    private int timeMinute;
    private String repeatingDays = "0 0 0 0 0 0 0";
    private boolean  repeatWeekly;
    private String alarmTone;
    private String alarmName;
    private boolean isEnabled;
    private int alarmMode; // way to turn off
    private int numOfRepeate;

    public Alarm() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public String getRepeatingDays() {
        return repeatingDays;
    }

    public void setRepeatingDays(String repeatingDays) {
        this.repeatingDays = repeatingDays;
    }

    public boolean isRepeatWeekly() {
        return repeatWeekly;
    }

    public void setRepeatWeekly(boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }

    public String getAlarmTone() {
        return alarmTone;
    }

    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(int larmMode) {
        this.alarmMode = alarmMode;
    }

    public int getNumOfRepeate() {
        return numOfRepeate;
    }

    public void setNumOfRepeate(int numOfRepeate) {
        this.numOfRepeate = numOfRepeate;
    }
}
