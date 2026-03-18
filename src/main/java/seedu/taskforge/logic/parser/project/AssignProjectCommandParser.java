package seedu.taskforge.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.AssignProjectCommand;
import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.project.Project;

/**
 * Parses input arguments and creates a new AssignProjectCommand object.
 */
public class AssignProjectCommandParser implements Parser<AssignProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignProjectCommand
     * and returns a AssignProjectCommand object for execution.
     *
     * @param args The arguments string to be parsed.
     * @return A AssignProjectCommand object containing the parsed index and project.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AssignProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE), pe);
        }

        AssignProjectDescriptor assignProjectDescriptor = new AssignProjectDescriptor();

        parseProjectsForAdd(argMultimap.getAllValues(PREFIX_NAME))
                .ifPresent(assignProjectDescriptor::setProjects);

        if (!assignProjectDescriptor.isProjectFieldEdited()) {
            throw new ParseException(AssignProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new AssignProjectCommand(index, assignProjectDescriptor);
    }

    /**
     * Parses {@code Collection<String> projects} into a {@code List<Project>} if {@code projects} is non-empty.
     * If {@code projects} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Project>} containing zero projects.
     */
    private Optional<List<Project>> parseProjectsForAdd(Collection<String> projects) throws ParseException {
        assert projects != null;

        if (projects.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> projectSet = (projects.size() == 1)
                && projects.contains("") ? Collections.emptyList() : projects;
        return Optional.of(ParserUtil.parseProjects(projectSet));
    }
}
