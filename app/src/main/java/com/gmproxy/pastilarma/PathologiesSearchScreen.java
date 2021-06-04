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

import com.gmproxy.Adapters.PathologyListAdapter;
import com.gmproxy.DAO.PathologyUserRepository;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Util.RecyclerItemClickListener;
import com.gmproxy.ViewModels.PathologyViewModel;


public class PathologiesSearchScreen extends AppCompatActivity {
    PathologyViewModel viewModel;
    PathologyUserRepository paUsRe;
    SearchView searchView;
    RecyclerView recyclerView;
    Pathology pathology;
    PathologyListAdapter adapter;
    int idU;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathology_search_list);
        idU = getIntent().getIntExtra("id_userA",0);
        paUsRe = new PathologyUserRepository(this.getApplication());
        searchView = findViewById(R.id.SearchView);
        recyclerView = findViewById(R.id.userRecyclerView);
        adapter = new PathologyListAdapter(new PathologyListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(PathologyViewModel.class);
        viewModel.pathologies.observe(this, adapter::submitList);

        /*
        For more information about this, check ListScreen explanation.
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setFilter(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                long start;
                start = System.currentTimeMillis();
                if ((newText.length() > 3) && (System.currentTimeMillis() - start > 500)) {
                    viewModel.setFilter(searchView.getQuery().toString());
                }
                return false;
            }
        });

        /*
        This is actually amazing, the fact that not only have I managed to implement a data list right from the database, but that I've too implemented a filtered list.
        Just select whatever you need, it will send the object down the activity intent.
        By now, you must have seen all these dialog builders. Well, this is what I like to call "User experience for monkeys"
        So that I don't hear any "This was added by accident!" while I'm working.
        No it wasn't Carol, you had to actually choose to add it, in a big text box that specified what it would do were you to press the "Yes" button, so learn from your mistakes.
         */
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {


                    @Override
                    public void onItemClick(View view, int position) {
                        pathology = getSelectedPathology(position);
                        Log.println(Log.INFO, "PathologyTest", pathology.toString());
                        final CharSequence[] options = {"Si", "No"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(PathologiesSearchScreen.this);
                        builder.setTitle("¿Añadir la patología " + pathology.getPathologyName() + "?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (options[item].equals("Si")) {
                                    Toast.makeText(PathologiesSearchScreen.this, "Has añadido la patología " + pathology.getPathologyName() + ".", Toast.LENGTH_SHORT).show();
                                    if (idU != 0){
                                        Intent mainAct = new Intent(PathologiesSearchScreen.this, UserInfoScreen.class);
                                        paUsRe.insertObject(idU,pathology.getId_pathology());
                                        int i = 1;
                                        mainAct.putExtra("path-record",i);
                                        startActivity(mainAct);
                                    } else {
                                        Intent mainAct = new Intent(PathologiesSearchScreen.this, UserAddScreen.class);
                                        mainAct.putExtra("path", pathology);
                                        int i = 1;
                                        mainAct.putExtra("path-record",i);
                                        startActivity(mainAct);
                                    }

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
                }
                )
        );
    }

    /**
     * Not much, get the clicked position, get the pathology object.
     * Simple and clean action.
     * @param position
     * @return Pathology
     */
        public Pathology getSelectedPathology (int position){
            Pathology path = adapter.getCurrentObject(position);
            Log.println(Log.INFO, "PathologyTest", path.toString());
            return path;
        }

}