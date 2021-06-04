package com.gmproxy.DAO;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Refer to AlarmRepository.
 */
public class PathologyRepository {
    private PathologyDAO concerningDao;
    private LiveData<List<Pathology>> pathologyList;

    public PathologyRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        concerningDao = db.pathologyDao();
        pathologyList = concerningDao.getAllObjects();
    }

    public LiveData<List<Pathology>> getAllObjects() {
        return concerningDao.getAllObjects();
    }

    void insertAllObjects(List<Pathology> objectsList) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertAllObjects(objectsList);
        });
    }

    public Pathology obtainById(int id) { return concerningDao.findObjectById(id); }


    public void insertObject(Pathology obj){
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertObject(obj);
        });
    }


    public LiveData<List<Pathology>> getRelationObj (int id) { return concerningDao.getRelationObj(id); }

    public int getDataCount() { return concerningDao.getDataCount(); }

    public void deleteObject(Pathology obj) {
        concerningDao.delete(obj);
    }

    public LiveData<List<Pathology>> filter(String input){
        try{
            return new FilterNoteAsyncTask(concerningDao).execute(input).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    static class FilterNoteAsyncTask extends AsyncTask<String, Void, LiveData<List<Pathology>>> {
        private PathologyDAO pathologyDAO;

        private FilterNoteAsyncTask(PathologyDAO pathologyDAO) {
            this.pathologyDAO = pathologyDAO;
        }

        @Override
        protected LiveData<List<Pathology>> doInBackground(String... strings) {
            return pathologyDAO.filterText(strings[0]);
        }
    }

}




