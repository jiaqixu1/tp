package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.projectcommand.ProjectAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Project;

/**
 * Parses input arguments and creates a new ProjectAddCommand object.
 */
public class ProjectAddCommandParser implements Parser<ProjectAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProjectAddCommand
     * and returns a ProjectAddCommand object for execution.
     *
     * @param args The arguments string to be parsed.
     * @return A ProjectAddCommand object containing the parsed index and project.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ProjectAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT_TITLE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectAddCommand.MESSAGE_USAGE), pe);
        }

        Project project = ParserUtil.parseProject(argMultimap.getValue(PREFIX_PROJECT_TITLE).get());

        return new ProjectAddCommand(index, project);
    }
}
