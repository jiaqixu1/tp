package seedu.taskforge.logic.parser;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice", "Bob"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_multipleFields_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice"))
                .setPhoneKeywords(Arrays.asList("91234567"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " -n Alice -p 91234567", expectedFindCommand);

        // multiple keywords in one field
        predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice", "Bob"))
                .setPhoneKeywords(Arrays.asList("91234567", "8888"));
        expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " -n Alice Bob -p 91234567 8888", expectedFindCommand);

        // duplicate prefixes
        PersonContainsKeywordsPredicate predicate2 = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("Alice", "Bob"));
        expectedFindCommand = new FindCommand(predicate2);
        assertParseSuccess(parser, " -n Alice -n Bob", expectedFindCommand);
    }

}
