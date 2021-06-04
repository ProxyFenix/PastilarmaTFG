package com.gmproxy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.OnConflictStrategy;
import androidx.room.*;

import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

/**
 * Refer to AlarmDAO.
 */
@Dao
public interface PathologyUserDAO {

    @Query("SELECT * FROM usuarios_condiciones")
    List<PathologyUser> getAllObjects();

    @Query("SELECT * FROM usuarios_condiciones WHERE id_usuarios_condiciones = :id")
    PathologyUser findObjectbyId(int id);

    @Query("INSERT INTO usuarios_condiciones (id_usuario,id_condiciones) VALUES (:id_user,:id_conditions)")
    void insertIntoPathUser(int id_user, int id_conditions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllObjects(List<PathologyUser> listObjects);

    @Query("SELECT * FROM usuarios_condiciones WHERE id_usuario = :i")
    LiveData<List<PathologyUser>> getAllObjectsFromUser(int i);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertObject(PathologyUser object);

    @Query("SELECT * FROM usuarios_condiciones INNER JOIN usuarios ON usuarios_condiciones.id_usuario=usuarios.id_usuario " +
            "INNER JOIN condiciones ON usuarios_condiciones.id_condiciones=condiciones.id_condiciones " +
            "WHERE usuarios.id_usuario=:id LIMIT 1")
    PathologyUser getAnyRecord(int id);

    @Query("SELECT * FROM usuarios_condiciones WHERE id_usuario = :idU AND id_condiciones = :idA")
    PathologyUser getViaUserAlarmId(int idU,int idA);

    @Update
    void updateObject(PathologyUser object);

    @Delete
    void delete(PathologyUser obj);

    @Query("DELETE FROM usuarios_condiciones WHERE id_usuario=:id")
    void deleteAllFromUser(int id);
}
