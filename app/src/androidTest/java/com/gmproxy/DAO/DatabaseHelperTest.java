package com.gmproxy.DAO;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;
import com.gmproxy.Util.TestUtils;
import com.gmproxy.pastilarma.PathologiesSearchScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class DatabaseHelperTest {

    private AlarmDAO alarmDAO;
    private AlarmUserDAO alarmUserDAO;
    private MedicineDAO medicineDAO;
    private PathologyDAO pathologyDAO;
    private PathologyUserDAO pathologyUserDAO;
    private UserDAO userDAO;
    private DatabaseHelper db;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(
                context, DatabaseHelper.class
        ).build();
        //Get All DAO
        alarmDAO = db.alarmDao();
        alarmUserDAO = db.alarmUserDao();
        medicineDAO = db.medicineDao();
        pathologyDAO = db.pathologyDao();
        pathologyUserDAO = db.pathologyUserDao();
        userDAO = db.userDao();

        //Insert Data from Test Utils
        medicineDAO.insertAllObjects(TestUtils.testMedicineList);
        alarmDAO.insertAllObjects(TestUtils.testAlarmList);
        pathologyDAO.insertAllObjects(TestUtils.testPathologyList);
        userDAO.insertAllObjects(TestUtils.testUserList);
        alarmUserDAO.insertAllObjects(TestUtils.testAlarmUserList);
        pathologyUserDAO.insertAllObjects(TestUtils.testPathologyUserList);
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    // Test for Alarm
    @Test
    public void testGetAlarm(){
        List<Alarm> listItems = alarmDAO.getAllObjects();
        assertThat(listItems.size(), equalTo(1));
    }

    @Test
    public void testInsertAlarm(){
        Alarm item = new Alarm(2, 201, "4");
        alarmDAO.insertObject(item);
        assertThat(alarmDAO.getAllObjects().size(), equalTo(2));
    }

    @Test
    public void testDeleteAlarm(){
        Alarm item = alarmDAO.getAllObjects().get(0);
        alarmDAO.delete(item);
        assertThat(alarmDAO.getAllObjects().size(), equalTo(0));
    }

    @Test
    public void testUpdateAlarm(){
        Alarm item = alarmDAO.getAllObjects().get(0);
        item.setHour("Changed");
        alarmDAO.updateObject(item);
        assertThat(alarmDAO.getAllObjects().get(0).getHour(), equalTo("Changed"));
    }

    // Test for Pathology
    @Test
    public void testGetPathology(){
        LiveData<List<Pathology>> listItems = pathologyDAO.getAllObjects();
        List<Pathology> lista = Transformations.switchMap(listItems);
        assertThat(listItems.size(), equalTo(3));
    }

    @Test
    public void testInsertPathology(){
        Pathology item = new Pathology(304, "Fourth Pathology");
        pathologyDAO.insertObject(item);
        assertThat(pathologyDAO.getAllObjects().size(), equalTo(4));
    }

    @Test
    public void testDeletePathology(){
        Pathology item = pathologyDAO.getAllObjects().get(0);
        pathologyDAO.delete(item);
        assertThat(pathologyDAO.getAllObjects().size(), equalTo(2));
    }

    @Test
    public void testUpdatePathology(){
        Pathology item = pathologyDAO.getAllObjects().get(0);
        item.setPathologyName("Changed");
        pathologyDAO.updateObject(item);
        assertThat(pathologyDAO.getAllObjects().get(0).getPathologyName(), equalTo("Changed"));
    }

    // Test for AlarmUser
    @Test
    public void testGetAlarmUser(){
        List<AlarmUser> listItems = alarmUserDAO.getAllObjects();
        assertThat(listItems.size(), equalTo(1));
    }

    @Test
    public void testInsertAlarmUser(){
        AlarmUser item = new AlarmUser(102, 1, 502);
        alarmUserDAO.insertObject(item);
        assertThat(alarmUserDAO.getAllObjects().size(), equalTo(2));
    }

    @Test
    public void testDeleteAlarmUser(){
        AlarmUser item = alarmUserDAO.getAllObjects().get(0);
        alarmUserDAO.delete(item);
        assertThat(alarmUserDAO.getAllObjects().size(), equalTo(0));
    }

    @Test
    public void testUpdateAlarmUser(){
        AlarmUser item = alarmUserDAO.getAllObjects().get(0);
        item.setId_user(501);
        alarmUserDAO.updateObject(item);
        assertThat(alarmUserDAO.getAllObjects().get(0).getId_user(), equalTo(501));
    }

    // Test for Medicine
    @Test
    public void testGetMedicine(){
        LiveData<List<Medicine>> listItems = medicineDAO.getAllObjects();
        assertThat(listItems.size(), equalTo(1));
    }

    @Test
    public void testInsertMedicine(){
        Medicine item = new Medicine(202, "Second Medicine", 502, 202);
        medicineDAO.insertObject(item);
        assertThat(medicineDAO.getAllObjects().size(), equalTo(2));
    }

    @Test
    public void testDeleteMedicine(){
        Medicine item = medicineDAO.getAllObjects().get(0);
        medicineDAO.delete(item);
        assertThat(medicineDAO.getAllObjects().size(), equalTo(0));
    }

    @Test
    public void testUpdateMedicine(){
        Medicine item = medicineDAO.getAllObjects().get(0);
        item.setMedicineName("Changed");
        medicineDAO.updateObject(item);
        assertThat(medicineDAO.getAllObjects().get(0).getMedicineName(), equalTo("Changed"));
    }

    // Test for PathologyUser
    @Test
    public void testGetPathologyUser(){
        List<PathologyUser> listItems = pathologyUserDAO.getAllObjects();
        assertThat(listItems.size(), equalTo(1));
    }

    @Test
    public void testInsertPathologyUser(){
        PathologyUser item = new PathologyUser(402, 501, 302);
        pathologyUserDAO.insertObject(item);
        assertThat(pathologyUserDAO.getAllObjects().size(), equalTo(2));
    }

    @Test
    public void testDeletePathologyUser(){
        PathologyUser item = pathologyUserDAO.getAllObjects().get(0);
        pathologyUserDAO.delete(item);
        assertThat(pathologyUserDAO.getAllObjects().size(), equalTo(0));
    }

    @Test
    public void testUpdatePathologyUser(){
        PathologyUser item = pathologyUserDAO.getAllObjects().get(0);
        item.setId_user(502);
        pathologyUserDAO.updateObject(item);
        assertThat(pathologyUserDAO.getAllObjects().get(0).getId_user(), equalTo(502));
    }
}