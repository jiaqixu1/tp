package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_PROJECT_DELETE;
import static seedu.taskforge.logic.commands.CommandTestUtil.PROJECT_DELETE_1;
import static seedu.taskforge.logic.commands.CommandTestUtil.PROJECT_DELETE_2;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand.UnassignProjectDescriptor;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.testutil.UnassignProjectDescriptorBuilder;

public class UnassignProjectCommandParserTest {
    private static final String PROJECT_EMPTY = " " + PREFIX_INDEX;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignProjectCommand.MESSAGE_USAGE);

    private UnassignProjectCommandParser parser = new UnassignProjectCommandParser();

    @Test
    public void parse_missingProject_failure() {
        // no index specified
        assertParseFailure(parser, PROJECT_DELETE_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", UnassignProjectCommand.MESSAGE_NOT_EDITED);

        // empty field after prefix
        assertParseFailure(parser, "1" + PROJECT_EMPTY, UnassignProjectCommand.MESSAGE_NOT_EDITED);

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
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "1" + INVALID_PROJECT_DELETE, ParserUtil.MESSAGE_INVALID_INDEX); // invalid project

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + PROJECT_DELETE_1
                + INVALID_PROJECT_DELETE, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_oneProject_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PROJECT_DELETE_1;

        UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand expectedCommand = new UnassignProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleProjects_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PROJECT_DELETE_1 + PROJECT_DELETE_2;

        UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2")
                .build();
        UnassignProjectCommand expectedCommand = new UnassignProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
