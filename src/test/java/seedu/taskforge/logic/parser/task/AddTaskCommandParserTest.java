package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_TASK_DESC_ADD;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DESC_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DESC_IMPLEMENT_Y;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DESC_IMPLEMENT_Z;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_Y;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_Z;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AddTaskCommand;
import seedu.taskforge.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.model.task.Task;

public class AddTaskCommandParserTest {
    private static final String TASK_EMPTY = " " + PREFIX_NAME;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_missingTask_failure() {
        assertParseFailure(parser, TASK_DESC_IMPLEMENT_X, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", AddTaskCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "1" + TASK_EMPTY, AddTaskCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-5" + TASK_DESC_IMPLEMENT_X, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0" + TASK_DESC_IMPLEMENT_X, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 some random string", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 i/ string", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TASK_DESC_ADD, Task.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TASK_DESC_IMPLEMENT_X + INVALID_TASK_DESC_ADD, Task.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_oneTask_success() {
        Index targetIndex = INDEX_SECOND_PROJECT;
        String userInput = targetIndex.getOneBased() + TASK_DESC_IMPLEMENT_X;

        AddTaskDescriptor descriptor = new AddTaskDescriptor();
        descriptor.setTasks(Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X)));
        AddTaskCommand expectedCommand = new AddTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTasks_success() {
        Index targetIndex = INDEX_SECOND_PROJECT;
        String userInput = targetIndex.getOneBased()
                + TASK_DESC_IMPLEMENT_X + TASK_DESC_IMPLEMENT_Y + TASK_DESC_IMPLEMENT_Z;

        AddTaskDescriptor descriptor = new AddTaskDescriptor();
        descriptor.setTasks(Arrays.asList(
                new Task(VALID_TASK_IMPLEMENT_X),
                new Task(VALID_TASK_IMPLEMENT_Y),
                new Task(VALID_TASK_IMPLEMENT_Z)));
        AddTaskCommand expectedCommand = new AddTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
