package com.gmproxy.DAO;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.Pathology;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Refer to AlarmRepository.
 */
public class MedicineRepository {
    private MedicineDAO concerningDao;
    private LiveData<List<Medicine>> medicineList;

    public MedicineRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        concerningDao = db.medicineDao();
        medicineList = concerningDao.getAllObjects();
    }

    public LiveData<List<Medicine>> getAllObjects() {
        return concerningDao.getAllObjects();
    }

    void insertAllObjects(List<Medicine> objectsList) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertAllObjects(objectsList);
        });
    }

    public void insertObject(Medicine obj) {
        DatabaseHelper.databaseWriteExecutor.execute(() ->{
            concerningDao.insertObject(obj);
        });
    }

    public Medicine findObjectById(int id) { return concerningDao.findObjectbyId(id); }

    public Medicine findObjectByName(String id) { return concerningDao.findObjectbyName(id); }

    void deleteObject(Medicine obj) {
        concerningDao.delete(obj);
    }

    public LiveData<List<Medicine>> filter(String input){
        try{
            return new FilterNoteAsyncTaskB(concerningDao).execute(input).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class FilterNoteAsyncTaskB extends AsyncTask<String, Void, LiveData<List<Medicine>>> {
        private MedicineDAO medicineDAO;

        private FilterNoteAsyncTaskB(MedicineDAO medicineDAO) {
            this.medicineDAO = medicineDAO;
        }

        @Override
        protected LiveData<List<Medicine>> doInBackground(String... inputs) {
            return medicineDAO.filterText(inputs[0]);
        }
    }
}
