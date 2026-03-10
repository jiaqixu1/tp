package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Views all tags of a specific person identified by index in the currently displayed person list.
 */
public class ViewTagsCommand extends Command {

    public static final String COMMAND_WORD = "viewtags";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all tags of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_NO_TAGS = "%s has no tags.";
    public static final String MESSAGE_TAGS_HEADER = "Tags for %s: %s";

    private final Index targetIndex;

    public ViewTagsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        var lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        Set<Tag> tags = person.getTags();

        if (tags.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_TAGS, person.getName().fullName));
        }

        String tagString = tags.stream()
                .map(Tag::toString)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(" "));

        return new CommandResult(String.format(MESSAGE_TAGS_HEADER, person.getName().fullName, tagString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewTagsCommand
                && targetIndex.equals(((ViewTagsCommand) other).targetIndex));
    }
}