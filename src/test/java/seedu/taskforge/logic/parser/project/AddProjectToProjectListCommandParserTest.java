package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.project.AddProjectToProjectListCommand;
import seedu.taskforge.model.project.Project;

public class AddProjectToProjectListCommandParserTest {

    private AddProjectToProjectListCommandParser parser = new AddProjectToProjectListCommandParser();

    @Test
    public void parse_validArgs_returnsAddProjectToProjectListCommand() {
        assertParseSuccess(parser, " alpha ", new AddProjectToProjectListCommand(new Project("alpha")));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectToProjectListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidProject_throwsParseException() {
        assertParseFailure(parser, " project-title ", Project.MESSAGE_CONSTRAINTS);
    }
}
