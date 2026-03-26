package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_TASK_DESC_ADD;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DELETE_1;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DESC_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.task.EditTaskCommand;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private final EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingFields_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_PROJECT_ALPHA + TASK_DELETE_1, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, VALID_PROJECT_ALPHA + TASK_DESC_IMPLEMENT_X, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValues_failure() {
        assertParseFailure(parser, "alpha*" + TASK_DELETE_1 + TASK_DESC_IMPLEMENT_X, Project.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_PROJECT_ALPHA + " -i x" + TASK_DESC_IMPLEMENT_X,
                ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, VALID_PROJECT_ALPHA + TASK_DELETE_1 + INVALID_TASK_DESC_ADD,
                Task.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        assertParseFailure(parser, VALID_PROJECT_ALPHA + " -i 1 -i 2" + TASK_DESC_IMPLEMENT_X,
                Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_INDEX);
        assertParseFailure(parser, VALID_PROJECT_ALPHA + TASK_DELETE_1 + TASK_DESC_IMPLEMENT_X + " -n another task",
                Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
    }

    @Test
    public void parse_validArgs_success() {
        String userInput = VALID_PROJECT_ALPHA + TASK_DELETE_1 + TASK_DESC_IMPLEMENT_X;
        EditTaskCommand expectedCommand = new EditTaskCommand(new Project(VALID_PROJECT_ALPHA),
                Index.fromOneBased(1), new Task(VALID_TASK_IMPLEMENT_X));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


