package bingbong.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import bingbong.command.*;
import bingbong.exception.BingBongException;

public class ParserTest {

    @Test
    public void parse_validCommands_returnsCorrectTypes() throws BingBongException {
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
    }

    @Test
    public void parse_unknownInputKeyword_exceptionThrown() {
        assertThrows(BingBongException.class, () -> {
            Parser.parse("invalidKeyword command string");
        });
    }

    @Test
    public void parse_invalidTodoInput_returnsAddCommand() throws BingBongException {
        assertInstanceOf(AddCommand.class, Parser.parse("todo   "));
    }

    @Test
    public void parse_invalidDeadlineInput_returnsAddCommand() throws BingBongException {
        assertInstanceOf(AddCommand.class, Parser.parse("deadline buy eggs 24/08/2026"));
    }
}
