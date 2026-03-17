package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.project.DeleteProjectFromProjectListCommand;

public class DeleteProjectFromProjectListCommandParserTest {

    private DeleteProjectFromProjectListCommandParser parser = new DeleteProjectFromProjectListCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteProjectFromProjectListCommand() {
        assertParseSuccess(parser, " 1 ", new DeleteProjectFromProjectListCommand(INDEX_FIRST_PROJECT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProjectFromProjectListCommand.MESSAGE_USAGE));
    }
}
