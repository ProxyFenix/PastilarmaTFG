package com.gmproxy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.User;

import java.util.List;

/**
 * Refer to AlarmDAO.
 */
@Dao
public interface MedicineDAO {
    @Query("SELECT * FROM medicamentos")
    LiveData<List<Medicine>> getAllObjects();

    @Query("SELECT * FROM medicamentos WHERE id_medicamentos = :id")
    Medicine findObjectbyId(int id);

    @Query("SELECT * FROM medicamentos WHERE nombre LIKE :id")
    Medicine findObjectbyName(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllObjects(List<Medicine> listObjects);

    @Query("SELECT * FROM medicamentos WHERE nombre LIKE :filter || '%'")
    LiveData<List<Medicine>> filterText(String filter);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertObject(Medicine object);

    @Update
    void updateObject(Medicine object);

    @Delete
    void delete(Medicine obj);
}
