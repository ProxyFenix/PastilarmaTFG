package com.gmproxy.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;
import com.gmproxy.Util.Constants;
import com.gmproxy.Util.ImageConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is your standard database connection helper.
 * There really isn't that much to explain here, it's based 99% from Android's dev page.
 */
@Database(entities = {Alarm.class,
        AlarmUser.class,
        Medicine.class,
        User.class,
        Pathology.class,
        PathologyUser.class},version = 1,
        exportSchema = true)
@TypeConverters({ImageConverter.class})
public abstract class DatabaseHelper extends RoomDatabase {

    public static final String DBNAME = Constants.DBNAME;

    public abstract AlarmDAO alarmDao();
    public abstract AlarmUserDAO alarmUserDao();
    public abstract MedicineDAO medicineDao();
    public abstract PathologyDAO pathologyDao();
    public abstract PathologyUserDAO pathologyUserDao();
    public abstract UserDAO userDao();

    private static volatile DatabaseHelper INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DatabaseHelper getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseHelper.class, Constants.DBNAME)
                            .allowMainThreadQueries()
                            .createFromAsset("database/pastilarmaSQLite.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}