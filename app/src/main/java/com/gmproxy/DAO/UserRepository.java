package com.gmproxy.DAO;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Refer to AlarmRepository.
 */
public class UserRepository {
    private UserDAO concerningDao;
    private LiveData<List<User>> userList;

    public UserRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        concerningDao = db.userDao();
        userList = concerningDao.getAllObjects();
    }

    public LiveData<List<User>> getAllObjects() {
        return userList;
    }

    public void insertAllObjects(List<User> objectsList) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertAllObjects(objectsList);
        });
    }

    public User findObjectById(int id) { return concerningDao.findObjectbyId(id); };

    public void insertObject(User obj) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertObject(obj);
        });
    }

    public LiveData<List<User>> filter(String input){
        try{
            return new UserRepository.FilterNoteAsyncTask(concerningDao).execute(input).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateObservation (int id, String obs) { concerningDao.updateObservation(id, obs); }

    public int getUserIdByNameAndSurname (String a, String b) { return concerningDao.getUserId(a,b); }

    public void deleteObject(User obj) {
        concerningDao.delete(obj);
    }

    public void update(User obj) { concerningDao.updateObject(obj); }

    private static class FilterNoteAsyncTask extends AsyncTask<String, Void, LiveData<List<User>>> {
        private UserDAO userDAO;

        private FilterNoteAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected LiveData<List<User>> doInBackground(String... strings) {
            return userDAO.filterText(strings[0]);
        }
    }
}
