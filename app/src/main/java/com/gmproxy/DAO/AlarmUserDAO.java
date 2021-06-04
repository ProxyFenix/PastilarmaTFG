package com.gmproxy.DAO;

import androidx.room.*;

import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;

import java.util.List;

/**
 * Refer to AlarmDAO.
 */
@Dao
public interface AlarmUserDAO {
    @Query("SELECT * FROM alarmas_usuarios")
    List<AlarmUser> getAllObjects();

    @Query("SELECT * FROM alarmas_usuarios WHERE id_alarma_usuario = :id")
    AlarmUser findObjectbyId(int id);

    @Query("SELECT * FROM alarmas_usuarios WHERE id_alarma = :id")
    AlarmUser findClassbyAlarmId(int id);

    @Query("INSERT INTO alarmas_usuarios (id_usuario,id_alarma) VALUES (:id_alarm,:id_user)")
    void insertIntoAlarmUser(int id_alarm, int id_user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllObjects(List<AlarmUser> listObjects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertObject(AlarmUser object);

    @Query("SELECT * FROM alarmas_usuarios WHERE id_usuario = :idU AND id_alarma = :idA")
    AlarmUser getViaUserAlarmId(int idU, int idA);

    @Query("SELECT * FROM alarmas_usuarios INNER JOIN horario_alarma on horario_alarma.id_alarma=alarmas_usuarios.id_alarma " +
            "INNER JOIN medicamentos on horario_alarma.id_medicamento=medicamentos.id_medicamentos  " +
            "INNER JOIN usuarios on alarmas_usuarios.id_usuario=usuarios.id_usuario WHERE usuarios.id_usuario = :id LIMIT 1")
    AlarmUser getAnyRecord(int id);

    @Update
    void updateObject(AlarmUser object);

    @Delete
    void delete(AlarmUser obj);

    @Query("DELETE FROM alarmas_usuarios WHERE id_usuario=:id")
    void deleteAllFromUser(int id);
}
