package seedu.taskforge.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.taskforge.logic.commands.person.FindCommand;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_TASK, PREFIX_PROJECT_TITLE);

        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = getKeywords(argMultimap.getAllValues(PREFIX_NAME));
            if (nameKeywords.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            predicate.setNameKeywords(nameKeywords);
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> phoneKeywords = getKeywords(argMultimap.getAllValues(PREFIX_PHONE));
            if (phoneKeywords.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            predicate.setPhoneKeywords(phoneKeywords);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            List<String> emailKeywords = getKeywords(argMultimap.getAllValues(PREFIX_EMAIL));
            if (emailKeywords.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            predicate.setEmailKeywords(emailKeywords);
        }

        if (argMultimap.getValue(PREFIX_TASK).isPresent()) {
            List<String> taskKeywords = getKeywords(argMultimap.getAllValues(PREFIX_TASK));
            if (taskKeywords.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            predicate.setTaskKeywords(taskKeywords);
        }

        if (argMultimap.getValue(PREFIX_PROJECT_TITLE).isPresent()) {
            List<String> projectKeywords = getKeywords(argMultimap.getAllValues(PREFIX_PROJECT_TITLE));
            if (projectKeywords.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            predicate.setProjectKeywords(projectKeywords);
        }

        if (!predicate.isAnyFieldChecked() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }

    private List<String> getKeywords(List<String> values) {
        return values.stream()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
