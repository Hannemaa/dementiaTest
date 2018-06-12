package hannemann.dementiatest;

/**
 * Created by Ann on 12.06.2018.
 */
public class Task {
    final private int taskNum;
    private int points = 0;
    private boolean assessed = true;

    public Task (int number) {
        this.taskNum = number;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public int getPoints() {
        return points;
    }

    public void successful() {
        this.points++;
    }

    public void failed() {
    }

    public boolean isAssessed() {
        return assessed;
    }

    public void setAssessed(boolean assessed) {
        this.assessed = assessed;
    }
}
