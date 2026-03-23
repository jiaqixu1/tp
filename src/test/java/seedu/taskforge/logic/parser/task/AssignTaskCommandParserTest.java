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
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.testutil.AssignTaskDescriptorBuilder;

public class AssignTaskCommandParserTest {
    private static final String TASK_EMPTY = " " + PREFIX_NAME;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE);

    private AssignTaskCommandParser parser = new AssignTaskCommandParser();

    @Test
    public void parse_missingTask_failure() {
        // no index specified
        assertParseFailure(parser, TASK_DESC_IMPLEMENT_X, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AssignTaskCommand.MESSAGE_NOT_EDITED);

        // empty task value after task prefix
        assertParseFailure(parser, "1" + TASK_EMPTY, AssignTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TASK_DESC_IMPLEMENT_X, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TASK_DESC_IMPLEMENT_X, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TASK_DESC_ADD, Task.MESSAGE_CONSTRAINTS); // invalid task

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + TASK_DESC_IMPLEMENT_X
                + INVALID_TASK_DESC_ADD, Task.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_oneTask_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TASK_DESC_IMPLEMENT_X;

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_IMPLEMENT_X).build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTasks_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + TASK_DESC_IMPLEMENT_X + TASK_DESC_IMPLEMENT_Y + TASK_DESC_IMPLEMENT_Z;

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withTasks(VALID_TASK_IMPLEMENT_X, VALID_TASK_IMPLEMENT_Y, VALID_TASK_IMPLEMENT_Z)
                .build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
