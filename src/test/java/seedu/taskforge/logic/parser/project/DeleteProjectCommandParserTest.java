package seedu.taskforge.logic.parser.project;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.project.DeleteProjectCommand;

public class DeleteProjectCommandParserTest {

    private DeleteProjectCommandParser parser = new DeleteProjectCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteProjectCommand() {
        assertParseSuccess(parser, " 1 ", new DeleteProjectCommand(INDEX_FIRST_PROJECT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProjectCommand.MESSAGE_USAGE));
    }
}

