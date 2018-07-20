package hannemann.dementiatest;

/**
 * Created by Ann on 12.06.2018.
 * Task ist die Aufgabe mit den fuer sie erreichten Punkten.
 * Wenn eine Aufgabe nicht ausgefuehrt werden konnte, kann dies ueber die Variabel assessed dokumentiert werden.
 * Jeder task hat eine Nummer und kann weitere Informationen in einem Objekt enthalten.
 */
public class Task {
    final private int taskNum;
    private int points = 0;
    private boolean assessed = false;
    private Object information;

    /**
     * Jeder Task muss eine Nummer zur Identifizierung haben.
     *
     * @param number Nummer zur Zuordnung der Ausgabe
     */
    public Task(int number) {
        this.taskNum = number;
    }

    /**
     * @return Nummer der Aufgabe
     */
    public int getTaskNum() {
        return taskNum;
    }

    /**
     * @return erreichte Punktzahl einer Ausgabe,
     * wenn keine Punkte vergeben wurden ist die Punktzahl 0
     */
    public int getPoints() {
        return points;
    }

    /**
     * bei erfolgreicher Testdurchfuehrung
     */
    public void successful() {
        assessed = true;
        points++;
    }

    /**
     *
     */
    public void unsuccessful() {
        assessed = true;
        points = 0;
    }

    /**
     * @return true, wenn die Aufgabe/der Task durchgefuehrt wurde
     * false, wenn ein Ausfuehren der Aufgabe/des Tasks nicht moeglich war
     */
    public boolean isAssessed() {
        return assessed;
    }

    /**
     * @return zuvor gesetzte Informationen
     * z.B. String mit Informationen des Gutachters oder die gegebene Antwort
     * bei Bildern und geschriebenen Text Abbilder davon,
     * bei Sprachaufnahmen der von der App verstandene Text in schriftlicher Form
     */
    public Object getInformation() {
        return information;
    }

    /**
     * Setzen einer zusaetzlichen Information zur Aufgabe und ihrer Beantwortung
     *
     * @param info z.B. String mit Informationen des Gutachters oder die gegebene Antwort
     *             bei Bildern und geschriebenen Text Abbilder davon,
     *             bei Sprachaufnahmen der von der App verstandene Text in schriftlicher Form
     */
    public void setInformation(Object info) {
        this.information = info;
    }
}
