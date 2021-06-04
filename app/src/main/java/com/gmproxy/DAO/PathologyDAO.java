package com.gmproxy.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.User;

import java.util.List;


/**
 * Refer to AlarmDAO.
 */
@Dao
public interface PathologyDAO {
    @Query("SELECT * FROM condiciones")
    LiveData<List<Pathology>> getAllObjects();

    //This will come in handy for getting all those pathologies, will need to get them on a for loop since I'm not completely sure
    //that the query will handle int[]
    @Query("SELECT id_condiciones FROM condiciones WHERE id_condiciones LIKE :id_condiciones")
    int getPathologiesForUser(int id_condiciones);

    @Query("SELECT nombreCondicion FROM condiciones WHERE nombreCondicion LIKE :pathologyName")
    String getPathologiesForName(String pathologyName);

    @Query("SELECT * FROM condiciones WHERE nombreCondicion LIKE :pathologyName")
    Pathology getPathologiesCompleteForName(String pathologyName);

    @Query("SELECT * FROM condiciones WHERE id_condiciones=:id")
    Pathology findObjectById(int id);

    @Query("SELECT COUNT(id_condiciones) FROM condiciones")
    int getDataCount();

    @Query("SELECT * FROM condiciones WHERE nombreCondicion LIKE :filter || '%'")
    LiveData<List<Pathology>> filterText(String filter);

    @Query("SELECT * FROM condiciones INNER JOIN usuarios_condiciones ON condiciones.id_condiciones=usuarios_condiciones.id_condiciones " +
            "INNER JOIN usuarios ON usuarios_condiciones.id_usuario=usuarios.id_usuario WHERE usuarios.id_usuario=:id")
    LiveData<List<Pathology>> getRelationObj(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllObjects(List<Pathology> listObjects);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertObject(Pathology object);

    @Update
    void updateObject(Pathology object);

    @Delete
    void delete(Pathology obj);


}
