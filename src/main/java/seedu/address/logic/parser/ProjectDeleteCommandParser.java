package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.projectcommand.ProjectDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ProjectDeleteCommand object.
 */
public class ProjectDeleteCommandParser implements Parser<ProjectDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProjectDeleteCommand
     * and returns a ProjectDeleteCommand object for execution.
     *
     * @param args The arguments string to be parsed.
     * @return A ProjectDeleteCommand object containing the parsed person index and project index.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ProjectDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT_INDEX);

        Index personIndex;
        Index projectIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            projectIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROJECT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectDeleteCommand.MESSAGE_USAGE), pe);
        }
        return new ProjectDeleteCommand(personIndex, projectIndex);
    }
}
