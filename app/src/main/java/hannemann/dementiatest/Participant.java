package hannemann.dementiatest;

/**
 * Participant of the test
 * the participant has to agree the test (agreed = true) otherwise the execution of the test is not allowed
 * the participant has a surname, first name, year of birth (ageGroupPatient)
 * other information are optional, this String is for a description of the situation like pain, surrounding area ...
 */
public class Participant {
    private String surname;
    private String firstname;
    private int ageGroup; //year of birth
    private String otherInformations;
    private boolean agreed;

    public Participant(String surname, String firstname, int ageGroup, String other,
                       boolean testAgreement) {
        this.surname = surname;
        this.firstname = firstname;
        this.ageGroup = ageGroup;
        this.otherInformations = other;
        this.agreed = testAgreement;
    }

    /**
     * @return String surname of participant
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @return String first name of participant
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return year of birth
     */
    public int getAgeGroup() {
        return ageGroup;
    }

    /**
     * @return String with other information to the participant
     * the information is optional, this String is for a description of the situation like pain, surrounding area ...
     */
    public String getOtherInformations() {
        return otherInformations;
    }

    /**
     * the participant has to agree the test (agreed = true) otherwise the execution of the test is not allowed
     * @return true, when the participant agreed the execution of the test
     * false, when the participant didn't agree the execution of the test
     */
    public boolean isAgreed() {
        return agreed;
    }

    /**
     * true, when the participant agreed the execution of the test
     * false, when the participant didn't agree the execution of the test
     */
    public void setAgreed() {
        this.agreed = true;
    }
}
