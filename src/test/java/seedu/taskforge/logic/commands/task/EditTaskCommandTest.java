package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.TaskForge;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class EditTaskCommandTest {

    private final Model model = new ModelManager(getTypicalTaskForge(), new UserPrefs());

    @Test
    public void execute_editTask_success() {
        Task newTask = new Task(VALID_TASK_IMPLEMENT_X);
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK, newTask);

        Person expectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonTask expectedPersonTask = expectedPerson.getTasks().get(INDEX_FIRST_TASK.getZeroBased());
        Project expectedProjectInModel = model.getProjectList().get(expectedPersonTask.getProjectIndex());
        Task taskToEdit = expectedProjectInModel.getTasks().get(expectedPersonTask.getTaskIndex());
        Task renamedTaskWithProject = new Task(VALID_TASK_IMPLEMENT_X, expectedProjectInModel.title);
        if (taskToEdit.getStatus()) {
            renamedTaskWithProject.setDone();
        }

        Project expectedEditedProject = new Project(expectedProjectInModel.title, expectedProjectInModel.getTasks());
        expectedEditedProject.getUniqueTaskList().setTask(taskToEdit, renamedTaskWithProject);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_SUCCESS, expectedEditedProject);

        Model expectedModel = new ModelManager(new TaskForge(model.getTaskForge()), new UserPrefs());
        Person copiedExpectedPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonTask copiedExpectedPersonTask = copiedExpectedPerson.getTasks().get(INDEX_FIRST_TASK.getZeroBased());
        Project copiedExpectedProject = expectedModel.getProjectList().get(copiedExpectedPersonTask.getProjectIndex());
        expectedModel.setProject(copiedExpectedProject, expectedEditedProject);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editTask_keepsPersonTaskAssignment() throws Exception {
        Person personBeforeEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        int taskCountBeforeEdit = personBeforeEdit.getTasks().size();

        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));
        editTaskCommand.execute(model);

        Person personAfterEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(taskCountBeforeEdit, personAfterEdit.getTasks().size());
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundPersonIndex, INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskIndexOutOfBound_failure() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(99), new Task(VALID_TASK_IMPLEMENT_X));

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_duplicateTaskNameInProject_failure() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_FIX_ERROR));

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidProjectReference_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person malformedPerson = new Person(firstPerson.getName(), firstPerson.getPhone(), firstPerson.getEmail(),
                firstPerson.getProjects(), List.of(new PersonTask(model.getProjectList().size(), 0)));

        TaskForge malformedTaskForge = new TaskForge(model.getTaskForge());
        malformedTaskForge.setPersons(List.of(malformedPerson));
        Model malformedModel = new ModelManager(malformedTaskForge, new UserPrefs());

        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));

        assertCommandFailure(editTaskCommand, malformedModel, EditTaskCommand.MESSAGE_INVALID_TASK_REFERENCE);
    }

    @Test
    public void execute_invalidTaskReference_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person malformedPerson = new Person(firstPerson.getName(), firstPerson.getPhone(), firstPerson.getEmail(),
                firstPerson.getProjects(), List.of(new PersonTask(0, model.getProjectList().get(0).getTasks().size())));

        TaskForge malformedTaskForge = new TaskForge(model.getTaskForge());
        malformedTaskForge.setPersons(List.of(malformedPerson));
        Model malformedModel = new ModelManager(malformedTaskForge, new UserPrefs());

        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));

        assertCommandFailure(editTaskCommand, malformedModel, EditTaskCommand.MESSAGE_INVALID_TASK_REFERENCE);
    }

    @Test
    public void equals() {
        EditTaskCommand firstCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));
        EditTaskCommand sameValuesCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));
        EditTaskCommand differentPersonIndexCommand = new EditTaskCommand(INDEX_SECOND_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));
        EditTaskCommand differentTaskIndexCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_SECOND_TASK,
                new Task(VALID_TASK_IMPLEMENT_X));
        EditTaskCommand differentCommand = new EditTaskCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK,
                new Task(VALID_TASK_REFACTOR));

        assertEquals(firstCommand, sameValuesCommand);
        assertNotEquals(firstCommand, differentPersonIndexCommand);
        assertNotEquals(firstCommand, differentTaskIndexCommand);
        assertNotEquals(firstCommand, differentCommand);
        assertNotEquals(1, firstCommand);
        assertNotEquals(null, firstCommand);
        assertEquals(firstCommand.hashCode(), sameValuesCommand.hashCode());
    }
}


