package seedu.taskforge.logic.parser;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.person.FindCommand.MESSAGE_USAGE;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.person.FindCommand;
import seedu.taskforge.logic.parser.person.FindCommandParser;
import seedu.taskforge.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice", "Bob"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " -n Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n -n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_preambleNotEmpty_throwsParseException() {
        // preamble without prefix
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // preamble with other prefix
        assertParseFailure(parser, "Alice -n Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleFields_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice"))
                .setPhoneKeywords(Arrays.asList("91234567"))
                .setEmailKeywords(Arrays.asList("alice@example.com"))
                .setTaskKeywords(Arrays.asList("Task1"))
                .setProjectKeywords(Arrays.asList("Project1"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " -n Alice -p 91234567 -e alice@example.com -d Task1 -l Project1",
                expectedFindCommand);

        // multiple keywords in one field
        predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice", "Bob"))
                .setPhoneKeywords(Arrays.asList("91234567", "8888"))
                .setEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"))
                .setTaskKeywords(Arrays.asList("Task1", "Task2"))
                .setProjectKeywords(Arrays.asList("Project1", "Project2"));
        expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " -n Alice Bob -p 91234567 8888 -e alice@example.com bob@example.com "
                + "-d Task1 Task2 -l Project1 Project2", expectedFindCommand);

        // duplicate prefixes
        PersonContainsKeywordsPredicate predicate2 = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice", "Bob"));
        expectedFindCommand = new FindCommand(predicate2);
        assertParseSuccess(parser, " -n Alice -n Bob", expectedFindCommand);
    }

    @Test
    public void parse_emptyFields_throwsParseException() {
        // field with empty whitespace string for individual parameter
        assertParseFailure(parser, " -n   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " -p   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " -e   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " -d   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, " -l   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // multiple parameters with empty whitespace string for parameters
        assertParseFailure(parser, " -n -p 91234567 -e email@gmail.com -d Task1 -l project1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        assertParseFailure(parser, " -n Alice -p -e email@gmail.com -d Task1 -l project1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        assertParseFailure(parser, " -n Alice -p 91234567 -e email@gmail.com -d -l project1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        assertParseFailure(parser, " -n Alice -p 91234567 -e -d Task1 -l project1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        assertParseFailure(parser, " -n Alice -p 91234567 -e email@gmail.com -d Task1 -l   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

}
