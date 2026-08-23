// Todo class: tasks without any date/time attached to them
class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T] [" + getStatusIcon() + "] " + this.description;
    }

    @Override
    public String formatToFile() {
        return "T | " + getStatusBinary() + " | " + this.description;
    }
}
