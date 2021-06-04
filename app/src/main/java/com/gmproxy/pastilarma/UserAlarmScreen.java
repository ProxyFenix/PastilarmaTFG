package com.gmproxy.pastilarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gmproxy.DAO.AlarmRepository;
import com.gmproxy.DAO.AlarmUserRepository;
import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.User;
import com.gmproxy.Alarm.UtilAlarma;
import com.gmproxy.ViewModels.AlarmViewModel;
import com.gmproxy.ViewModels.MedicineViewModel;
import com.gmproxy.ViewModels.UserViewModel;

import java.util.Calendar;

public class UserAlarmScreen extends AppCompatActivity {

    TextView usernameAlarm,numHabText,medicineText,notificationsTime;
    EditText medicineQuantityText;
    Button gotoBackBtn,gotoMedicinesBtn,confirmAlarmBtn;

    int alarmID = 1;
    int idM,idU;

    SharedPreferences settings;
    User user;
    Medicine medicine;
    Alarm alarm;

    AlarmUserRepository alUsRe;
    AlarmRepository alRe;
    AlarmViewModel alarmViewModel;
    UserViewModel userViewModel;
    MedicineViewModel medicineViewModel;

    String medicineQuantity;
    int med_record;

    Calendar today;
    String message;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_alarm_screen);
        context = this.getApplicationContext();

        idU = getIntent().getIntExtra("id_userA",0);
        Log.println(Log.INFO,"id",String.valueOf(idU));
        idM = getIntent().getIntExtra("med",0);
        Log.println(Log.INFO,"Id medicina",String.valueOf(idM));
        med_record = getIntent().getIntExtra("med-record",0);

        /*
        If we come back from medicine search, we actually reload everything that was writter before, so that the user doesn't have to rewrite it
         */
        if (med_record == 1){
            loadPreferences();
        }

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(UserViewModel.class);
        alarmViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AlarmViewModel.class);
        medicineViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MedicineViewModel.class);
        alUsRe = new AlarmUserRepository(this.getApplication());
        alRe = new AlarmRepository(this.getApplication());


        user = userViewModel.getObjectById(idU);
        medicine = medicineViewModel.getMedicineById(idM);

        usernameAlarm = findViewById(R.id.UsernameAlarm);
        usernameAlarm.setText(user.getUser_name() + " " + user.getUser_surname());
        numHabText = findViewById(R.id.NumHabText);
        numHabText.setText(String.valueOf(user.getRoom_number()));
        medicineText = findViewById(R.id.MedicineText);
        if (idM > 0){
            medicineText.setText(medicine.getMedicineName());
        }
        medicineQuantityText = findViewById(R.id.MedicineQuantity);
        medicineQuantity = medicineQuantityText.getText().toString();
        gotoBackBtn = findViewById(R.id.gotoBack);
        notificationsTime = findViewById(R.id.HoraInput);
        gotoMedicinesBtn = findViewById(R.id.gotoMedicines);
        confirmAlarmBtn = findViewById(R.id.ConfirmAlarm);

        /*
        Now this is pod racing. From here on out until the create method ends, we'll be creating the actual alarm+notification!
        Isn't it exciting!?
        No it's not, please do keep reading.
         */
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;

        hour = settings.getString("hour","");
        minute = settings.getString("minute","");



        if(hour.length() > 0)
        {
            notificationsTime.setText(hour + ":" + minute);
        }

        /*
         This displays the TimePicker and automatically writes it down for the notification to be created
         */
        findViewById(R.id.CambiarNotificacion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(UserAlarmScreen.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHour, finalMinute;

                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;
                        if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;
                        notificationsTime.setText(finalHour + ":" + finalMinute);

                        today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putString("hour", finalHour);
                        edit.putString("minute", finalMinute);

                        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                        edit.putInt("alarmID", alarmID);
                        edit.putLong("alarmTime", today.getTimeInMillis());

                        edit.commit();

                        Toast.makeText(UserAlarmScreen.this, "Alarma puesta a las " + finalHour + ":" + finalMinute, Toast.LENGTH_LONG).show();

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Escoge una hora");
                mTimePicker.show();

            }
        });
    }


    private void loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("dataA",Context.MODE_PRIVATE);

        idU = preferences.getInt("idU",0);
    }

    private void savePreferences(){
        SharedPreferences preferences = getSharedPreferences("dataA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editer = preferences.edit();

        editer.putInt("idU",idU);
        editer.commit();
    }



    public void gotoBack(View view){
            Intent mainAct = new Intent(UserAlarmScreen.this, UserInfoScreen.class);
            mainAct.putExtra("id_user",idU);
            startActivity(mainAct);
    }

    public void gotoMedicines(View view){
        Intent mainAct = new Intent(UserAlarmScreen.this, PillsSearchScreen.class);
        mainAct.putExtra("id_userB",idU);
        savePreferences();
        startActivity(mainAct);
    }

    /**
     * Pretty much self explanatory, confirms the alarm and adds it to the database, both to the alarm table and
     * the alarm-user table in order to be shown in the recycler view back at user-info-screen.
     * @param view
     */
    public void confirmAlarm(View view) throws InterruptedException {


        alRe.insertAlarm(medicine.getId_medicine(),
                notificationsTime.getText().toString());


        Log.println(Log.INFO,"Med check null",String.valueOf(medicine.getId_medicine()));
        Log.println(Log.INFO,"Hour check null",notificationsTime.getText().toString());
        int idAl = alRe.getAlarmbyTimeAndMedId(notificationsTime.getText().toString(),medicine.getId_medicine());
        Log.println(Log.INFO,"Alarm Id Res check null",String.valueOf(alRe.getAlarmbyTimeAndMedId(notificationsTime.getText().toString(),medicine.getId_medicine())));
        Log.println(Log.INFO,"Alarm Id check null",String.valueOf(idAl));
        alUsRe.insertObjectById(idAl,idU);

        message = "El paciente " + user.getUser_name() + " " + user.getUser_surname()
                + " tiene apuntada la medicación " + medicine.getMedicineName() + " a las " + notificationsTime.getText().toString() + " horas.";
        UtilAlarma.setAlarm(idAl, today.getTimeInMillis(), UserAlarmScreen.this, message);
        Intent mainAct = new Intent(UserAlarmScreen.this, UserInfoScreen.class);
        int id = 1;
        mainAct.putExtra("alar-record",id);
        startActivity(mainAct);
    }

}