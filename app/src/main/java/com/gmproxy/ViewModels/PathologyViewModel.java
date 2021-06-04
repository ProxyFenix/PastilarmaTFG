package com.gmproxy.ViewModels;

import android.app.Application;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.gmproxy.DAO.PathologyRepository;
import com.gmproxy.Entities.Pathology;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PathologyViewModel extends AndroidViewModel {
        private PathologyRepository repository;
        public LiveData<List<Pathology>> pathologies;
        public MutableLiveData<String> filteredList = new MutableLiveData<>();

        public PathologyViewModel(Application application) {
                super(application);
                repository = new PathologyRepository(application);
                pathologies = Transformations.switchMap(filteredList, (input) -> {
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

        public Pathology ObtainById(int id) {return repository.obtainById(id); }

        public int getDataCount() { return repository.getDataCount();}

        public void insert(Pathology obj) { repository.insertObject(obj); }

        public void delete(Pathology obj) { repository.deleteObject(obj); }


}