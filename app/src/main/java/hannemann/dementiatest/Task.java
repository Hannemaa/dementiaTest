package hannemann.dementiatest;

/**
 * Created by Ann on 12.06.2018.
 */
public class Task {
    final private int taskNum;
    private int points = 0;
    private boolean assessed = false;

    public Task (int number) { this.taskNum = number; }

    public int getTaskNum() {
        return taskNum;
    }

    public int getPoints() {
        return points;
    }

    public void successful() { assessed = true; points++; }

    public void failed() { assessed = true; points = 0; }

    public boolean isAssessed() {
        return assessed;
    }

    public void setAssessed(boolean assessed) {
        this.assessed = assessed;
    }
}
