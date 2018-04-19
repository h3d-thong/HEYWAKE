package com.alarm.vxtlab.heywakealarm.untils.helpers;

import com.alarm.vxtlab.heywakealarm.models.Alarm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by VXT on 1/26/2018.
 */

public class DBHelper {


    private static Realm realm = Realm.getDefaultInstance();
    public static List<Alarm> getAllAlarms(){
        RealmResults<Alarm> results = realm.where(Alarm.class).findAll();
        return results;
    }
    public static void deleteAlarm(final long id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Alarm> result = realm.where(Alarm.class).equalTo("id",id).findAll();
                result.deleteAllFromRealm();
            }
        });
    }
    public static void copyOrUpdateAlarm(Alarm alarm){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(alarm);
        realm.commitTransaction();
    }
    public static Alarm getAlarm(long id){
        Alarm alarm = realm.where(Alarm.class).equalTo("id",id).findFirst();
        return alarm;
    }

}
