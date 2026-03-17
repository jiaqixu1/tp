package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME;
import static seedu.taskforge.logic.commands.CommandTestUtil.PROJECT_DESC_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.PROJECT_DESC_Y;
import static seedu.taskforge.logic.commands.CommandTestUtil.PROJECT_DESC_Z;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_Y;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_Z;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.AssignProjectCommand;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.testutil.AssignProjectDescriptorBuilder;

public class AssignProjectCommandParserTest {
    private static final String PROJECT_EMPTY = " " + PREFIX_NAME;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE);

    private AssignProjectCommandParser parser = new AssignProjectCommandParser();

    @Test
    public void parse_missingProject_failure() {
        // no index specified
        assertParseFailure(parser, PROJECT_DESC_X, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AssignProjectCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PROJECT_DESC_X, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PROJECT_DESC_X, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PROJECT_NAME, Project.MESSAGE_CONSTRAINTS); // invalid project

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + PROJECT_DESC_X
                + INVALID_PROJECT_NAME, Project.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_oneProject_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PROJECT_DESC_X;

        AssignProjectCommand.AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_X).build();
        AssignProjectCommand expectedCommand = new AssignProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleProjects_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + PROJECT_DESC_X + PROJECT_DESC_Y + PROJECT_DESC_Z;

        AssignProjectCommand.AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z)
                .build();
        AssignProjectCommand expectedCommand = new AssignProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
