package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Marks a task as done for an existing person in the address book.
 */
public class MarkTaskCommand extends TaskCommand {
    public static final String SUBCOMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Marks the task identified by the task index as done for the person identified by the person index.\n"
            + "Parameters: PERSON_INDEX TASK_INDEX\n"
            + "Example: " + COMMAND_WORD + " " + SUBCOMMAND_WORD + " 1 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked task as done: %1$s";
    public static final String MESSAGE_TASK_ALREADY_DONE = "This task is already marked as done.";

    private final Index personIndex;
    private final Index taskIndex;

    /**
     * @param personIndex of the person in the filtered person list
     * @param taskIndex of the task in the person's task list
     */
    public MarkTaskCommand(Index personIndex, Index taskIndex) {
        requireNonNull(personIndex);
        requireNonNull(taskIndex);

        this.personIndex = personIndex;
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        List<Task> taskList = personToEdit.getTasks();

        if (taskIndex.getZeroBased() >= taskList.size()) {
            throw new CommandException(DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
        }

        Task taskToMark = taskList.get(taskIndex.getZeroBased());
        if (taskToMark.getStatus()) {
            throw new CommandException(MESSAGE_TASK_ALREADY_DONE);
        }

        Person editedPerson = createMarkedPerson(personToEdit, taskIndex);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark.description));
    }

    private static Person createMarkedPerson(Person personToEdit, Index taskIndex) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<Project> projectList = personToEdit.getProjects();
        List<Task> currentTasks = personToEdit.getTasks();

        List<Task> updatedTasks = new ArrayList<>();
        for (int i = 0; i < currentTasks.size(); i++) {
            Task currentTask = currentTasks.get(i);
            if (i == taskIndex.getZeroBased()) {
                Task markedTask = new Task(currentTask.description, currentTask.getProjectTitle());
                markedTask.setDone();
                updatedTasks.add(markedTask);
            } else {
                updatedTasks.add(currentTask);
            }
        }

        return new Person(name, phone, email, projectList, updatedTasks);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkTaskCommand)) {
            return false;
        }

        MarkTaskCommand otherMarkTaskCommand = (MarkTaskCommand) other;
        return personIndex.equals(otherMarkTaskCommand.personIndex)
                && taskIndex.equals(otherMarkTaskCommand.taskIndex);
    }
}
