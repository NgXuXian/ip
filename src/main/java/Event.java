// Event class: tasks that start at a specific date/time and end at a specific date/time
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Event(String description, String from, String to) throws DateTimeParseException {
        super(description);
        String trimmedFrom = from.trim();
        String trimmedTo = to.trim();

        try {
            this.from = LocalDate.parse(trimmedFrom, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            this.from = LocalDate.parse(trimmedFrom);
        }

        try {
            this.to = LocalDate.parse(trimmedTo, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            this.to = LocalDate.parse(trimmedTo);
        }
    }

    @Override
    public String toString() {
        String formattedFrom = this.from.format(DATE_FORMATTER);
        String formattedTo = this.to.format(DATE_FORMATTER);
        return "[E] [" + getStatusIcon() + "] " + this.description + "(from: " + formattedFrom + " to: " + formattedTo + ")";
    }

    @Override
    public String formatToFile() {
        return "E | " + getStatusBinary() + " | " + this.description + " | " + this.from + " | " + this.to;
    }
}
