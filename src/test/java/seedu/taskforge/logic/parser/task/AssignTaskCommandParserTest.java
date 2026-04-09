package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.ProjectTaskPair;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.testutil.AssignTaskDescriptorBuilder;

public class AssignTaskCommandParserTest {
    private static final String PROJECT_TASK_1 = " " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "1";
    private static final String PROJECT_TASK_2 = " " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "2";
    private static final String INVALID_PROJECT = " " + PREFIX_PROJECT + "abc " + PREFIX_INDEX + "1";
    private static final String INVALID_TASK = " " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "abc";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE);

    private final AssignTaskCommandParser parser = new AssignTaskCommandParser();

    @Test
    public void parse_missingTask_failure() {
        // no index specified
        assertParseFailure(parser, PROJECT_TASK_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AssignTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PROJECT_TASK_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PROJECT_TASK_1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PROJECT, ParserUtil.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "1" + INVALID_TASK, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_oneTask_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PROJECT_TASK_1;

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withProjectTaskPairs(
                        new ProjectTaskPair(Index.fromOneBased(1), INDEX_FIRST_TASK))
                .build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleTasks_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + PROJECT_TASK_1 + PROJECT_TASK_2;

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withProjectTaskPairs(
                        new ProjectTaskPair(Index.fromOneBased(1), INDEX_FIRST_TASK),
                        new ProjectTaskPair(Index.fromOneBased(1), INDEX_SECOND_TASK))
                .build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_mismatchedProjectTaskPairs_failure() {
        // More project indices than task indices
        assertParseFailure(parser, "1 " + PREFIX_PROJECT + "1 " + PREFIX_PROJECT + "2 " + PREFIX_INDEX + "1",
                MESSAGE_INVALID_FORMAT);

        // More task indices than project indices
        assertParseFailure(parser, "1 " + PREFIX_INDEX + "1 " + PREFIX_INDEX + "2 " + PREFIX_PROJECT + "1",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_differentProjectIndices_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "1"
                + " " + PREFIX_PROJECT + "2 " + PREFIX_INDEX + "1";

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withProjectTaskPairs(
                        new ProjectTaskPair(Index.fromOneBased(1), INDEX_FIRST_TASK),
                        new ProjectTaskPair(Index.fromOneBased(2), INDEX_FIRST_TASK))
                .build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_largeIndices_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_PROJECT + "10 " + PREFIX_INDEX + "20";

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withProjectTaskPairs(
                        new ProjectTaskPair(Index.fromOneBased(10), Index.fromOneBased(20)))
                .build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_projectAndTaskAtBoundary_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "1";

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withProjectTaskPairs(
                        new ProjectTaskPair(Index.fromOneBased(1), Index.fromOneBased(1)))
                .build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noProjectTaskPairs_failure() {
        // Only person index, no pairs
        assertParseFailure(parser, "1", AssignTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidProjectIndexFormat_failure() {
        // Non-numeric project index
        assertParseFailure(parser, "1 " + PREFIX_PROJECT + "notanumber " + PREFIX_INDEX + "1",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // Non-numeric with invalid format
        assertParseFailure(parser, "1 " + PREFIX_PROJECT + "abc123 " + PREFIX_INDEX + "1",
                ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidTaskIndexFormat_failure() {
        // Non-numeric task index
        assertParseFailure(parser, "1 " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "notanumber",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // Non-numeric with invalid format
        assertParseFailure(parser, "1 " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "abc123",
                ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_threeProjectTaskPairs_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "1"
                + " " + PREFIX_PROJECT + "1 " + PREFIX_INDEX + "2"
                + " " + PREFIX_PROJECT + "2 " + PREFIX_INDEX + "1";

        AssignTaskDescriptor descriptor = new AssignTaskDescriptorBuilder()
                .withProjectTaskPairs(
                        new ProjectTaskPair(Index.fromOneBased(1), INDEX_FIRST_TASK),
                        new ProjectTaskPair(Index.fromOneBased(1), INDEX_SECOND_TASK),
                        new ProjectTaskPair(Index.fromOneBased(2), INDEX_FIRST_TASK))
                .build();
        AssignTaskCommand expectedCommand = new AssignTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
