package seedu.taskforge.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand.UnassignProjectDescriptor;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ProjectDeleteCommand object.
 */
public class UnassignProjectCommandParser implements Parser<UnassignProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProjectDeleteCommand
     * and returns a ProjectDeleteCommand object for execution.
     *
     * @param args The arguments string to be parsed.
     * @return A ProjectDeleteCommand object containing the parsed person index and project index.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public UnassignProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignProjectCommand.MESSAGE_USAGE), pe);
        }

        UnassignProjectDescriptor unassignProjectDescriptor = new UnassignProjectDescriptor();

        parseProjectsIndexesForAdd(argMultimap.getAllValues(PREFIX_INDEX))
                .ifPresent(unassignProjectDescriptor::setProjectsIndexes);

        if (!unassignProjectDescriptor.isProjectFieldEdited()) {
            throw new ParseException(UnassignProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new UnassignProjectCommand(index, unassignProjectDescriptor);
    }

    /**
     * Parses {@code Collection<String> projects} into a {@code List<Project>} if {@code projects} is non-empty.
     * If {@code projects} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Project>} containing zero projects.
     */
    private Optional<List<Index>> parseProjectsIndexesForAdd(Collection<String> projectsIndexes)
            throws ParseException {
        assert projectsIndexes != null;

        if (projectsIndexes.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> projectSet = (projectsIndexes.size() == 1)
                && projectsIndexes.contains("") ? Collections.emptyList() : projectsIndexes;
        return Optional.of(ParserUtil.parseProjectIndexes(projectSet));
    }
}
