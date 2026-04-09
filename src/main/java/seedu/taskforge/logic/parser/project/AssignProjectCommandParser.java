package seedu.taskforge.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;

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
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE), pe);
        }

        AssignProjectDescriptor assignProjectDescriptor = new AssignProjectDescriptor();

        parseProjectsIndexesForAssign(argMultimap.getAllValues(PREFIX_INDEX))
                .ifPresent(assignProjectDescriptor::setProjectsIndexes);

        if (!assignProjectDescriptor.isProjectFieldEdited()) {
            throw new ParseException(AssignProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new AssignProjectCommand(index, assignProjectDescriptor);
    }

    private Optional<List<Index>> parseProjectsIndexesForAssign(Collection<String> projectIndexes)
            throws ParseException {
        assert projectIndexes != null;

        if (projectIndexes.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> projectSet = (projectIndexes.size() == 1)
                && projectIndexes.contains("") ? Collections.emptyList() : projectIndexes;
        return Optional.of(ParserUtil.parseProjectIndexes(projectSet));
    }
}
