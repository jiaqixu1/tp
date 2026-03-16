package seedu.address.logic.parser.project;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_X;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_Y;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_DESC_Z;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_Y;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_Z;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.project.AddProjectCommand;
import seedu.address.model.project.Project;
import seedu.address.testutil.AddProjectDescriptorBuilder;

public class AddProjectCommandParserTest {
    private static final String PROJECT_EMPTY = " " + PREFIX_PROJECT_TITLE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE);

    private AddProjectCommandParser parser = new AddProjectCommandParser();

    @Test
    public void parse_missingProject_failure() {
        // no index specified
        assertParseFailure(parser, PROJECT_DESC_X, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AddProjectCommand.MESSAGE_NOT_EDITED);

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
        assertParseFailure(parser, "1" + INVALID_PROJECT_TITLE, Project.MESSAGE_CONSTRAINTS); // invalid project

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + PROJECT_DESC_X
                + INVALID_PROJECT_TITLE, Project.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_oneProject_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PROJECT_DESC_X;

        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_X).build();
        AddProjectCommand expectedCommand = new AddProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleProjects_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + PROJECT_DESC_X + PROJECT_DESC_Y + PROJECT_DESC_Z;

        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z)
                .build();
        AddProjectCommand expectedCommand = new AddProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
