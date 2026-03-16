package seedu.address.logic.commands.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class ViewAllTagCommandTest {

    @Test
    public void execute_noTags_showsNone() {
        ModelStubWithTagList modelStub = new ModelStubWithTagList();

        CommandResult result = new ViewAllTagCommand().execute(modelStub);

        assertEquals(ViewAllTagCommand.MESSAGE_SUCCESS + " None", result.getFeedbackToUser());
    }

    @Test
    public void execute_withTags_showsAllTags() {
        Tag friends = new Tag("friends");
        Tag colleagues = new Tag("colleagues");
        ModelStubWithTagList modelStub = new ModelStubWithTagList(friends, colleagues);

        CommandResult result = new ViewAllTagCommand().execute(modelStub);

        String expected = ViewAllTagCommand.MESSAGE_SUCCESS + "\n"
                + friends + "\n" + colleagues;
        assertEquals(expected, result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ViewAllTagCommand viewAllTagCommand = new ViewAllTagCommand();

        // same object -> returns true
        assertTrue(viewAllTagCommand.equals(viewAllTagCommand));

        // different instance of same type -> returns true
        assertTrue(viewAllTagCommand.equals(new ViewAllTagCommand()));

        // different types -> returns false
        assertFalse(viewAllTagCommand.equals(1));

        // null -> returns false
        assertFalse(viewAllTagCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        String expected = ViewAllTagCommand.class.getCanonicalName()
                + "{commandWord=" + ViewAllTagCommand.COMMAND_WORD + "}";
        assertEquals(expected, new ViewAllTagCommand().toString());
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(ViewAllTagCommand.COMMAND_WORD.hashCode(), new ViewAllTagCommand().hashCode());
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
     * A Model stub with a configurable tag list.
     */
    private class ModelStubWithTagList extends ModelStub {
        private final ObservableList<Tag> tags;

        ModelStubWithTagList(Tag... tags) {
            this.tags = FXCollections.observableArrayList(tags);
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }
}
