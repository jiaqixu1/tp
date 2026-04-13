package seedu.taskforge.logic.parser;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.taskforge.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.taskforge.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.taskforge.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.taskforge.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.taskforge.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.taskforge.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskforge.testutil.TypicalPersons.AMY;
import static seedu.taskforge.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.person.AddCommand;
import seedu.taskforge.logic.parser.person.AddCommandParser;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.testutil.PersonBuilder;

public class AddCommandParserTest {

    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB)
                .withProjects()
                .withTasks()
                .build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        assertParseFailure(parser, NAME_DESC_AMY + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, PHONE_DESC_AMY + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, EMAIL_DESC_AMY + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser,
                validInput + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Person expectedPerson = new PersonBuilder(AMY)
                .withProjects()
                .withTasks()
                .build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,
                INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
