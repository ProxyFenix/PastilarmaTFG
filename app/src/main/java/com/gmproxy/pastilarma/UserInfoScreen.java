package com.gmproxy.pastilarma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gmproxy.Adapters.AlarmInfoListAdapter;
import com.gmproxy.Adapters.MedicineInfoListAdapter;
import com.gmproxy.Adapters.PathologyInfoListAdapter;
import com.gmproxy.DAO.AlarmUserRepository;
import com.gmproxy.DAO.MedicineRepository;
import com.gmproxy.DAO.PathologyUserRepository;
import com.gmproxy.DAO.UserRepository;
import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.HourMedicine;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;
import com.gmproxy.Util.ImageConverter;
import com.gmproxy.Util.RecyclerItemClickListener;
import com.gmproxy.ViewModels.AlarmInfoViewModel;
import com.gmproxy.ViewModels.PathologyInfoViewModel;
import com.gmproxy.ViewModels.UserViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserInfoScreen extends AppCompatActivity {

    Button addPathology,deleteUser,editUser,addAlarm,gotoObservation,gotoBack;
    EditText usernameText,surnameText,ageText,roomNumberText;
    Spinner genderText;
    ImageButton imageButton;
    RecyclerView pathUsList,alarUsList;

    UserViewModel userViewModel;
    PathologyInfoViewModel pathologyViewModel;
    AlarmInfoViewModel alarmViewModel;

    AlarmUserRepository alUsRe;
    PathologyUserRepository paUsRe;
    UserRepository userRepository;

    PathologyInfoListAdapter adapterPa;
    AlarmInfoListAdapter adapterAl;

    User user;
    PathologyUser pathologyUser;
    Pathology pathology;
    Alarm alarm;
    AlarmUser alarmUser;

    int id;
    int pathAct;
    int alarAct;
    int obseAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_screen);
        /*
        This will come in handy for reloading the user id
         */
        pathAct = getIntent().getIntExtra("path-record",0);
        alarAct = getIntent().getIntExtra("alar-record",0);
        obseAct = getIntent().getIntExtra("obs-record",0);

        id = getIntent().getIntExtra("id_user",0);

        /*
        Now this here, if you came back from any of the activities that can be accesed on the activity itself, you'd reload the id
        If you didn't, you'd get it from the list back there at user-list-screen
         */
        if ((pathAct == 1 || alarAct == 1 || obseAct == 1)){
            loadPreference();
        } else {
            id = getIntent().getIntExtra("id_user",0);
            savePreference();
        }
        Log.println(Log.INFO,"id_user",String.valueOf(id));

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(UserViewModel.class);
        alUsRe = new AlarmUserRepository(this.getApplication());
        paUsRe = new PathologyUserRepository(this.getApplication());
        userRepository = new UserRepository(this.getApplication());

        PathologyUser pU = paUsRe.getAny(id);
        AlarmUser aU = alUsRe.isThereAnyBodyHome(id);

        /*
        If there is no record in the relation down in the database, don't even bother creating the recyclerview, right?
         */
        if (aU != null){
            alarmViewModel = new AlarmInfoViewModel(this.getApplication(),id);
            adapterAl = new AlarmInfoListAdapter(new AlarmInfoListAdapter.UserDiff());
            alarUsList = findViewById(R.id.AlarmList);
            alarUsList.setAdapter(adapterAl);
            alarUsList.setLayoutManager(new LinearLayoutManager(this));
            alarmViewModel.alarm.observe(this,adapterAl::submitList);
            /*
            After setting up the recyclerview, we get to make it do things!
            In particular, we get it to select the item that was clicked, display an options panel, and then delete it (or not) given the user's choice
            Then, if it was deleted, we remove it from the relations table, and stop showing it via reloading the recyclerview itself.
             */
            alarUsList.addOnItemTouchListener(
                    new RecyclerItemClickListener(this,alarUsList, new RecyclerItemClickListener.OnItemClickListener(){
                        @Override
                        public void onItemClick(View view, int position) {
                            alarm = getAlSelected(position);
                            alarmUser = alUsRe.findObjectViaId(user.getId_user(),alarm.getId_alarm());
                            final CharSequence[] options = {"Si", "No"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoScreen.this);
                            builder.setTitle("¿Eliminar la alarma?");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {
                                    if (options[item].equals("Si")) {
                                        Toast.makeText(UserInfoScreen.this, "Has eliminado la patología.", Toast.LENGTH_SHORT).show();
                                        alUsRe.deleteObject(alarmUser);
                                        alarmViewModel.delete(alarm);
                                        alarUsList.removeViewAt(position);
                                        adapterAl.notifyItemRemoved(position);
                                        adapterAl.notifyDataSetChanged();
                                    } else if (options[item].equals("No")) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                            builder.show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    })
            );
        }

        /*
        Same here.
         */
        if (pU != null) {
            pathologyViewModel = new PathologyInfoViewModel(this.getApplication(), id);
            pathUsList = findViewById(R.id.PathologyList);
            adapterPa = new PathologyInfoListAdapter(new PathologyInfoListAdapter.UserDiff());
            pathUsList.setAdapter(adapterPa);
            pathUsList.setLayoutManager(new LinearLayoutManager(this));
            pathologyViewModel.pathologies.observe(this, adapterPa::submitList);
            /*
            Refer to comment in line 128.
             */
            pathUsList.addOnItemTouchListener(
                    new RecyclerItemClickListener(this, pathUsList, new RecyclerItemClickListener.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            pathology = getSelected(position);
                            pathologyUser = paUsRe.getObj(user.getId_user(), pathology.getId_pathology());
                            final CharSequence[] options = {"Si", "No"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoScreen.this);
                            builder.setTitle("¿Eliminar la patología " + pathology.getPathologyName() + "?");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {
                                    if (options[item].equals("Si")) {
                                        Toast.makeText(UserInfoScreen.this, "Has eliminado la patología " + pathology.getPathologyName() + ".", Toast.LENGTH_SHORT).show();
                                        paUsRe.deleteObject(pathologyUser);
                                        pathUsList.removeViewAt(position);
                                        adapterPa.notifyItemRemoved(position);
                                        adapterPa.notifyDataSetChanged();
                                    } else if (options[item].equals("No")) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                            builder.show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    })
            );
        }



        user = userViewModel.getObjectById(id);

        usernameText = findViewById(R.id.Username);
        usernameText.setText(user.getUser_name());
        usernameText.setEnabled(false);
        surnameText = findViewById(R.id.Surname);
        surnameText.setText(user.getUser_surname());
        surnameText.setEnabled(false);
        imageButton = findViewById(R.id.ImageViewButton);
        Bitmap bmp;
        ImageConverter imgC = new ImageConverter();
        bmp = imgC.byteArrayToBitmap(user.getUser_image());
        imageButton.setImageBitmap(bmp);
        imageButton.setEnabled(false);
        genderText = findViewById(R.id.Gender);

        /*
        Noooo you need to do 3 classes, adapters and all kinds of weird things in order to
        have the spinner do what it should do
        Programmer with 0 time left for this goes brrrrrrr
         */
        List<String> genderList = new ArrayList<>();
        genderList.add("M");
        genderList.add("F");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner,genderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderText.setAdapter(adapter);
        if (user.getGender().equals("M")){
            genderText.setSelection(0);
        } else {
            genderText.setSelection(1);
        }

        genderText.setEnabled(false);
        ageText = findViewById(R.id.Age);
        ageText.setText(user.getAge());
        ageText.setEnabled(false);
        roomNumberText = findViewById(R.id.RoomNumber);
        roomNumberText.setText(String.valueOf(user.getRoom_number()));
        roomNumberText.setEnabled(false);
        addPathology = findViewById(R.id.AddPathology);
        deleteUser = findViewById(R.id.DeleteUser);
        editUser = findViewById(R.id.EditUser);
        editUser.setBackgroundResource(R.drawable.pencil);
        addAlarm = findViewById(R.id.AddAlarm);
        gotoObservation = findViewById(R.id.GotoObservation);
        gotoBack = findViewById(R.id.GotoBack);

    }


    private void loadPreference(){
        SharedPreferences preferences = getSharedPreferences("dataS", Context.MODE_PRIVATE);
        id = preferences.getInt("id_usuarioImp",0);
    }

    private void savePreference(){
        SharedPreferences preferences = getSharedPreferences("dataS",Context.MODE_PRIVATE);
        SharedPreferences.Editor editer = preferences.edit();

        editer.putInt("id_usuarioImp",id);

        editer.commit();
    }


    public void gotoBack(View view){
        Intent mainAct = new Intent(UserInfoScreen.this, UserListScreen.class);
        startActivity(mainAct);
        finish();
    }

    public void addAlarm(View view){
        Intent mainAct = new Intent(UserInfoScreen.this, UserAlarmScreen.class);
        mainAct.putExtra("id_userA",user.getId_user());
        startActivity(mainAct);

    }

    public void addPathology(View view){
        Intent mainAct = new Intent(UserInfoScreen.this, PathologiesSearchScreen.class);
        mainAct.putExtra("id_userA",user.getId_user());
        startActivity(mainAct);

    }

    public void gotoObservation(View view){
        Intent mainAct = new Intent(UserInfoScreen.this, UserObservationScreen.class);
        mainAct.putExtra("id_userA",user.getId_user());
        startActivity(mainAct);

    }

    /**
     * Tired of this user in particular? Well now you can do what you can't in real life, and erase it from existence!
     * @param view
     */
    public void deleteUser(View view){
        Toast.makeText(this, "Usuario eliminado.", Toast.LENGTH_SHORT).show();
        paUsRe.deleteAllFromUser(user.getId_user());
        alUsRe.deleteAllFromUser(user.getId_user());
        userViewModel.delete(user);
        Intent mainAct = new Intent(UserInfoScreen.this, UserListScreen.class);
        startActivity(mainAct);
        finish();
    }

    public void editUser(View view){
            usernameText.setEnabled(true);
            surnameText.setEnabled(true);
            ageText.setEnabled(true);
            roomNumberText.setEnabled(true);
            genderText.setEnabled(true);
            imageButton.setEnabled(true);
            editUser.setBackgroundResource(android.R.drawable.ic_menu_save);
            editUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(UserInfoScreen.this, "Has editado el usuario.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    user.setUser_name(usernameText.getText().toString());
                    user.setUser_surname(surnameText.getText().toString());
                    user.setAge(ageText.getText().toString());
                    user.setRoom_number(Integer.parseInt(roomNumberText.getText().toString()));
                    if (genderText.getSelectedItemPosition() == 0){
                        user.setGender("M");
                    } else if (genderText.getSelectedItemPosition() == 1){
                        user.setGender("F");
                    }

                    Bitmap bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();
                    ImageConverter imgC = new ImageConverter();
                    byte[] image = imgC.bitmapToBiteArray(bitmap);
                    user.setUser_image(image);

                    editUser.setBackgroundResource(R.drawable.pencil);
                    usernameText.setEnabled(false);
                    surnameText.setEnabled(false);
                    ageText.setEnabled(false);
                    roomNumberText.setEnabled(false);
                    genderText.setEnabled(false);
                    imageButton.setEnabled(false);
                    userRepository.update(user);
                }
            });
    }


    public void selectImageAction(View view) {
        final CharSequence[] options = {"Hacer una foto", "Elegir de la galería", "Usar una genérica" , "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoScreen.this);
        builder.setTitle("¡Añade una foto!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Hacer una foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivity(intent);
                } else if (options[item].equals("Elegir de la galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(intent);
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                } else if(options[item].equals("Usar una genérica")){
                    imageButton.setImageResource(R.drawable.ic_user_generic_foreground);
                    imageButton.setBackgroundResource(R.drawable.ic_user_generic_foreground);
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
                    imageButton.setImageBitmap(bitmap);
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
                imageButton.setImageBitmap(thumbnail);
            }
        }
    }

    private Pathology getSelected(int position){
        Pathology path = adapterPa.getCurrentObject(position);
        Log.println(Log.INFO, "PathologyUserTest", path.toString());
        return path;
    }

    private Alarm getAlSelected(int position){
        HourMedicine hM = adapterAl.getCurrentObject(position);
        MedicineRepository medRe = new MedicineRepository(this.getApplication());
        Medicine med = medRe.findObjectByName(hM.getNombre());
        int idAl = alarmViewModel.getAlarmbyTimeAndMedId(hM.getHora(),med.getId_medicine());
        Alarm al = alarmViewModel.getAlarmById(idAl);
        Log.println(Log.INFO, "PathologyUserTest", al.toString());
        return al;
    }
}