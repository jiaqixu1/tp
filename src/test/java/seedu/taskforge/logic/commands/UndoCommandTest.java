package seedu.taskforge.logic.commands;

import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.logic.commands.person.AddCommand;
import seedu.taskforge.logic.commands.person.FindCommand;
import seedu.taskforge.logic.commands.person.ListCommand;
import seedu.taskforge.logic.commands.project.ListProjectCommand;
import seedu.taskforge.logic.commands.task.ViewTasksCommand;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonContainsKeywordsPredicate;
import seedu.taskforge.testutil.PersonBuilder;

public class UndoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noCommitHistory_fails() {
        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage = String.format(UndoCommand.MESSAGE_FAILED);

        assertCommandFailure(undoCommand, model, expectedMessage);
    }

    @Test
    public void execute_withCommitHistory_success() throws CommandException {
        UndoCommand undoCommand = new UndoCommand();
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Person person = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(person);
        addCommand.execute(model);

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, AddCommand.COMMAND_WORD);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonModifyingCommand_noCommit() throws CommandException {
        UndoCommand undoCommand = new UndoCommand();

        FindCommand findCommand = new FindCommand(new PersonContainsKeywordsPredicate());
        ListCommand listCommand = new ListCommand();
        ListProjectCommand listProjectCommand = new ListProjectCommand();
        ViewTasksCommand viewTasksCommand = new ViewTasksCommand(INDEX_FIRST_PERSON);
        HelpCommand helpCommand = new HelpCommand();
        ExitCommand exitCommand = new ExitCommand();

        findCommand.execute(model);
        listCommand.execute(model);
        listProjectCommand.execute(model);
        viewTasksCommand.execute(model);
        helpCommand.execute(model);
        exitCommand.execute(model);

        String expectedMessage = String.format(UndoCommand.MESSAGE_FAILED);

        assertCommandFailure(undoCommand, model, expectedMessage);
    }
}
