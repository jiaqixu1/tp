package seedu.address.logic.commands.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandTest {

    @Test
    public void execute_validIndex_success() throws Exception {
        Tag tagToDelete = new Tag("friends");
        ModelStubWithTagList modelStub = new ModelStubWithTagList(tagToDelete);

        CommandResult result = new DeleteTagCommand(Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete),
                result.getFeedbackToUser());
        assertTrue(modelStub.deletedTags.contains(tagToDelete));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithTagList modelStub = new ModelStubWithTagList(new Tag("friends"));
        Index outOfBoundIndex = Index.fromOneBased(2);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> new DeleteTagCommand(
                        outOfBoundIndex).execute(modelStub));
    }

    @Test
    public void equals() {
        DeleteTagCommand deleteFirstCommand = new DeleteTagCommand(Index.fromOneBased(1));
        DeleteTagCommand deleteSecondCommand = new DeleteTagCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCopy = new DeleteTagCommand(Index.fromOneBased(1));
        assertTrue(deleteFirstCommand.equals(deleteFirstCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(index);
        String expected = DeleteTagCommand.class.getCanonicalName() + "{targetIndex=" + index + "}";
        assertEquals(expected, deleteTagCommand.toString());
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
     * A Model stub that contains a fixed tag list and records deleted tags.
     */
    private class ModelStubWithTagList extends ModelStub {
        final ArrayList<Tag> deletedTags = new ArrayList<>();
        private final ArrayList<Tag> tags;

        ModelStubWithTagList(Tag... tags) {
            this.tags = new ArrayList<>(Arrays.asList(tags));
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return FXCollections.observableArrayList(tags);
        }

        @Override
        public void deleteTag(Tag target) {
            tags.remove(target);
            deletedTags.add(target);
        }
    }
}
