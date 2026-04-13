package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_TASK_DELETE;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DELETE_1;
import static seedu.taskforge.logic.commands.CommandTestUtil.TASK_DELETE_2;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.taskforge.logic.parser.ParserUtil;

public class DeleteTaskCommandParserTest {
    private static final String TASK_EMPTY = " " + PREFIX_INDEX;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_missingTask_failure() {
        assertParseFailure(parser, TASK_DELETE_1, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", DeleteTaskCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "1" + TASK_EMPTY, DeleteTaskCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-5" + TASK_DELETE_1, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0" + TASK_DELETE_1, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 some random string", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 i/ string", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "1" + INVALID_TASK_DELETE, ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1" + TASK_DELETE_1 + INVALID_TASK_DELETE, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_oneTask_success() {
        Index targetIndex = INDEX_SECOND_PROJECT;
        String userInput = targetIndex.getOneBased() + TASK_DELETE_1;

        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptor();
        descriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(1)));
        DeleteTaskCommand expectedCommand = new DeleteTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTasks_success() {
        Index targetIndex = INDEX_SECOND_PROJECT;
        String userInput = targetIndex.getOneBased() + TASK_DELETE_1 + TASK_DELETE_2;

        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptor();
        descriptor.setTaskIndexes(Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2)));
        DeleteTaskCommand expectedCommand = new DeleteTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
