package hannemann.dementiatest;

/**
 * Created by Ann on 12.06.2018.
 */
public class Patient {
    private String surnamePatient;
    private String firstnamePatient;
    private int ageGroupPatient;
    private String otherInformations;
    private boolean agreed;

    public Patient(String surname, String firstname, int ageGroup, String other,
                   boolean testAgreement){
        this.surnamePatient = surname;
        this.firstnamePatient = firstname;
        this.ageGroupPatient = ageGroup;
        this.otherInformations = other;
        this.agreed = testAgreement;
    }

    public String getSurnamePatient() {
        return surnamePatient;
    }

    public String getFirstnamePatient() {
        return firstnamePatient;
    }

    public int getAgeGroupPatient() {
        return ageGroupPatient;
    }

    public String getOtherInformations() {
        return otherInformations;
    }

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed() {
        this.agreed = true;
    }
}
