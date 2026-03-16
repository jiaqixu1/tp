package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TASK_REFACTOR = "refactor code";
    public static final String VALID_TASK_FIX_ERROR = "fix error in tp project";
    public static final String VALID_TASK_IMPLEMENT_X = "implement feature x";
    public static final String VALID_TASK_IMPLEMENT_Y = "implement feature y";
    public static final String VALID_TASK_IMPLEMENT_Z = "implement feature z";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_PROJECT_ALPHA = "alpha";
    public static final String VALID_PROJECT_BETA = "beta";
    public static final String VALID_PROJECT_X = "project x";
    public static final String VALID_PROJECT_Y = "project y";
    public static final String VALID_PROJECT_Z = "project z";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TASK_FIX_ERROR = " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_FIX_ERROR;
    public static final String TASK_REFACTOR = " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_REFACTOR;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String PROJECT_DESC_BETA = " " + PREFIX_PROJECT_TITLE + VALID_PROJECT_BETA;
    public static final String PROJECT_DESC_ALPHA = " " + PREFIX_PROJECT_TITLE + VALID_PROJECT_ALPHA;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TASK_DESC = " " + PREFIX_TASK_DESCRIPTION
            + "refactor code*"; // '*' not allowed in tasks
    public static final String INVALID_TASK_INDEX = " " + PREFIX_TASK + -1;

    // For task related commands not person related
    public static final String TASK_DESC_IMPLEMENT_X = " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_IMPLEMENT_X;
    public static final String TASK_DESC_IMPLEMENT_Y = " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_IMPLEMENT_Y;
    public static final String TASK_DESC_IMPLEMENT_Z = " " + PREFIX_TASK_DESCRIPTION + VALID_TASK_IMPLEMENT_Z;

    public static final String TASK_DELETE_1 = " " + PREFIX_TASK + "1";
    public static final String TASK_DELETE_2 = " " + PREFIX_TASK + "2";
    public static final String INVALID_TASK_DELETE = " " + PREFIX_TASK + "-1";

    // For project related commands not person related
    public static final String PROJECT_DESC_X = " " + PREFIX_PROJECT_TITLE + VALID_PROJECT_X;
    public static final String PROJECT_DESC_Y = " " + PREFIX_PROJECT_TITLE + VALID_PROJECT_Y;
    public static final String PROJECT_DESC_Z = " " + PREFIX_PROJECT_TITLE + VALID_PROJECT_Z;

    public static final String PROJECT_DELETE_1 = " " + PREFIX_PROJECT_INDEX + "1";
    public static final String PROJECT_DELETE_2 = " " + PREFIX_PROJECT_INDEX + "2";
    public static final String INVALID_PROJECT_DELETE = " " + PREFIX_PROJECT_INDEX + "-1";

    public static final String INVALID_TASK_DESC_ADD = " " + PREFIX_TASK_DESCRIPTION
            + "refactor code*"; // '*' not allowed in tasks
    public static final String INVALID_PROJECT_TITLE = " " + PREFIX_PROJECT_TITLE
            + "alpha*"; // '*' not allowed in projects
    public static final String INVALID_PROJECT_INDEX = " " + PREFIX_PROJECT_INDEX + -1;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withProjects(VALID_PROJECT_ALPHA).withTasks(VALID_TASK_FIX_ERROR).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withProjects(VALID_PROJECT_ALPHA).withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
