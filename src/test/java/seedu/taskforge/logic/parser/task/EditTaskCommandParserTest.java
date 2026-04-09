package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_TASK_DESC_ADD;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DESC_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.task.EditTaskCommand;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.model.task.Task;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private final EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingFields_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, TASK_DESC_IMPLEMENT_X, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValues_failure() {
        assertParseFailure(parser, "x 1" + TASK_DESC_IMPLEMENT_X,
                ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 x" + TASK_DESC_IMPLEMENT_X,
                ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 1" + INVALID_TASK_DESC_ADD,
                Task.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        assertParseFailure(parser, "1 1" + TASK_DESC_IMPLEMENT_X + " -n another task",
                Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
    }

    @Test
    public void parse_validArgs_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_FIRST_TASK.getOneBased()
                + TASK_DESC_IMPLEMENT_X;
        EditTaskCommand expectedCommand = new EditTaskCommand(Index.fromOneBased(1),
                Index.fromOneBased(1), new Task(VALID_TASK_IMPLEMENT_X));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


