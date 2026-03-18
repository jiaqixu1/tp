package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.project.AddProjectCommand;
import seedu.taskforge.model.project.Project;

public class AddProjectCommandParserTest {

    private AddProjectCommandParser parser = new AddProjectCommandParser();

    @Test
    public void parse_validArgs_returnsAddProjectCommand() {
        assertParseSuccess(parser, " alpha ", new AddProjectCommand(new Project("alpha")));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidProject_throwsParseException() {
        assertParseFailure(parser, " project-title ", Project.MESSAGE_CONSTRAINTS);
    }
}

