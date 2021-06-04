package com.gmproxy.DAO;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;

import java.util.List;

/**
 * Refer to AlarmRepository.
 */
public class PathologyUserRepository {
    private PathologyUserDAO concerningDao;

    public PathologyUserRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        concerningDao = db.pathologyUserDao();
    }

    public List<PathologyUser> getAllObjects() {
        return concerningDao.getAllObjects();
    }

    public LiveData<List<PathologyUser>> getAllFromUser(int i) { return concerningDao.getAllObjectsFromUser(i); }

    public PathologyUser getAny(int id) { return concerningDao.getAnyRecord(id); }

    void insertAllObjects(List<PathologyUser> objectsList) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertAllObjects(objectsList);
        });
    }

    public void deleteAllFromUser(int i) { concerningDao.deleteAllFromUser(i);}

    public void insertObject(int i,int j) { concerningDao.insertIntoPathUser(i,j); }

    public PathologyUser getObj(int i,int j) { return concerningDao.getViaUserAlarmId(i,j); }


    public void deleteObject(PathologyUser obj) {
        concerningDao.delete(obj);
    }
}
