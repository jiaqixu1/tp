package seedu.address.logic.commands.project;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.DeleteProjectDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class DeleteProjectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteOneProjectUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("1").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(DeleteProjectCommand.MESSAGE_DELETE_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteOneProjectFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("1").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(DeleteProjectCommand.MESSAGE_DELETE_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteOneProjectOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("2").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(deleteProjectCommand, model, DeleteProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteOneProjectOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("2").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(deleteProjectCommand, model, DeleteProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteMultipleProjectsOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(deleteProjectCommand, model, DeleteProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteMultipleProjectsOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(deleteProjectCommand, model, DeleteProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteMultipleProjectsUnfilteredList_success() {
        Index indexSecondPerson = Index.fromOneBased(2);
        Person firstPerson = model.getFilteredPersonList().get(indexSecondPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(indexSecondPerson, descriptor);

        String expectedMessage = String.format(DeleteProjectCommand.MESSAGE_DELETE_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteMultipleProjectsFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(DeleteProjectCommand.MESSAGE_DELETE_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noProjectSpecifiedUnfilteredList_errorThrown() {
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(
                INDEX_FIRST_PERSON, new DeleteProjectCommand.DeleteProjectDescriptor()
        );
        assertCommandFailure(deleteProjectCommand, model, DeleteProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noProjectSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(
                INDEX_FIRST_PERSON, new DeleteProjectCommand.DeleteProjectDescriptor()
        );
        assertCommandFailure(deleteProjectCommand, model, DeleteProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteProjectCommand.DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder()
                .withProjects("1").build();
        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds project to a person of a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteProjectCommand deleteProjectCommand = new DeleteProjectCommand(outOfBoundIndex,
                new DeleteProjectDescriptorBuilder().withProjects("1").build());

        assertCommandFailure(deleteProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
