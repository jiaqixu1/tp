package seedu.taskforge.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.taskforge.logic.commands.Command;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
