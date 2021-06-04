package com.gmproxy.DAO;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.HourMedicine;
import com.gmproxy.Entities.User;

import java.util.List;

/**
 * This class here is the bridge between an entity in the database, and an entity in the program.
 * Gets the DAO methods, and makes it available for the use.
 */
public class AlarmRepository {
    private AlarmDAO concerningDao;

    public AlarmRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        concerningDao = db.alarmDao();
    }

    public LiveData<List<Alarm>> getAllObjects() {
        return concerningDao.getAllObjects();
    }

    public void insertAllObjects(List<Alarm> objectsList) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertAllObjects(objectsList);
        });
    }

    public void insertAlarm(int i, String j){
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertAlarm(i,j);
        });
    }

    public Alarm getAlarmById(int i) { return concerningDao.getAlarmById(i); }

    public int getAlarmbyTimeAndMedId(String a, int i) { return concerningDao.getAlarmbyTimeAndMedId(a,i); }

    public LiveData<List<HourMedicine>> getRelationObj(int id) { return concerningDao.getRelationObjects(id); }

    public Alarm getAlarmByTime(String time) { return concerningDao.getAlarmbyTime(time); }

    public void insertObject(Alarm obj){ concerningDao.insertAlarm(obj.getId_medicine_fk(), obj.getHour()); }

    public void deleteObject(Alarm obj) {
        concerningDao.delete(obj);
    }
}
