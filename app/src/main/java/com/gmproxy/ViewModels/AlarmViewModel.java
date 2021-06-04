package com.gmproxy.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gmproxy.DAO.AlarmRepository;
import com.gmproxy.DAO.AlarmUserRepository;
import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;

import java.util.List;

/**
 * Not much different here from what you'll see on the rest of view models, get all the objects, and shoos. This one in particular isn't really that much used
 * for listing, but well oh well, we can't all be born to be astronauts.
 */
public class AlarmViewModel extends AndroidViewModel {
        private AlarmRepository repository;
        public LiveData<List<Alarm>> alarm;

        public AlarmViewModel(Application application) {
                super(application);
                repository = new AlarmRepository(application);
                alarm = repository.getAllObjects();
        }

        LiveData<List<Alarm>> getAll() { return alarm; }

        public Alarm getAlarmById(int i) { return repository.getAlarmById(i); }

        public int getAlarmbyTimeAndMedId(String time, int idM) { return repository.getAlarmbyTimeAndMedId(time,idM); }

        public Alarm getAlarmByTime(String time) { return repository.getAlarmByTime(time); }

        public void insert(Alarm obj) { repository.insertObject(obj); }

        public void insertByIds(int i, String j) { repository.insertAlarm(i,j); }

        public void delete(Alarm obj) { repository.deleteObject(obj); }
}