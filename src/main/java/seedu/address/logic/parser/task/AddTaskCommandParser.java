package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;

public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    public AddTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE), pe);
        }

        AddTaskDescriptor addTaskDescriptor = new AddTaskDescriptor();

        parseTasksForAdd(argMultimap.getAllValues(PREFIX_TASK_DESCRIPTION)).ifPresent(addTaskDescriptor::setTasks);

        if (!addTaskDescriptor.isTaskFieldEdited()) {
            throw new ParseException(AddTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new AddTaskCommand(index, addTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tasks} into a {@code Set<Task>} if {@code tasks} is non-empty.
     * If {@code tasks} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Task>} containing zero tasks.
     */
    private Optional<Set<Task>> parseTasksForAdd(Collection<String> tasks) throws ParseException {
        assert tasks != null;

        if (tasks.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> taskSet = tasks.size() == 1 && tasks.contains("") ? Collections.emptySet() : tasks;
        return Optional.of(ParserUtil.parseTasks(taskSet));
    }
}
