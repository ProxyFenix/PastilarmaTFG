package com.gmproxy.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.gmproxy.DAO.MedicineRepository;
import com.gmproxy.Entities.Medicine;

import java.util.List;

/**
 * Now this, user and pathologies view model are the interesting ones. Basically, we create another list in order to get the filtered list,
 * which checks which one should it show based on the text input of the searchViews object.
 */
public class MedicineViewModel extends AndroidViewModel {
        private MedicineRepository repository;
        public LiveData<List<Medicine>> medicines;
        public MutableLiveData<String> filteredList = new MutableLiveData<>();

        public MedicineViewModel(Application application) {
                super(application);
                repository = new MedicineRepository(application);
                medicines = Transformations.switchMap(filteredList, (input) -> {
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

        LiveData<List<Medicine>> getAllMedicines() { return medicines; }

        public Medicine getMedicineById(int id) { return repository.findObjectById(id); }

        public void insert(Medicine obj) { repository.insertObject(obj); }
}