package seedu.address.logic.commands.project;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class AddTagCommandTest {

    @Test
    public void execute_newTag_success() throws Exception {
        ModelStubAcceptingTagAdded modelStub = new ModelStubAcceptingTagAdded();
        Tag validTag = new Tag("friends");

        CommandResult commandResult = new AddTagCommand(validTag).execute(modelStub);

        assertEquals(String.format(AddTagCommand.MESSAGE_SUCCESS, Messages.format(validTag)),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.tagsAdded.size());
        assertEquals(validTag, modelStub.tagsAdded.get(0));
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        Tag validTag = new Tag("friends");
        AddTagCommand addTagCommand = new AddTagCommand(validTag);
        ModelStubWithTag modelStub = new ModelStubWithTag(validTag);

        assertThrows(CommandException.class,
                AddTagCommand.MESSAGE_DUPLICATE_TAG, () -> addTagCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Tag friends = new Tag("friends");
        Tag colleagues = new Tag("colleagues");
        AddTagCommand addFriendsCommand = new AddTagCommand(friends);
        AddTagCommand addColleaguesCommand = new AddTagCommand(colleagues);

        // same object -> returns true
        assertTrue(addFriendsCommand.equals(addFriendsCommand));

        // same values -> returns true
        AddTagCommand addFriendsCopy = new AddTagCommand(friends);
        assertTrue(addFriendsCommand.equals(addFriendsCopy));

        // different types -> returns false
        assertFalse(addFriendsCommand.equals(1));

        // null -> returns false
        assertFalse(addFriendsCommand.equals(null));

        // different tag -> returns false
        assertFalse(addFriendsCommand.equals(addColleaguesCommand));
    }

    @Test
    public void toStringMethod() {
        Tag tag = new Tag("friends");
        AddTagCommand addTagCommand = new AddTagCommand(tag);
        String expected = AddTagCommand.class.getCanonicalName() + "{toAdd=" + tag + "}";
        assertEquals(expected, addTagCommand.toString());
    }

    /**
     * A default model stub that has all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deleteTag(Tag target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setTag(Tag target, Tag editedTag) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Tag> getTagList() {
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that contains a single tag.
     */
    private class ModelStubWithTag extends ModelStub {
        private final Tag tag;

        ModelStubWithTag(Tag tag) {
            requireNonNull(tag);
            this.tag = tag;
        }

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return this.tag.equals(tag);
        }
    }

    /**
     * A Model stub that always accepts the tag being added.
     */
    private class ModelStubAcceptingTagAdded extends ModelStub {
        final ArrayList<Tag> tagsAdded = new ArrayList<>();

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return tagsAdded.contains(tag);
        }

        @Override
        public void addTag(Tag tag) {
            requireNonNull(tag);
            tagsAdded.add(tag);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
