package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.CARL;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonTask;

public class ViewTasksCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexWithTasks_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewTasksCommand command = new ViewTasksCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewTasksCommand.MESSAGE_TASKS_HEADER,
                firstPerson.getName().fullName, "[ ] refactor code");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexWithDoneTask_success() {
        Model mutatedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        mutatedModel.getProjectList().get(0).getUniqueTaskList().iterator().next().setDone();

        Person firstPerson = mutatedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewTasksCommand command = new ViewTasksCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewTasksCommand.MESSAGE_TASKS_HEADER,
                firstPerson.getName().fullName, "[X] refactor code");

        Model expectedModel = new ModelManager(new AddressBook(mutatedModel.getAddressBook()), new UserPrefs());
        assertCommandSuccess(command, mutatedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexWithNoTasks_success() {
        int carlIndex = findFilteredPersonIndex(CARL);
        Index target = Index.fromZeroBased(carlIndex);
        ViewTasksCommand command = new ViewTasksCommand(target);

        String expectedMessage = String.format(ViewTasksCommand.MESSAGE_NO_TASKS, CARL.getName().fullName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskReference_success() {
        Model mutatedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person originalPerson = mutatedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person invalidReferencedPerson = new Person(originalPerson.getName(), originalPerson.getPhone(),
                originalPerson.getEmail(), originalPerson.getProjects(),
                java.util.List.of(new PersonTask(9, 9)));
        mutatedModel.setPerson(originalPerson, invalidReferencedPerson);

        ViewTasksCommand command = new ViewTasksCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(ViewTasksCommand.MESSAGE_TASKS_HEADER,
                invalidReferencedPerson.getName().fullName, "[invalid-task-reference]");

        Model expectedModel = new ModelManager(new AddressBook(mutatedModel.getAddressBook()), new UserPrefs());
        assertCommandSuccess(command, mutatedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewTasksCommand command = new ViewTasksCommand(invalidIndex);
        assertCommandFailure(command, model, ViewTasksCommand.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void equals() {
        ViewTasksCommand firstCommand = new ViewTasksCommand(INDEX_FIRST_PERSON);
        ViewTasksCommand sameValuesCommand = new ViewTasksCommand(INDEX_FIRST_PERSON);
        ViewTasksCommand differentCommand = new ViewTasksCommand(Index.fromOneBased(2));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(sameValuesCommand));
        assertEquals(firstCommand.hashCode(), sameValuesCommand.hashCode());
        assertFalse(firstCommand.equals(differentCommand));
        assertFalse(firstCommand.equals(1));
    }

    private int findFilteredPersonIndex(Person personToFind) {
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            if (model.getFilteredPersonList().get(i).isSamePerson(personToFind)) {
                return i;
            }
        }
        throw new AssertionError("Expected person not found in filtered person list");
    }
}
