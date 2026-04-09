package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.ArrayList;
import java.util.List;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.ProjectTaskPair;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignTaskCommand object.
 */
public class AssignTaskCommandParser implements Parser<AssignTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignTaskCommand
     * and returns an AssignTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PROJECT, PREFIX_INDEX);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), pe);
        }

        AssignTaskDescriptor assignTaskDescriptor = new AssignTaskDescriptor();
        parseProjectTaskPairs(argMultimap)
                .ifPresent(assignTaskDescriptor::setProjectTaskPairs);

        if (!assignTaskDescriptor.isTaskFieldEdited()) {
            throw new ParseException(AssignTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new AssignTaskCommand(index, assignTaskDescriptor);
    }

    private java.util.Optional<List<ProjectTaskPair>> parseProjectTaskPairs(ArgumentMultimap argMultimap)
            throws ParseException {
        List<String> projectIndexes = argMultimap.getAllValues(PREFIX_PROJECT);
        List<String> taskIndexes = argMultimap.getAllValues(PREFIX_INDEX);

        if (projectIndexes.isEmpty() && taskIndexes.isEmpty()) {
            return java.util.Optional.empty();
        }

        if (projectIndexes.size() != taskIndexes.size()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));
        }

        List<ProjectTaskPair> pairs = new ArrayList<>();
        for (int i = 0; i < projectIndexes.size(); i++) {
            Index projectIndex = ParserUtil.parseIndex(projectIndexes.get(i));
            Index taskIndex = ParserUtil.parseIndex(taskIndexes.get(i));
            pairs.add(new ProjectTaskPair(projectIndex, taskIndex));
        }

        return pairs.isEmpty() ? java.util.Optional.empty() : java.util.Optional.of(pairs);
    }
}
