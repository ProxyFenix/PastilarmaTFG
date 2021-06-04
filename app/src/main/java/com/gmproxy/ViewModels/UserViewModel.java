package com.gmproxy.ViewModels;

import android.app.Application;
import android.view.animation.Transformation;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.gmproxy.DAO.UserRepository;
import com.gmproxy.Entities.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
        private UserRepository repository;
        public LiveData<List<User>> users;
        public MutableLiveData<String> filteredList = new MutableLiveData<>();

        public UserViewModel(Application application) {
                super(application);
                repository = new UserRepository(application);
                users = Transformations.switchMap(filteredList, (input) ->{
                        if(input == null || input.equals("")){
                                return repository.getAllObjects();
                        } else {
                                return repository.filter(input);
                        }
                });
        }

        public void setFilter(String query){
                filteredList.setValue(query);
        }

        LiveData<List<User>> getAllUsers() { return users; }

        public void insert(User obj) { repository.insertObject(obj); }

        public void insertUser(String a, String b, String c, String d, String e, String f, byte[] g) { repository.insertUser(a,b,c,d,e,f,g); }

        public User getObjectById(int i) { return repository.obtainById(i); }

        public int getIdByNameAndSurname (String a, String b) { return repository.getUserIdByNameAndSurname(a,b); }

        public void delete(User obj) { repository.deleteObject(obj); }

        public void update(User obj) { repository.update(obj); }

        public void updateObservation(int id, String obs) { repository.updateObservation(id, obs);}
}