package com.gmproxy.pastilarma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.gmproxy.Adapters.UserListAdapter;
import com.gmproxy.DAO.UserRepository;
import com.gmproxy.Entities.User;
import com.gmproxy.Util.RecyclerItemClickListener;
import com.gmproxy.ViewModels.UserViewModel;

public class UserListScreen extends AppCompatActivity {
    UserViewModel userViewModel;
    RecyclerView recyclerView;
    User user;
    UserListAdapter adapter;
    Button addUser;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_screen);

        addUser = findViewById(R.id.AddUser);
        recyclerView = findViewById(R.id.userRecyclerView);
        searchView = findViewById(R.id.SearchView);
        adapter = new UserListAdapter(new UserListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(UserViewModel.class);
        userViewModel.users.observe(this,adapter::submitList);

        /*
        Since this is the first activity you all get to see when you boot up the app, I'll explain it here:
        -You enter a text
        -Said text filters the list
        -Boom, filtered list for you to see. Easier for the user, not that hard for the coder
        -(Mostly)
        You can resort to this explanation for the recyclerviews in Pathologies and Medicines list, besides this one.
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userViewModel.setFilter(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                long start;
                start = System.currentTimeMillis();
                if ((newText.length() > 3) && (System.currentTimeMillis() - start > 500)) {
                    userViewModel.setFilter(searchView.getQuery().toString());
                }
                return false;
            }
        });

        /*
        We get the user by clicking on it, get the id, use it to open the user's info on the info-screen.
         */
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        user = getSelectedUser(position);
                        Intent mainAct = new Intent(UserListScreen.this, UserInfoScreen.class);
                        mainAct.putExtra("id_user", user.getId_user());
                        startActivity(mainAct);
                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }
                )
        );


    }

    /**
     * Pretty self explanatory, we just get the user.
     * @param position
     * @return
     */
    public User getSelectedUser(int position){
        User usr = adapter.getCurrentObject(position);
        Log.println(Log.INFO,"UserTest",usr.toString());
        return usr;
    }

    public void addUser(View view){
        Intent mainAct = new Intent(UserListScreen.this, UserAddScreen.class);
        startActivity(mainAct);
        finish();
    }
}