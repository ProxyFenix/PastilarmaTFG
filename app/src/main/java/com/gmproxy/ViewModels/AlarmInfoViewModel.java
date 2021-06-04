package com.gmproxy.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gmproxy.DAO.AlarmRepository;
import com.gmproxy.DAO.AlarmUserRepository;
import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.HourMedicine;

import java.util.List;

/**
 * What makes this and PathologyInfoViewModel special is that it works for the two recyclerviews down InfoScreen.
 * So, what this does is, create a method to check if there is any record on the database given the user id
 * so that we don't create anything null on the activity, then display all the objects from that id.
 * Marvelous, isn't it?
 */
public class AlarmInfoViewModel extends AndroidViewModel {
        private AlarmRepository repository;
        private AlarmUserRepository alUsRe;
        public LiveData<List<HourMedicine>> alarm;

        public AlarmInfoViewModel(Application application, int id) {
                super(application);
                repository = new AlarmRepository(application);
                alUsRe = new AlarmUserRepository(application);
                alarm = repository.getRelationObj(id);

        }
        public int getAlarmbyTimeAndMedId(String time, int idM) { return repository.getAlarmbyTimeAndMedId(time,idM); }

        public Alarm getAlarmById(int i) { return repository.getAlarmById(i); }

        public AlarmUser isThereAnyBodyHome(int id) { return alUsRe.isThereAnyBodyHome(id);  }

        public Alarm getAlarmByTime(String time) { return repository.getAlarmByTime(time); }

        public void insert(Alarm obj) { repository.insertObject(obj); }

        public void insertByIds(int i, String j) { repository.insertAlarm(i,j); }

        public void delete(Alarm obj) { repository.deleteObject(obj); }
}