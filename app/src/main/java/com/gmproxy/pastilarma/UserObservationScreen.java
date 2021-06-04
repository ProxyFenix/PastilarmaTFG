package com.gmproxy.pastilarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmproxy.Entities.User;
import com.gmproxy.ViewModels.UserViewModel;

import java.text.BreakIterator;

public class UserObservationScreen extends AppCompatActivity {

    Button saveObservation,gotoBack;
    EditText observationText;

    int id;

    UserViewModel userViewModel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_observation_screen);
        id = getIntent().getIntExtra("id_userA",0);

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(UserViewModel.class);



        saveObservation = findViewById(R.id.SaveObservation);
        gotoBack = findViewById(R.id.GotoBack);
        observationText = findViewById(R.id.ObservationText);
        observationText.setEnabled(true);
        /*
        After all, we are showing a text here, aren't we? We create the user using the id we were passed down by the activity before us,
        and display said text, if there is any, of course.
         */
        if (id > 0){
            user = userViewModel.getObjectById(id);
            observationText.setText(user.getObservation());
            observationText.setEnabled(false);
        }
    }

    /**
     * This method, while being pretty much identical to the rest of gotoBack(s), adds some things like it does on Pathologies-search-screen
     * Mostly, depending on the id it received on the creation, it chooses to go to either info or add screen.
     * @param view
     */
    public void gotoBack(View view){
        int obId = 1;
        if (id > 0){
            Intent mainAct = new Intent(UserObservationScreen.this, UserInfoScreen.class);
            mainAct.putExtra("obs-record",obId);
            startActivity(mainAct);
        } else {
            Toast.makeText(this, "Observación guardada.", Toast.LENGTH_SHORT).show();
            Intent mainAct = new Intent(UserObservationScreen.this, UserAddScreen.class);
            String obs = getObservation();
            mainAct.putExtra("obs-record",obId);
            mainAct.putExtra("observation",obs);
            startActivity(mainAct);
            finish();
        }

    }

    /**
     * Method to return somewhat a shorter variable.
     * @return
     */
    public String getObservation(){
        String text = observationText.getText().toString();
        if (text != null){
            return text;
        } else {
            return null;
        }
    }

    public void editObservation(View view){
        observationText.setEnabled(true);
        saveObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id > 0){
                    userViewModel.updateObservation(user.getId_user(), observationText.getText().toString());
                }
                observationText.setEnabled(false);
            }
        });
        Toast.makeText(this, "Observación editada.", Toast.LENGTH_SHORT).show();


    }
}