/**
 * Represents a generic task.
 * It stores the main description and tracks if the task is done or not done.
 */

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    protected String getStatusBinary() {
        return isDone ? "1" : "0";
    }

    public abstract  String formatToFile();
}
