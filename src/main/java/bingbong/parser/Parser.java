package bingbong.parser;

import bingbong.command.AddCommand;
import bingbong.command.Command;
import bingbong.command.DatesCommand;
import bingbong.command.DeleteCommand;
import bingbong.command.ExitCommand;
import bingbong.command.FindCommand;
import bingbong.command.ListCommand;
import bingbong.command.MarkCommand;
import bingbong.command.UnmarkCommand;
import bingbong.exception.BingBongException;

/**
 * Reads user inputs and decides what command the user wants to execute. It splits the text to understand the command
 * word and its arguments.
 */

public class Parser {

    public static Command parse(String fullCommand) throws BingBongException {
        CommandType type = getCommandType(fullCommand);

        switch (type) {
            case BYE:
                return new ExitCommand();
            case LIST:
                return new ListCommand();
            case MARK:
                return new MarkCommand(fullCommand);
            case UNMARK:
                return new UnmarkCommand(fullCommand);
            case TODO:
                return new AddCommand(fullCommand, CommandType.TODO);
            case DEADLINE:
                return new AddCommand(fullCommand, CommandType.DEADLINE);
            case EVENT:
                return new AddCommand(fullCommand, CommandType.EVENT);
            case DELETE:
                return new DeleteCommand(fullCommand);
            case DATES:
                return new DatesCommand(fullCommand);
            case FIND:
                return new FindCommand(fullCommand);
            default:
                throw new BingBongException("BingBong does not know what that means... :(");
        }
    }

    public static CommandType getCommandType(String in) {
        String commandWord = in.split(" ", 2)[0];
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

    // Extracts target index from mark, unmark and delete
    public static int parseIndex(String in, int prefixLength) throws NumberFormatException {
        return Integer.parseInt(in.substring(prefixLength).trim()) - 1;
    }

    // Splits bingbong.task.Deadline inputs into [description, byDate]
    public static String[] parseDeadline(String in) throws BingBongException {
        if (in.length() <= 9 || in.substring(8).trim().isEmpty()) {
            throw new BingBongException("The description of a deadline cannot be blank. :(");
        }
        String content = in.substring(9);
        int byIndex = content.indexOf(" /by ");
        if (byIndex == -1) {
            throw new BingBongException("A deadline must include a target timing using '/by'.");
        }
        String desc = content.substring(0, byIndex).trim();
        String by = content.substring(byIndex + 4).trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new BingBongException(
                    "Missing fields. BingBong needs the deadline description and target time of the deadline. :(");
        }
        return new String[]{desc, by};
    }

    // Splits bingbong.task.Event input into [description, fromDate, toDate]
    public static String[] parseEvent(String in) throws BingBongException {
        if (in.length() <= 6 || in.substring(5).trim().isEmpty()) {
            throw new BingBongException("The description of an event cannot be blank. :(");
        }
        String content = in.substring(6);
        int fromIndex = content.indexOf(" /from ");
        int toIndex = content.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            throw new BingBongException("An event requires valid time constraints using '/from' and '/to'.");
        }
        String desc = content.substring(0, fromIndex).trim();
        String from = content.substring(fromIndex + 6, toIndex).trim();
        String to = content.substring(toIndex + 4).trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new BingBongException(
                    "Missing fields. BingBong needs the event description, start and end parameters.");
        }
        return new String[]{desc, from, to};
    }

    // Defines all valid command keywords recognised by BingBong
    public enum CommandType {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, DATES, FIND, BYE, UNKNOWN
    }


}
