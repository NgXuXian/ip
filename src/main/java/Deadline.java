import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that must be completed by a specific deadline date.
 */

class Deadline extends Task {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected LocalDate by;

    public Deadline(String description, String by) throws DateTimeParseException {
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
