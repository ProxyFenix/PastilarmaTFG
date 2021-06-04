package com.gmproxy.Util;

import com.gmproxy.Entities.Alarm;
import com.gmproxy.Entities.AlarmUser;
import com.gmproxy.Entities.Medicine;
import com.gmproxy.Entities.Pathology;
import com.gmproxy.Entities.PathologyUser;
import com.gmproxy.Entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * TESTING! Not really much to say.
 */
public class TestUtils {

    public static List<Medicine> testMedicineList = new ArrayList<Medicine>() {{
        add(new Medicine(201, "First Medicine", 1001, 201));
    }};

    public static List<Alarm> testAlarmList = new ArrayList<Alarm>() {{
        add(new Alarm(1, 201, "3"));
    }};

    public static List<Pathology> testPathologyList = new ArrayList<Pathology>() {{
        add(new Pathology(301, "First Pathology"));
        add(new Pathology(302, "Second Pathology"));
        add(new Pathology(303, "Third Pathology"));
    }};

    public static List<User> testUserList = new ArrayList<User>() {{
        byte[] byte1 = new byte[8];
        byte[] byte2 = new byte[8];
        add(new User(501, "First User", "First", "50", 301, "F", "male", byte1));
        add(new User(502, "Second User", "Second", "35", 302, "M", "male", byte2));
    }};

    public static List<AlarmUser> testAlarmUserList = new ArrayList<AlarmUser>() {{
        add(new AlarmUser(101, 1, 501));
    }};

    public static List<PathologyUser> testPathologyUserList = new ArrayList<PathologyUser>() {{
        add(new PathologyUser(401, 501, 301));
    }};
}
