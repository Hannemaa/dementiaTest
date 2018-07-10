package hannemann.dementiatest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ann on 12.06.2018.
 */
public class Mmse {
    private Patient patientUnderTest;
    private String expertName, dateOfMmse;
    private Task[] points = new Task[30];

    public Mmse(Patient patient, String expert, String date) {
        this.patientUnderTest = patient;
        this.expertName = expert;
        this.dateOfMmse = date;
        for(int i = 0; i < 30; i++) {
            points[i] = new Task(i);
        }
    }

    public void setPatientUnderTest(Patient patientUnderTest) {
        this.patientUnderTest = patientUnderTest;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public void setDateOfMmse(String dateOfMmse) {
        this.dateOfMmse = dateOfMmse;
    }

    public void setTaskPointSuccessful(int taskNumber) {
        points[taskNumber].successful();
    }

    public void setTaskPointFailed(int taskNumber) { points[taskNumber].setAssessed(false); }

    public void setTaskPointFalse(int taskNumber) { points[taskNumber].failed(); }

    public void setTaskInformation(int taskNumber, Object info) {
        points[taskNumber].setInformation(info);
    }

    public int getPoints() {
        int sum = 0;
        for (Task t: points) {
            sum += t.getPoints();
        }
        return sum;
    }

    public Object getTaskInformation(int taskNumber) {
        return points[taskNumber].getInformation();
    }

    public Patient getPatientUnderTest() {
        return patientUnderTest;
    }

    public List<String> getTestData() {
        List<String> result = new ArrayList<>();
        result.add(patientUnderTest.getSurnamePatient() + " " + patientUnderTest.getFirstnamePatient());
        result.add(dateOfMmse);
        int sum = 0;
        for (Task t: points) {
            sum += t.getPoints();
        }
        result.add(Integer.toString(sum));
        return result;
    }
}
