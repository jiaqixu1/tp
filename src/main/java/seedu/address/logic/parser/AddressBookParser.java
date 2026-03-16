package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.ClearCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.project.AddProjectCommand;
import seedu.address.logic.commands.project.DeleteProjectCommand;
import seedu.address.logic.commands.project.ProjectCommand;
import seedu.address.logic.commands.tag.AddTagCommand;
import seedu.address.logic.commands.tag.DeleteTagCommand;
import seedu.address.logic.commands.tag.ViewAllTagCommand;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.person.AddCommandParser;
import seedu.address.logic.parser.person.DeleteCommandParser;
import seedu.address.logic.parser.person.EditCommandParser;
import seedu.address.logic.parser.person.FindCommandParser;
import seedu.address.logic.parser.project.AddProjectCommandParser;
import seedu.address.logic.parser.project.DeleteProjectCommandParser;
import seedu.address.logic.parser.task.AddTaskCommandParser;
import seedu.address.logic.parser.task.DeleteTaskCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        // Person related commands
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        // Task related commands
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        // Project related commands
        case ProjectCommand.COMMAND_WORD:
            return handleProject(arguments);

        // Address book related commands
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case ViewAllTagCommand.COMMAND_WORD:
            return new ViewAllTagCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command handleProject(String subinput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(subinput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectCommand.MESSAGE_USAGE));
        }

        final String subcommandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (subcommandWord) {

        case AddProjectCommand.SUBCOMMAND_WORD:
            return new AddProjectCommandParser().parse(arguments);

        case DeleteProjectCommand.SUBCOMMAND_WORD:
            return new DeleteProjectCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: project " + subinput);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectCommand.MESSAGE_USAGE));
        }
    }

}
