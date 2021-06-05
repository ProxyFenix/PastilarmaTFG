package com.gmproxy.DAO;

import android.app.Application;

import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.User;

import java.util.List;

/**
 * Refer to AlarmRepository.
 */
public class AlarmUserRepository {
    private AlarmUserDAO concerningDao;

    public AlarmUserRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        concerningDao = db.alarmUserDao();
    }

    public List<AlarmUser> getAllObjects() {
        return concerningDao.getAllObjects();
    }

    public void insertAllObjects(List<AlarmUser> objectsList) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertAllObjects(objectsList);
        });
    }

    public void insertObject(AlarmUser obj) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertObject(obj);
        });
    }

    public void deleteAllFromUser(int i) { concerningDao.deleteAllFromUser(i);}

    public AlarmUser isThereAnyBodyHome(int id) { return concerningDao.getAnyRecord(id); }

    public AlarmUser findObjectViaId(int i,int j) { return concerningDao.getViaUserAlarmId(i,j); }

    public AlarmUser findClassById(int id){ return concerningDao.findClassbyAlarmId(id); }

    public void insertObjectById(int i, int j) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertIntoAlarmUser(i,j);
        });
    }

    public void deleteObject(AlarmUser obj) {
        concerningDao.delete(obj);
    }
}
