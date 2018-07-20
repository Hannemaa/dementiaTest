package hannemann.dementiatest;

import java.util.ArrayList;
import java.util.List;

/**
 * Mini-Mental-Status-Test, Mini-Mental-State-Examination
 * Screeningtest zur Erfassung und grobe Einschaetzung von kognitiven Funktionsstoerungen und des Schweregrades
 * Nur fuer die Einschaetzung fortgeschrittener Demenz geeignet
 */
public class Mmse {
    private Participant participantUnderTest;
    private String expertName, dateOfMmse;
    private Task[] mmseTasks = new Task[30];

    public Mmse(Participant participant, String expert, String date) {
        this.participantUnderTest = participant;
        this.expertName = expert;
        this.dateOfMmse = date;
        for (int i = 0; i < 30; i++) {
            mmseTasks[i] = new Task(i);
        }
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public void setTaskPointSuccessful(int taskNumber) {
        mmseTasks[taskNumber].successful();
    }

    public void setTaskPointUnsuccessful(int taskNumber) {
        mmseTasks[taskNumber].unsuccessful();
    }

    public void setTaskInformation(int taskNumber, Object info) {
        mmseTasks[taskNumber].setInformation(info);
    }

    public int getMmseTasks() {
        int sum = 0;
        for (Task t : mmseTasks) {
            sum += t.getPoints();
        }
        return sum;
    }

    public Object getTaskInformation(int taskNumber) {
        return mmseTasks[taskNumber].getInformation();
    }

    public Participant getParticipantUnderTest() {
        return participantUnderTest;
    }

    public void setParticipantUnderTest(Participant participantUnderTest) {
        this.participantUnderTest = participantUnderTest;
    }

    public String getDateOfMmse() {
        return dateOfMmse;
    }

    public void setDateOfMmse(String dateOfMmse) {
        this.dateOfMmse = dateOfMmse;
    }

    /**
     * @return data of participant with sum of points
     * name, date of test, expert, sum of scored points
     */
    public List<String> getTestData() {
        List<String> result = new ArrayList<>();
        result.add(participantUnderTest.getSurname() + " " + participantUnderTest.getFirstname());
        result.add(dateOfMmse);
        result.add(expertName);
        int sum = 0;
        for (Task t : mmseTasks) {
            sum += t.getPoints();
        }
        result.add(Integer.toString(sum));
        return result;
    }
}
