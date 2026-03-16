package seedu.address.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.project.DeleteProjectCommand;
import seedu.address.logic.commands.project.DeleteProjectCommand.DeleteProjectDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ProjectDeleteCommand object.
 */
public class DeleteProjectCommandParser implements Parser<DeleteProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProjectDeleteCommand
     * and returns a ProjectDeleteCommand object for execution.
     *
     * @param args The arguments string to be parsed.
     * @return A ProjectDeleteCommand object containing the parsed person index and project index.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public DeleteProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteProjectCommand.MESSAGE_USAGE), pe);
        }

        DeleteProjectDescriptor deleteProjectDescriptor = new DeleteProjectDescriptor();

        parseProjectsIndexesForAdd(argMultimap.getAllValues(PREFIX_PROJECT_INDEX))
                .ifPresent(deleteProjectDescriptor::setProjectsIndexes);

        if (!deleteProjectDescriptor.isProjectFieldEdited()) {
            throw new ParseException(DeleteProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new DeleteProjectCommand(index, deleteProjectDescriptor);
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
