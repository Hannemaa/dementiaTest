package hannemann.dementiatest;

import java.util.Date;

/**
 * Created by Ann on 12.06.2018.
 */
public class Mmse {
    private Patient patientUnderTest;
    private String expertName;
    private Date dateOfMmse;
    private Task[] points = new Task[30];

    public Mmse(Patient patient, String expert, Date date) {
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

    public void setDateOfMmse(Date dateOfMmse) {
        this.dateOfMmse = dateOfMmse;
    }

    public void setTaskPointSuccessful(int taskNumber) {
        points[taskNumber].successful();
    }

    public void setTaskPointFailed(int taskNumber) { points[taskNumber].failed(); }

    public void setTaskPointFalse(int taskNumber) { points[taskNumber].setAssessed(false); }
}
