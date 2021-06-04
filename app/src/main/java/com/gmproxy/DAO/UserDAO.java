package com.gmproxy.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;
import java.util.List;

/**
 * Refer to AlarmDAO.
 */
@Dao
public interface UserDAO {

    @Query("SELECT * FROM usuarios")
    LiveData<List<User>> getAllObjects();

    @Query("SELECT * FROM usuarios WHERE id_usuario = :id")
    User findObjectbyId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllObjects(List<User> listObjects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertObject(User object);

    @Query("SELECT nombre from usuarios where id_usuario like :id_user")
    String getName(int id_user);

    //This will add an user into the database, hopefully, but without the observation, that will be added later
    @Query("INSERT INTO usuarios (nombre,apellido,edad,numHabitacion,genero,observaciones,imagen)" +
            " VALUES (:name,:surname,:age,:roomNumber,:gender,:observations,:image)")
    void insertUser(String name, String surname, String age, String roomNumber, String gender, String observations, byte[] image);

    @Query("UPDATE usuarios SET observaciones = :observacionesText WHERE id_usuario=:id_user")
    void getCondiciones(String observacionesText, int id_user);

    //This is for adding it to the many-to-many table, first I hafta get the id
    @Query("SELECT id_usuario FROM usuarios WHERE nombre LIKE :userName AND apellido LIKE :surname")
    int getUserId(String userName, String surname);

    @Query("SELECT * FROM usuarios WHERE nombre LIKE :filter || '%'")
    LiveData<List<User>> filterText(String filter);

    @Delete
    void delete(User obj);

    @Update
    void updateObject(User object);

    @Query("UPDATE usuarios SET observaciones = :obs WHERE id_usuario LIKE :id")
    void updateObservation(int id, String obs);

}
