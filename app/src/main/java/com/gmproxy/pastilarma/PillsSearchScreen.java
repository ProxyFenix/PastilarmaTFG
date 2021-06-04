package com.gmproxy.pastilarma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.gmproxy.Adapters.MedicineListAdapter;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Util.RecyclerItemClickListener;
import com.gmproxy.ViewModels.MedicineViewModel;

/**
 * Identical 0,99/1 class to Pathologies Search Screen
 */
public class PillsSearchScreen extends AppCompatActivity {
    private MedicineViewModel viewModel;
    SearchView searchView;
    Medicine medicine;
    MedicineListAdapter adapter;
    int idU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pills_search_screen);

        idU = getIntent().getIntExtra("id_userB",0);

        searchView = findViewById(R.id.SearchView);
        RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
        adapter = new MedicineListAdapter(new MedicineListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new ViewModelProvider(this).get(MedicineViewModel.class);
        viewModel.medicines.observe(this, adapter::submitList);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setFilter(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                long start;
                start = System.currentTimeMillis();
                if ((newText.length() > 3) && (System.currentTimeMillis() - start > 500)){
                    viewModel.setFilter(searchView.getQuery().toString());
                }
                return false;
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {


                    @Override
                    public void onItemClick(View view, int position) {
                        medicine = getSelectedMedicine(position);
                        Log.println(Log.INFO, "PathologyTest", medicine.toString());
                        final CharSequence[] options = {"Si", "No"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(PillsSearchScreen.this);
                        builder.setTitle("¿Añadir el medicamento " + medicine.getMedicineName() + "?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (options[item].equals("Si")) {
                                    Toast.makeText(PillsSearchScreen.this, "Has añadido la patología " + medicine.getMedicineName() + ".", Toast.LENGTH_SHORT).show();
                                    Intent mainAct = new Intent(PillsSearchScreen.this, UserAlarmScreen.class);
                                    mainAct.putExtra("med", medicine.getId_medicine());
                                    int i = 1;
                                    mainAct.putExtra("med-record",i);
                                    mainAct.putExtra("id_userA",idU);
                                    startActivity(mainAct);
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
                }));

    }

    public Medicine getSelectedMedicine(int position){
        medicine = adapter.getCurrentObject(position);
        Log.println(Log.INFO,"MedicineTest",medicine.toString());
        return medicine;
    }

}