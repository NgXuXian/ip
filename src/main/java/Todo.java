/**
 * Represents a simple task without any date or time constraints.
 */

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
