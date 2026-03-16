package seedu.address.logic.parser.tag;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.tag.AddTagCommand;
import seedu.address.logic.parser.AddTagCommandParser;
import seedu.address.model.tag.Tag;

public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_validArgs_returnsAddTagCommand() {
        assertParseSuccess(parser, " friends ", new AddTagCommand(new Tag("friends")));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        assertParseFailure(parser, " friend-tag ", Tag.MESSAGE_CONSTRAINTS);
    }
}
