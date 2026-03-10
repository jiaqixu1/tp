package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.projectcommand.ProjectAddCommand;
import seedu.address.logic.commands.projectcommand.ProjectDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class ProjectDeleteCommandParser implements Parser<ProjectDeleteCommand> {

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
