package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.model.task.exceptions.DuplicateTaskException;

/**
 * Edits an existing task name from an existing project in the address book.
 */
public class EditTaskCommand extends TaskCommand {
    public static final String SUBCOMMAND_WORD = "edit";

    public static final String MESSAGE_SUCCESS = "Task edited in project: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + SUBCOMMAND_WORD + " PERSON_INDEX TASK_INDEX -n NEW_TASK_NAME";
    public static final String MESSAGE_INDEX_OUT_OF_BOUND = "Task index is out of bound";
    public static final String MESSAGE_INVALID_TASK_REFERENCE = "This task reference is invalid.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists for this project!";

    private final Index personIndex;
    private final Index taskIndex;
    private final Task newTask;

    /**
     * Creates an EditTaskCommand to edit a task in a project.
     */
    public EditTaskCommand(Index personIndex, Index taskIndex, Task newTask) {
        requireNonNull(personIndex);
        requireNonNull(taskIndex);
        requireNonNull(newTask);

        this.personIndex = personIndex;
        this.taskIndex = taskIndex;
        this.newTask = newTask;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        List<PersonTask> personTaskList = personToEdit.getTasks();
        if (taskIndex.getZeroBased() >= personTaskList.size()) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_BOUND);
        }

        PersonTask personTask = personTaskList.get(taskIndex.getZeroBased());
        Project projectToEdit = resolveProject(model, personTask);
        Task taskToEdit = resolveTask(model, personTask);

        Task renamedTaskWithProject = createTaskWithProjectAndStatus(newTask.description,
                projectToEdit.title, taskToEdit);
        Project editedProject = createEditedProject(projectToEdit, taskToEdit, renamedTaskWithProject);

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        model.commitAddressBook(String.format("%s %s", COMMAND_WORD, SUBCOMMAND_WORD));
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedProject));
    }

    private static Project resolveProject(Model model, PersonTask personTask) throws CommandException {
        int projectIndex = personTask.getProjectIndex();
        if (projectIndex < 0 || projectIndex >= model.getProjectList().size()) {
            throw new CommandException(MESSAGE_INVALID_TASK_REFERENCE);
        }
        return model.getProjectList().get(projectIndex);
    }

    private static Task resolveTask(Model model, PersonTask personTask) throws CommandException {
        int taskIndex = personTask.getTaskIndex();
        List<Task> projectTasks = resolveProject(model, personTask).getTasks();
        if (taskIndex < 0 || taskIndex >= projectTasks.size()) {
            throw new CommandException(MESSAGE_INVALID_TASK_REFERENCE);
        }
        return projectTasks.get(taskIndex);
    }

    private static Project createEditedProject(Project projectToEdit, Task taskToEdit, Task renamedTask)
            throws CommandException {
        Project editedProject = new Project(projectToEdit.title, projectToEdit.getTasks());
        try {
            editedProject.getUniqueTaskList().setTask(taskToEdit, renamedTask);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return editedProject;
    }

    private static Task createTaskWithProjectAndStatus(String description, String projectTitle, Task sourceTask) {
        Task task = new Task(description, projectTitle);
        if (sourceTask.getStatus()) {
            task.setDone();
        }
        return task;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        EditTaskCommand otherEditTaskCommand = (EditTaskCommand) other;
        return personIndex.equals(otherEditTaskCommand.personIndex)
                && taskIndex.equals(otherEditTaskCommand.taskIndex)
                && newTask.equals(otherEditTaskCommand.newTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personIndex, taskIndex, newTask);
    }
}

