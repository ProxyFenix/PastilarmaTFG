package com.gmproxy.ViewModels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.gmproxy.DAO.AlarmRepository;
import com.gmproxy.DAO.AlarmUserRepository;
import com.gmproxy.DAO.MedicineRepository;
import com.gmproxy.DAO.UserRepository;
import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.User;

public class FalseViewModel extends ViewModel {
    UserRepository usRe;
    AlarmRepository alRe;
    AlarmUserRepository alUsRe;
    MedicineRepository meRe;

    public FalseViewModel(Application application){
        usRe = new UserRepository(application);
        alRe = new AlarmRepository(application);
        alUsRe = new AlarmUserRepository(application);
        meRe = new MedicineRepository(application);
    }

    public AlarmUser getObjectById(int i) { return alUsRe.findClassById(i);}

    public Alarm getAlarmByTime(String time) { return alRe.getAlarmByTime(time); }

    public Medicine getMedicineById(int id) { return meRe.findObjectById(id); }

    public User getUserById(int i) { return usRe.obtainById(i); }
}
