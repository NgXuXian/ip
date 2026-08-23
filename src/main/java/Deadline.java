// Deadline class: tasks that need to be done before a specific date/time
class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D] [" + getStatusIcon() + "] " + this.description + "(by: " + this.by + ")";
    }

    @Override
    public String formatToFile() {
        return "D | " + getStatusBinary() + " | " + this.description + " | " + this.by;
    }
}
