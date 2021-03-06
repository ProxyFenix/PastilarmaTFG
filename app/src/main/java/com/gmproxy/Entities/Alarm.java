package com.gmproxy.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * It's your trusty old entity class. It's not much, but it does what it has to do.
 * Some of these you'll see with two constructors. This is due to not having to insert a id since it's autogenerated.
 */
@Entity(tableName = "horario_alarma", foreignKeys = @ForeignKey(entity = Medicine.class,
                                        parentColumns = "id_medicamentos",
                                        childColumns = "id_medicamento"))
public class Alarm implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_alarma")
    int id_alarm;

    @ColumnInfo(name = "id_medicamento")
    int id_medicine_fk;

    @NonNull
    @ColumnInfo(name = "hora")
    String hour;

    public class AlarmMedicine {
         @Embedded public Medicine medicine;
         @Relation(
                 parentColumn = "id_medicine",
                 entityColumn = "id_medicine_fk"
         ) public List<Medicine> medicines;
    }

    @Ignore
    public Alarm(int id_alarm, int id_medicine_fk, String hour) {
        this.id_alarm = id_alarm;
        this.id_medicine_fk = id_medicine_fk;
        this.hour = hour;
    }

    public Alarm(int id_medicine_fk, String hour) {
        this.id_medicine_fk = id_medicine_fk;
        this.hour = hour;
    }

    public int getId_alarm() {
        return id_alarm;
    }

    public void setId_alarm(int id_alarm) {
        this.id_alarm = id_alarm;
    }

    public int getId_medicine_fk() {
        return id_medicine_fk;
    }

    public void setId_medicine_fk(int id_medicine_fk) {
        this.id_medicine_fk = id_medicine_fk;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id_alarm=" + id_alarm +
                ", id_medicine=" + id_medicine_fk +
                ", hour=" + hour +
                '}';
    }
}
