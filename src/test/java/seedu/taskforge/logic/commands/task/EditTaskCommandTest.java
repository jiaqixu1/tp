package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.taskforge.testutil.TypicalPersons.ALICE;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class EditTaskCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editTask_successAndReassignsPeople() {
        Project targetProject = new Project(VALID_PROJECT_ALPHA);
        Project projectInModel = model.getProjectList().stream()
                .filter(project -> project.equals(targetProject))
                .findFirst()
                .orElseThrow();
        Person aliceInModel = model.getAddressBook().getPersonList().stream()
                .filter(person -> person.isSamePerson(ALICE))
                .findFirst()
                .orElseThrow();
        Person trackedAlice = new Person(aliceInModel.getName(), aliceInModel.getPhone(), aliceInModel.getEmail(),
                aliceInModel.getProjects(), Arrays.asList(new Task(VALID_TASK_REFACTOR, projectInModel.title)));
        model.setPerson(aliceInModel, trackedAlice);

        Task newTask = new Task(VALID_TASK_IMPLEMENT_X);
        EditTaskCommand editTaskCommand = new EditTaskCommand(targetProject, INDEX_FIRST_TASK, newTask);

        Project expectedEditedProject = new Project(VALID_PROJECT_ALPHA,
                Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X), new Task(VALID_TASK_FIX_ERROR)));
        Person expectedAlice = new Person(trackedAlice.getName(), trackedAlice.getPhone(), trackedAlice.getEmail(),
                trackedAlice.getProjects(), Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X, projectInModel.title)));

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_SUCCESS, expectedEditedProject);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Project expectedProjectInModel = expectedModel.getProjectList().stream()
                .filter(project -> project.equals(targetProject))
                .findFirst()
                .orElseThrow();
        expectedModel.setProject(expectedProjectInModel, expectedEditedProject);

        Person expectedAliceInModel = expectedModel.getAddressBook().getPersonList().stream()
                .filter(person -> person.isSamePerson(trackedAlice))
                .findFirst()
                .orElseThrow();
        expectedModel.setPerson(expectedAliceInModel, expectedAlice);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_projectNotFound_failure() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(new Project("nonexistent"), INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_PROJECT_NOT_FOUND);
    }

    @Test
    public void execute_taskIndexOutOfBound_failure() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(new Project(VALID_PROJECT_ALPHA),
                Index.fromOneBased(99), new Task(VALID_TASK_IMPLEMENT_X));

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_duplicateTaskNameInProject_failure() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(new Project(VALID_PROJECT_ALPHA), INDEX_FIRST_TASK,
                new Task(VALID_TASK_FIX_ERROR));

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void equals() {
        EditTaskCommand firstCommand = new EditTaskCommand(new Project(VALID_PROJECT_ALPHA), INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));
        EditTaskCommand sameValuesCommand = new EditTaskCommand(new Project(VALID_PROJECT_ALPHA), INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));
        EditTaskCommand differentCommand = new EditTaskCommand(new Project(VALID_PROJECT_ALPHA), INDEX_FIRST_TASK,
                new Task(VALID_TASK_REFACTOR));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(sameValuesCommand));
        assertFalse(firstCommand.equals(differentCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals((Object) null));
    }
}


