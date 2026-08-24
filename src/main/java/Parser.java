/**
 * Reads user inputs and decides what command the user wants to execute.
 * It splits the text to understand the command word and its arguments.
 */

public class Parser {
    // Defines all valid command keywords recognised by BingBong
    public enum CommandType {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, DATES, BYE, UNKNOWN
    }

    public static CommandType getCommandType(String in) {
        String commandWord = in.split(" ", 2)[0];
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }
}
