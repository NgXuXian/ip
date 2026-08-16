// Event class: tasks that start at a specific date/time and end at a specific date/time
class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E] ["
                + getStatusIcon()
                + "] "
                + this.description
                + "(from: "
                + this.from
                + " to: "
                + this.to
                + ")";
    }
}
