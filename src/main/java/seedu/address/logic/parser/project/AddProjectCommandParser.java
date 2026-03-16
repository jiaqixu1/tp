package seedu.address.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.project.AddProjectCommand;
import seedu.address.logic.commands.project.AddProjectCommand.AddProjectDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Project;

/**
 * Parses input arguments and creates a new AddProjectCommand object.
 */
public class AddProjectCommandParser implements Parser<AddProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddProjectCommand
     * and returns a AddProjectCommand object for execution.
     *
     * @param args The arguments string to be parsed.
     * @return A AddProjectCommand object containing the parsed index and project.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AddProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT_TITLE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE), pe);
        }

        AddProjectDescriptor addProjectDescriptor = new AddProjectCommand.AddProjectDescriptor();

        parseProjectsForAdd(argMultimap.getAllValues(PREFIX_PROJECT_TITLE))
                .ifPresent(addProjectDescriptor::setProjects);

        if (!addProjectDescriptor.isProjectFieldEdited()) {
            throw new ParseException(AddProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new AddProjectCommand(index, addProjectDescriptor);
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
