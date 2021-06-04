package com.gmproxy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.HourMedicine;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;

import java.util.List;

/**
 * Not much here, it's a DAO. We all know how DAOs work, just register the queries into usable methods, then pass it to the
 * repositories so that the view models and the other stuff can actuall use said queries.
 * The queries themselves are pretty self explanatory.
 */
@Dao
public interface AlarmDAO {
    @Query("SELECT * FROM horario_alarma")
    LiveData<List<Alarm>> getAllObjects();

    //Create new alarm
    @Query("INSERT INTO horario_alarma (id_medicamento,hora) VALUES (:id_medicamentos,:hora)")
    void insertAlarm(int id_medicamentos, String hora);

    //This is for adding it to the many-to-many table, first I hafta get the id
    @Query("SELECT * FROM horario_alarma WHERE id_alarma LIKE :id_alarm")
    Alarm getAlarmById(int id_alarm);

    @Query("SELECT * FROM horario_alarma WHERE hora LIKE :time")
    Alarm getAlarmbyTime(String time);

    @Query("SELECT id_alarma FROM horario_alarma WHERE hora LIKE :time and id_medicamento=:med")
    int getAlarmbyTimeAndMedId(String time,int med);

    @Query("SELECT * FROM horario_alarma WHERE id_alarma = :id")
    Alarm findObjectbyId(int id);

    @Query("SELECT horario_alarma.hora,medicamentos.nombre FROM horario_alarma INNER JOIN medicamentos on horario_alarma.id_medicamento=medicamentos.id_medicamentos " +
            "INNER JOIN alarmas_usuarios on horario_alarma.id_alarma=alarmas_usuarios.id_alarma " +
            "INNER JOIN usuarios on alarmas_usuarios.id_usuario=usuarios.id_usuario WHERE usuarios.id_usuario = :id")
    LiveData<List<HourMedicine>> getRelationObjects(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllObjects(List<Alarm> listObjects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertObject(Alarm object);

    @Update
    void updateObject(Alarm object);

    @Delete
    void delete(Alarm obj);
}
