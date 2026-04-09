package seedu.taskforge.logic.commands;

import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.logic.commands.person.AddCommand;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.TaskForge;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.testutil.PersonBuilder;

public class RedoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaskForge(), new UserPrefs());
        expectedModel = new ModelManager(model.getTaskForge(), new UserPrefs());
    }

    @Test
    public void execute_noUndoHistory_fails() {
        RedoCommand redoCommand = new RedoCommand();

        String expectedMessage = String.format(RedoCommand.MESSAGE_FAILED);

        assertCommandFailure(redoCommand, model, expectedMessage);
    }

    @Test
    public void execute_withUndoHistory_success() throws CommandException {
        RedoCommand redoCommand = new RedoCommand();

        Person person = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(person);
        addCommand.execute(model);
        expectedModel = new ModelManager(new TaskForge(model.getTaskForge()), new UserPrefs());

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, AddCommand.COMMAND_WORD);

        assertCommandSuccess(redoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_commitAfterUndo_fails() throws CommandException {
        RedoCommand redoCommand = new RedoCommand();

        Person person = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(person);
        addCommand.execute(model);

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        AddCommand newAddCommand = new AddCommand(person);
        newAddCommand.execute(model);

        String expectedMessage = String.format(RedoCommand.MESSAGE_FAILED);

        assertCommandFailure(redoCommand, model, expectedMessage);
    }
}
