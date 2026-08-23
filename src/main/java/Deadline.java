// Deadline class: tasks that need to be done before a specific date/time
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Deadline(String description, String by) throws DateTimeParseException{
        super(description);
        String trimmedBy = by.trim();
        try {
            this.by = LocalDate.parse(trimmedBy, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            this.by = LocalDate.parse(trimmedBy);
        }
    }

    public LocalDate getDueDate() {
        return this.by;
    }

    @Override
    public String toString() {
        String formattedDate = this.by.format(DATE_FORMATTER);
        return "[D] [" + getStatusIcon() + "] " + this.description + " (by: " + formattedDate + ")";
    }

    @Override
    public String formatToFile() {
        return "D | " + getStatusBinary() + " | " + this.description + " | " + this.by;
    }
}
