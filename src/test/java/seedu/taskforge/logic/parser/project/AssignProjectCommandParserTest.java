package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_PROJECT_INDEX;
import static seedu.taskforge.logic.commands.CommandTestUtil.PROJECT_DELETE_1;
import static seedu.taskforge.logic.commands.CommandTestUtil.PROJECT_DELETE_2;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.AssignProjectCommand;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.testutil.AssignProjectDescriptorBuilder;

public class AssignProjectCommandParserTest {
    private static final String PROJECT_EMPTY = " " + PREFIX_INDEX;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE);

    private AssignProjectCommandParser parser = new AssignProjectCommandParser();

    @Test
    public void parse_missingProject_failure() {
        // no index specified
        assertParseFailure(parser, PROJECT_DELETE_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AssignProjectCommand.MESSAGE_NOT_EDITED);

        // empty field after prefix
        assertParseFailure(parser, "1" + PROJECT_EMPTY, AssignProjectCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PROJECT_DELETE_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PROJECT_DELETE_1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PROJECT_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + PROJECT_DELETE_1 + INVALID_PROJECT_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_oneProject_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PROJECT_DELETE_1;

        AssignProjectCommand.AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjectIndexes(String.valueOf(INDEX_FIRST_PROJECT.getOneBased())).build();
        AssignProjectCommand expectedCommand = new AssignProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleProjects_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + PROJECT_DELETE_1 + PROJECT_DELETE_2;

        AssignProjectCommand.AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjectIndexes(String.valueOf(INDEX_FIRST_PROJECT.getOneBased()),
                        String.valueOf(INDEX_SECOND_PROJECT.getOneBased()))
                .build();
        AssignProjectCommand expectedCommand = new AssignProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
