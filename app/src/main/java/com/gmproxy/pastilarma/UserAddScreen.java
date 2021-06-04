package com.gmproxy.pastilarma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gmproxy.DAO.PathologyRepository;
import com.gmproxy.DAO.PathologyUserRepository;
import com.gmproxy.DAO.UserRepository;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.User;
import com.gmproxy.Util.ImageConverter;
import com.gmproxy.ViewModels.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserAddScreen extends AppCompatActivity {

    Button gotoBack, addObservation, saveUser, addPathologies;
    EditText addUsername, addSurname, addAge, addRoomNumber;
    Spinner addGender;
    ImageView selectImage;
    String obs;
    Pathology paths;
    int pathId;
    Context context;
    UserViewModel userViewModel;
    int pathAct = 0;
    int obsAct = 0;
    PathologyUserRepository paUsRe;
    PathologyRepository paRe;
    UserRepository usRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = this.getIntent();
        context = this.getApplicationContext();
        pathAct = getIntent().getIntExtra("path-record", 0);
        obsAct = getIntent().getIntExtra("obs-record", 0);
        super.onCreate(savedInstanceState);
        paUsRe = new PathologyUserRepository(this.getApplication());
        paRe = new PathologyRepository(this.getApplication());
        usRe = new UserRepository(this.getApplication());


        setContentView(R.layout.activity_user_add_screen);
        gotoBack = findViewById(R.id.GotoBack);
        addObservation = findViewById(R.id.AddObservation);
        saveUser = findViewById(R.id.SaveUser);
        addPathologies = findViewById(R.id.AddPathologies);
        addUsername = findViewById(R.id.AddUsername);
        addSurname = findViewById(R.id.AddSurname);
        addAge = findViewById(R.id.AddAge);
        addRoomNumber = findViewById(R.id.AddRoomNumber);
        addGender = findViewById(R.id.AddGender);
        selectImage = findViewById(R.id.SelectImage);

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(UserViewModel.class);

        /*
        From this line to 94, we create the spinner, since there's only two options there really is no need of poblating the list from the database.
         */
        List<String> genderList = new ArrayList<>();
        genderList.add("M");
        genderList.add("F");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, genderList);
        adapter.setDropDownViewResource(R.layout.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addGender.setAdapter(adapter);
        addGender.setSelection(0);

        if (pathAct == 1 || obsAct == 1){
            loadPreferences();
        }

        paths = (Pathology) getIntent().getSerializableExtra("path");

        if (paths != null){
            savePaths();
            Log.println(Log.INFO,"PathSaveTest",paths.toString());
        } else {
            if (pathAct == 1){
                paths = (Pathology) getIntent().getSerializableExtra("path");
                Log.println(Log.INFO,"PathSaveTest",paths.toString());
                savePaths();
            }

        }

        obs = getIntent().getStringExtra("observation");

        if (obs != null){
            saveObs();
        } else {
            if (obsAct == 1){
                obs = getIntent().getStringExtra("observation");
                Log.println(Log.INFO,"PathSaveTest",obs);
                saveObs();
            }
        }

    }


    public void gotoBack(View view) {
        Intent mainAct = new Intent(UserAddScreen.this, UserListScreen.class);
        startActivity(mainAct);
        finish();
    }

    public void addObservation(View view) {
        Intent mainAct = new Intent(UserAddScreen.this, UserObservationScreen.class);
        if (paths != null){
            savePaths();
        }
        savePreferences();
        startActivity(mainAct);
    }

    public void addPathology(View view) {
        Intent mainAct = new Intent(UserAddScreen.this, PathologiesSearchScreen.class);
        if (obs != null){
            saveObs();
        }
        savePreferences();
        startActivity(mainAct);
    }


    private void savePreferences(){

        SharedPreferences preferences = getSharedPreferences("dataG",Context.MODE_PRIVATE);
        SharedPreferences.Editor editer = preferences.edit();

        editer.putString("Nombre_usuario", addUsername.getText().toString());
        editer.putString("Apellido_usuario", addSurname.getText().toString());
        editer.putString("Edad_usuario", addAge.getText().toString());
        editer.putString("Habitacion_usuario", addRoomNumber.getText().toString());
        editer.putInt("Sexo_usuario", addGender.getSelectedItemPosition());
        editer.commit();
    }

    private void saveObs(){
        SharedPreferences preferences = getSharedPreferences("dataG",Context.MODE_PRIVATE);
        SharedPreferences.Editor editer = preferences.edit();

        editer.putString("Observaciones_usuario", obs);

        editer.commit();
    }

    private void savePaths(){
        SharedPreferences preferences = getSharedPreferences("dataG",Context.MODE_PRIVATE);
        SharedPreferences.Editor editer = preferences.edit();

        editer.putInt("Patología_usuario",paths.getId_pathology());

        editer.commit();
    }


    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("dataG",Context.MODE_PRIVATE);

        addUsername.setText(preferences.getString("Nombre_usuario",""));
        addSurname.setText(preferences.getString("Apellido_usuario",""));
        addAge.setText(preferences.getString("Edad_usuario",""));
        addRoomNumber.setText(preferences.getString("Habitacion_usuario",""));
        addGender.setSelection(preferences.getInt("Sexo_usuario",0));

        Log.println(Log.INFO,"Bundle","Bundle cargado.");
    }

    private void loadObs(){
        SharedPreferences preferences = getSharedPreferences("dataG",Context.MODE_PRIVATE);

        obs = preferences.getString("Observacion_usuario","");

    }

    private void loadPaths(){
        SharedPreferences preferences = getSharedPreferences("dataG",Context.MODE_PRIVATE);
        pathId = preferences.getInt("Patología_usuario",0);
        Log.println(Log.INFO,"PathTest",String.valueOf(pathId));
        paths = paRe.obtainById(pathId);
    }

    /*
    The next two methods are for
    A: Opening either the camera, gallery, or getting a picture from assets
    B: Actually selecting the image and making it show in the imageButton
     */

    public void selectImageAction(View view) {
        final CharSequence[] options = {"Hacer una foto", "Elegir de la galería", "Usar una genérica", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAddScreen.this);
        builder.setTitle("¡Añade una foto!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Hacer una foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivity(intent);
                    finish();
                } else if (options[item].equals("Elegir de la galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(intent);
                    finish();
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                } else if (options[item].equals("Usar una genérica")) {
                    selectImage.setImageResource(R.drawable.ic_user_generic_foreground);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    selectImage.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, System.currentTimeMillis() + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("..******************...", picturePath + "");
                ImageView viewImage;
                selectImage.setImageBitmap(thumbnail);
            }
        }
    }


    /**
     * Actually creates the user in the database
     * @param view
     */
    public void saveUser(View view) {

        //Just this to turn the image into a blob

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.id.SelectImage);
        ImageConverter imgCon = new ImageConverter();
        byte[] bytearray = imgCon.bitmapToBiteArray(bitmap);

        loadPreferences();
        loadObs();
        loadPaths();

        if (!addUsername.getText().toString().equals("")
                || !addSurname.getText().toString().equals("")
                || !addAge.getText().toString().equals("")
                || !addRoomNumber.getText().toString().equals("")){
            Toast.makeText(this, "Usuario creado.", Toast.LENGTH_SHORT).show();
            User user = new User(addUsername.getText().toString(),
                    addSurname.getText().toString(),
                    addAge.getText().toString(),
                    Integer.parseInt(addRoomNumber.getText().toString()),
                    addGender.getSelectedItem().toString(),
                    obs,
                    bytearray);
            usRe.insertObject(user);
            paUsRe.insertObject(user.getId_user(),
                    paths.getId_pathology());
            Toast.makeText(this, "Patología " + paths.getPathologyName() + " añadida", Toast.LENGTH_SHORT).show();
            Intent mainAct = new Intent(UserAddScreen.this, UserListScreen.class);
            startActivity(mainAct);
            finish();
        } else {
            Toast.makeText(this, "Por favor, introduce todos los parámetros necesarios.", Toast.LENGTH_SHORT).show();
        }

    }
}