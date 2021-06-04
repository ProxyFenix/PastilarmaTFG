package com.gmproxy.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.gmproxy.DAO.PathologyRepository;
import com.gmproxy.DAO.PathologyUserRepository;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.PathologyUser;

import java.util.List;

/**
 * What makes this and AlarmInfoViewModel special is that it works for the two recyclerviews down InfoScreen.
 * So, what this does is, create a method to check if there is any record on the database given the user id
 * so that we don't create anything null on the activity, then display all the objects from that id.
 * Marvelous, isn't it?
 */
public class PathologyInfoViewModel extends AndroidViewModel {
        private PathologyRepository repository;
        private PathologyUserRepository paUsRe;
        public LiveData<List<Pathology>> pathologies;
        private PathologyUser pU;

        public PathologyInfoViewModel(Application application, int id) {
                super(application);
                paUsRe = new PathologyUserRepository(application);
                repository = new PathologyRepository(application);
                pathologies = repository.getRelationObj(id);

        }

        public PathologyUser isThereAnyBodyHome(int id) { return paUsRe.getAny(id); }

        public Pathology ObtainById(int id) {return repository.obtainById(id); }

        public int getDataCount() { return repository.getDataCount();}

        public void insert(Pathology obj) { repository.insertObject(obj); }

        public void delete(Pathology obj) { repository.deleteObject(obj); }


}